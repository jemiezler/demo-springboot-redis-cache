package com.example.redis_api.service;

import java.util.List;

import com.example.redis_api.entity.Country;

public interface CountryService {

    List<Country> getAllCountries();

    List<Country> getAllCountriesFromRedis();

    Country getCountryById(Long id);

    Country createCountry(Country country);

    Country updateCountry(Long id, Country country);

    void deleteCountry(Long id);
}
