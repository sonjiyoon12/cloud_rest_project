package com.cloud.cloud_rest.qnaBoard;

import com.cloud.cloud_rest._global.utils.DateUtil;
import com.cloud.cloud_rest.qnaAnswer.QnaAnswer;
import com.cloud.cloud_rest.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.ToStringExclude;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Table(name = "qna_board_tb")
@Entity
public class QnaBoard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long qnaBoardId;

    private String title;
    private String content;

    @CreationTimestamp
    private Timestamp createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @ToStringExclude
    private User user;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "qnaBoard", cascade = CascadeType.ALL, orphanRemoval = true)
    private QnaAnswer qnaAnswer;

    public String getTime() {
        return DateUtil.timestampFormat(createdAt);
    }

    public boolean isOwner(Long checkUserId){
        return this.user.getUserId().equals(checkUserId);
    }

    // 수정 기능 추가
    public void update(QnaBoardRequest.UpdateDTO updateDTO){
        this.title = updateDTO.getTitle();
        this.content = updateDTO.getContent();
    }
}
