package com.cloud.cloud_rest.board;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;


@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "board_tb")
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id")
    private Long boardId; // 게시글 고유 Id

    @Column(name = "title", nullable = false, length = 255)
    private String title; // 게시글 제목

    @Lob
    @Column(name = "content")
    private String content; // 게시글 내용

    @Column(name = "user_id", nullable = false)
    private Long userId; // 작성자 Id

    @Column(name = "created_at", columnDefinition = "datetime(6) default current_timestamp")
    private LocalDateTime createdAt; // 게시글 작성일

    @Column(name = "views", columnDefinition = "int default 0")
    private Integer views; // 조회수


}
