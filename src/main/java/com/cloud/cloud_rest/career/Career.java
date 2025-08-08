package com.cloud.cloud_rest.career;

import com.cloud.cloud_rest.resume.Resume;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Table(name = "career_tb")
@Entity
public class Career {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long careerId;

    private String corpName;
    private String position;
    private String content;

    private LocalDate startAt;
    private LocalDate endAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "resume_id")
    private Resume resume;

    public Career(Resume resume) {
        this.resume = resume;
    }

    // 수정 기능 추가
    public void update(CareerRequest.UpdateDTO updateDTO) {
        this.corpName = updateDTO.getCorpName();
        this.position = updateDTO.getPosition();
        this.content = updateDTO.getContent();
        this.startAt = updateDTO.getStartAt();
        this.endAt = updateDTO.getEndAt();
    }
}
