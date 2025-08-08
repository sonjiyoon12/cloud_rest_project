package com.cloud.cloud_rest.corporatePostLike;

import com.cloud.cloud_rest.corporate.CorporatePost;
import com.cloud.cloud_rest.user.User;
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
                        columnNames = {"user_id", "corporate_post_id"}
                )
        })
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CorporatePostLike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "corporate_post_id", nullable = false)
    private CorporatePost corporatePost;

    @Builder
    public CorporatePostLike(User user, CorporatePost corporatePost) {
        this.user = user;
        this.corporatePost = corporatePost;
    }
}