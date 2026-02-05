package com.example.redis_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.redis_api.entity.Country;

public interface CountryRepository extends JpaRepository<Country, Long> {
    
}
