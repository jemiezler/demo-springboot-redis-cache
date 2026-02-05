package com.example.redis_api;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.test.context.ActiveProfiles;

import com.example.redis_api.entity.Country;
import com.example.redis_api.repository.CountryRepository;
import com.example.redis_api.service.CountryService;

@SpringBootTest
@EnableCaching
@ActiveProfiles("test")
class RedisApiApplicationTests {

    @Autowired
    private CountryService countryService;

    @Autowired
    private CountryRepository countryRepository;

    @Autowired
    private CacheManager cacheManager;

    @Test
    void should_cache_country_by_id() {
        // given
        Country c = new Country();
        c.setCode("TH");
        c.setName("Thailand");
        Country saved = countryRepository.saveAndFlush(c);

        Cache cache = cacheManager.getCache("master:country");
        assertNotNull(cache);
        assertNull(cache.get(saved.getId()));

        // when - first call (DB)
        Country first = countryService.getCountryById(saved.getId());

        // then - cache should exist
        assertNotNull(cache.get(saved.getId()));

        // when - second call (CACHE)
        Country second = countryService.getCountryById(saved.getId());

        // then
        assertEquals(first.getName(), second.getName());
    }
}
