package com.killdongmu.Hanghae99Week5BackEnd.repository;


import com.killdongmu.Hanghae99Week5BackEnd.entity.Boards;
import com.killdongmu.Hanghae99Week5BackEnd.entity.Comments;
import com.killdongmu.Hanghae99Week5BackEnd.entity.Members;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comments, Long> {

    List<Comments> findCommentsByBoardOrderByCommentIdDesc(Boards board);

    List<Comments> findAllByBoard(Boards board);

    List<Comments> findAllByMember(Members member);

    void deleteByBoard(Boards board);

    Long countByBoard(Boards board);
}
