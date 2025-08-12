package com.cloud.cloud_rest.corporatePostLike;

import com.cloud.cloud_rest.corp.Corp;
import com.cloud.cloud_rest.corporate.CorporatePost;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "corporate_post_like_tb",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "corporate_post_like_uk",
                        columnNames = {"corp_id", "corporate_post_id"}
                )
        })
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CorporatePostLike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "corp_id", nullable = false)
    private Corp corp;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "corporate_post_id", nullable = false)
    private CorporatePost corporatePost;

    @Builder
    public CorporatePostLike(Corp corp, CorporatePost corporatePost) {
        this.corp = corp;
        this.corporatePost = corporatePost;
    }
}