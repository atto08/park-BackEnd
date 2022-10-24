package com.killdongmu.Hanghae99Week5BackEnd.service;

import com.killdongmu.Hanghae99Week5BackEnd.entity.Boards;
import com.killdongmu.Hanghae99Week5BackEnd.entity.Hearts;
import com.killdongmu.Hanghae99Week5BackEnd.entity.Members;
import com.killdongmu.Hanghae99Week5BackEnd.repository.BoardRepository;
import com.killdongmu.Hanghae99Week5BackEnd.repository.HeartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class HeartService {

    private final HeartRepository heartRepository;

    private final BoardRepository boardRepository;

    @Transactional
    public void clickHeart(Long boardId, Members member) {

        // 게시글 찾기
        Boards board = boardRepository.findById(boardId).orElseThrow();

        // 게시글 존재 여부 파악해서 좋아요 및 좋아요 취소
        if(heartRepository.existsByBoardAndMember(board, member)){
            heartRepository.deleteByBoardAndMember(board, member);
        } else {
            Hearts heart = new Hearts(member, board);
            heartRepository.save(heart);
        }
    }
}
