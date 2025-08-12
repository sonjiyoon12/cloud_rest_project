package com.cloud.cloud_rest.corporateCommentLike;

import com.cloud.cloud_rest.corp.Corp; // Corp 엔티티 임포트
import com.cloud.cloud_rest.corporateComment.CorporatePostComment;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Table(name = "corporate_post_comment_like_tb",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "corporate_post_comment_like_uk",
                        columnNames = {"corp_id", "corporate_post_comment_id"}
                )
        })
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CorporateCommentLike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "corp_id", nullable = false)
    private Corp corp;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "corporate_post_comment_id", nullable = false)
    private CorporatePostComment comment;

    @Builder
    public CorporateCommentLike(Corp corp, CorporatePostComment comment) {
        this.corp = corp;
        this.comment = comment;
    }
}