package com.cloud.cloud_rest.qnaAnswer;

import com.cloud.cloud_rest._global.utils.DateUtil;
import com.cloud.cloud_rest.qnaBoard.QnaBoard;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Table(name = "qna_answer_tb")
@Entity
public class QnaAnswer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long qnaAnswerId;

    private String content;

    @CreationTimestamp
    private Timestamp createdAt;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "qnaBoard_id", nullable = false)
    private QnaBoard qnaBoard;

    public String getTime(){
        return DateUtil.timestampFormat(createdAt);
    }

    // 수정 기능 추가
    public void update(QnaAnswerRequest.UpdateDTO updateDTO){
        this.content = updateDTO.getContent();
    }
}
