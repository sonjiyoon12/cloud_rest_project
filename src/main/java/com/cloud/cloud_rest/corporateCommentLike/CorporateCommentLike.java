package com.cloud.cloud_rest.corporateCommentLike;

import com.cloud.cloud_rest.corporateComment.CorporatePostComment;
import com.cloud.cloud_rest.user.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "corporate_post_comment_like_tb",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "corporate_comment_like_uk",
                        columnNames = {"user_id", "comment_id"}
                )
        })
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CorporateCommentLike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comment_id", nullable = false)
    private CorporatePostComment comment;

    @Builder
    public CorporateCommentLike(User user, CorporatePostComment comment) {
        this.user = user;
        this.comment = comment;
    }
}