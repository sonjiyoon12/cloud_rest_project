package com.cloud.cloud_rest.corporate.corporate_tag;

import com.cloud.cloud_rest.corporate.CorporatePost;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "corporate_tag_tb",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "corporate_post_tag_uk",
                        columnNames = {"corporate_post_id", "name"}
                )
        })
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CorporateTag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "corporate_post_id", nullable = false)
    private CorporatePost corporatePost;

    @Builder
    public CorporateTag(String name, CorporatePost corporatePost) {
        this.name = name;
        this.corporatePost = corporatePost;
    }

    // 연관관계 편의 메서드 (양방향 관계 설정 시 사용)
    public void setCorporatePost(CorporatePost corporatePost) {
        this.corporatePost = corporatePost;
    }
}