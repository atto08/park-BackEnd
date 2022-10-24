package com.killdongmu.Hanghae99Week5BackEnd.repository;

import com.killdongmu.Hanghae99Week5BackEnd.entity.Members;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Members, Long> {

    Optional<Members> findByUsername(String username);

    boolean existsByUsername(String username);
}
