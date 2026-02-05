package com.example.redis_api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.redis_api.entity.Country;
import com.example.redis_api.service.CountryService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/country")
@Tag(name = "Country", description = "Country API")
public class CountryController {

    @Autowired
    private CountryService countryService;

    @GetMapping
    @Operation(summary = "Get All Countries", description = "Get All Countries")
    public List<Country> getAllCountries() {
        return countryService.getAllCountries();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get Country By Id", description = "Get Country By Id")
    public Country getCountryById(@PathVariable Long id) {
        return countryService.getCountryById(id);
    }

    @PostMapping
    @Operation(summary = "Create Country", description = "Create Country")
    public Country createCountry(@RequestBody Country country) {
        return countryService.createCountry(country);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update Country", description = "Update Country")
    public Country updateCountry(@PathVariable Long id, @RequestBody Country country) {
        return countryService.updateCountry(id, country);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete Country", description = "Delete Country")
    public void deleteCountry(@PathVariable Long id) {
        countryService.deleteCountry(id);
    }

}
