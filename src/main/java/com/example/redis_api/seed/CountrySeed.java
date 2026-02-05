package com.example.redis_api.seed;

import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.example.redis_api.entity.Country;
import com.example.redis_api.repository.CountryRepository;

@Component
@Profile({"dev", "test"})
public class CountrySeed implements CommandLineRunner {

    private final CountryRepository countryRepository;

    public CountrySeed(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    @Override
    public void run(String... args) {

        if (countryRepository.count() > 0) {
            return;
        }

        List<Country> countries = List.of(
            new Country("TH", "Thailand"),
            new Country("US", "United States"),
            new Country("JP", "Japan"),
            new Country("CN", "China"),
            new Country("IN", "India"),
            new Country("BR", "Brazil"),
            new Country("RU", "Russia"),
            new Country("DE", "Germany"),
            new Country("FR", "France"),
            new Country("GB", "United Kingdom"),
            new Country("CA", "Canada"),
            new Country("AU", "Australia"),
            new Country("MX", "Mexico"),
            new Country("IT", "Italy"),
            new Country("ES", "Spain"),
            new Country("KR", "South Korea"),
            new Country("ID", "Indonesia"),
            new Country("TR", "Turkey"),
            new Country("SA", "Saudi Arabia"),
            new Country("AR", "Argentina")
        );

        countryRepository.saveAll(countries);

        System.out.println("✅ Country seed completed: " + countries.size() + " records");
    }
}
