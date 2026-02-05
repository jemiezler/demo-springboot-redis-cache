package com.example.redis_api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.redis_api.entity.Country;
import com.example.redis_api.service.CountryService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/redis/country")
@Tag(name = "Country Redis", description = "Country Redis Management API")
public class CountryRedisController {

    @Autowired
    private CountryService countryService;

    @PostMapping
    @Operation(summary = "Refresh/Create Country Cache", description = "Fetch data from DB and update Redis cache")
    public List<Country> refreshCountries() {
        return countryService.refreshAllCountries();
    }

    @GetMapping
    @Operation(summary = "Get Countries from Redis", description = "Get Countries directly from Redis cache")
    public List<Country> getCountriesFromRedis() {
        return countryService.getAllCountriesFromRedis();
    }

    @DeleteMapping
    @Operation(summary = "Clear Country Cache", description = "Clear the Country list from Redis cache")
    public void clearCountries() {
        countryService.clearAllCountries();
    }
}
