package com.cloud.cloud_rest.board;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "board_tb")
@Builder
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

    public void update(BoardRequestDto.UpdateDto updateDTO) {
        // userId는 게시글 생성시에만 필요 --> 수정시에는 변경하지않음
        // board.setUserId(this.userId);
        this.title = updateDTO.getTitle();
        this.content = updateDTO.getContent();

    }

}
