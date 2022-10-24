package com.killdongmu.Hanghae99Week5BackEnd.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.killdongmu.Hanghae99Week5BackEnd.util.Timestamped;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Builder
@Entity(name = "board")
@NoArgsConstructor
@AllArgsConstructor
public class Boards extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id", nullable = false)
    private Long board_id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "content", nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    @JsonIgnore
    private Members member;

    public void updateBoard(String title, String content) {
        this.title = title;
        this.content = content;
    }

}
