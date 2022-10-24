package com.killdongmu.Hanghae99Week5BackEnd.repository;

import com.killdongmu.Hanghae99Week5BackEnd.entity.Boards;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardRepository extends JpaRepository<Boards, Long> {

     List<Boards> findAllByOrderByCreatedAtDesc();
}
