package com.killdongmu.Hanghae99Week5BackEnd.repository;


import com.killdongmu.Hanghae99Week5BackEnd.entity.Boards;
import com.killdongmu.Hanghae99Week5BackEnd.entity.Comments;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comments, Long> {

    List<Comments> findCommentsByBoardOrderByCreatedAtDesc(Boards board);

    List<Comments> findAllByBoard(Boards board);

    void deleteByBoard(Boards board);
}
