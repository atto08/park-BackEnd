package com.killdongmu.Hanghae99Week5BackEnd.repository;

import com.killdongmu.Hanghae99Week5BackEnd.entity.Boards;
import com.killdongmu.Hanghae99Week5BackEnd.entity.Members;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardRepository extends JpaRepository<Boards, Long> {

     List<Boards> findAllByOrderByBoardIdDesc();

     Slice<Boards> findAllByOrderByBoardIdDesc(Pageable pageable);

     List<Boards> findAllByMember(Members member);
}
