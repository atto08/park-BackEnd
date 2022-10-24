package com.killdongmu.Hanghae99Week5BackEnd.repository;

import com.killdongmu.Hanghae99Week5BackEnd.entity.Boards;
import com.killdongmu.Hanghae99Week5BackEnd.entity.Hearts;
import com.killdongmu.Hanghae99Week5BackEnd.entity.Members;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HeartRepository extends JpaRepository<Hearts, Long> {

    List<Hearts> findAllByBoard(Boards board);

    // 게시글 삭제시 좋아요 삭제
    void deleteByBoard(Boards board);

    // 좋아요 존재 여부 확인 ( 좋아요 추가 or 삭제 )
    boolean existsByBoardAndMember(Boards board, Members member);

    // 좋아요 삭제
    void deleteByBoardAndMember(Boards board, Members members);


}
