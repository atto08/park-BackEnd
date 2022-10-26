package com.killdongmu.Hanghae99Week5BackEnd.repository;


import com.killdongmu.Hanghae99Week5BackEnd.security.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByKey(String key);
}