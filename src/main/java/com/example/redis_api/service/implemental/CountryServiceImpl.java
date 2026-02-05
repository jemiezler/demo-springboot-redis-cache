package com.example.redis_api.service.implemental;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import com.example.redis_api.entity.Country;
import com.example.redis_api.repository.CountryRepository;
import com.example.redis_api.service.CountryService;

@Service
public class CountryServiceImpl implements CountryService {

    @Autowired
    private CountryRepository countryRepository;
    @Autowired
    private CacheManager cacheManager;
    private static final Logger log = LoggerFactory.getLogger(CountryServiceImpl.class);

    /**
     * Cache list of countries
     */
    @Override
    @Cacheable(value = "master:country", key = "'list'")
    public List<Country> getAllCountries() {
        log.info(">>> Query COUNTRY LIST from MASTER DB");
        return countryRepository.findAll();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Country> getAllCountriesFromRedis() {
        Cache cache = cacheManager.getCache("master:country");
        if (cache == null) {
            return null;
        }

        Cache.ValueWrapper wrapper = cache.get("list");
        if (wrapper == null) {
            log.info(">>> COUNTRY LIST cache MISS (cache-only)");
            return null;
        }

        log.info(">>> COUNTRY LIST cache HIT (cache-only)");
        return (List<Country>) wrapper.get();
    }

    /**
     * Cache country by id
     */
    @Override
    @Cacheable(value = "master:country", key = "#id")
    public Country getCountryById(Long id) {
        log.info(">>> Query COUNTRY by ID from MASTER DB");
        return countryRepository.findById(id).orElse(null);
    }

    /**
     * Create country → clear list cache
     */
    @Override
    @CacheEvict(value = "master:country", key = "'list'")
    public Country createCountry(Country country) {
        log.info(">>> Create COUNTRY in MASTER DB");
        return countryRepository.save(country);
    }

    /**
     * Update country → clear list + specific id
     */
    @Override
    @Caching(evict = {
            @CacheEvict(value = "master:country", key = "'list'"),
            @CacheEvict(value = "master:country", key = "#id")
    })
    public Country updateCountry(Long id, Country country) {
        log.info(">>> Update COUNTRY in MASTER DB");
        Country existing = countryRepository.findById(id).orElse(null);
        if (existing != null) {
            existing.setCode(country.getCode());
            existing.setName(country.getName());
            return countryRepository.save(existing);
        }
        return null;
    }

    /**
     * Delete country → clear list + specific id
     */
    @Override
    @Caching(evict = {
            @CacheEvict(value = "master:country", key = "'list'"),
            @CacheEvict(value = "master:country", key = "#id")
    })
    public void deleteCountry(Long id) {
        log.info(">>> Delete COUNTRY in MASTER DB");
        countryRepository.deleteById(id);
    }

    @Override
    @CachePut(value = "master:country", key = "'list'")
    public List<Country> refreshAllCountries() {
        log.info(">>> Refresh COUNTRY LIST in REDIS from MASTER DB");
        return countryRepository.findAll();
    }

    @Override
    @CacheEvict(value = "master:country", key = "'list'")
    public void clearAllCountries() {
        log.info(">>> Clear COUNTRY LIST in REDIS");
    }
}
