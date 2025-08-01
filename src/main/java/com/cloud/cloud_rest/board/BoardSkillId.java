package com.cloud.cloud_rest.board;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
public class BoardSkillId {

    @Column(name = "board_id")
    private Long boardId;

    @Column(name = "skill_id")
    private Long skillId;

    @Builder
    public BoardSkillId(Long boardId, Long skillId) {
        this.boardId = boardId;
        this.skillId = skillId;
    }
}
