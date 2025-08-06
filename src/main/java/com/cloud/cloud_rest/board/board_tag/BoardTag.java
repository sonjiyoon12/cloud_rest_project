package com.cloud.cloud_rest.board.board_tag;

import com.cloud.cloud_rest.board.Board;
import jakarta.persistence.*;
import lombok.*;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
@Entity
@Table(name = "board_tag_tb")
public class BoardTag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long boardTagId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    @Column(name = "tag_name")
    private String tagName;

    @Builder
    public BoardTag(Board board, String tagName) {
        this.board = board;
        this.tagName = tagName;
    }
}
