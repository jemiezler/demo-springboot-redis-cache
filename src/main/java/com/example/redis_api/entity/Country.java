package com.example.redis_api.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Entity
@Table(
    name = "country",
    uniqueConstraints = {
        @UniqueConstraint(columnNames = "code")
    }
)
@Data
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
public class Country {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @Column(nullable = false, length = 5)
    private String code;

    @NonNull
    @Column(nullable = false, length = 100)
    private String name;
}
