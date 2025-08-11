package com.cloud.cloud_rest.corporateComment;

import com.cloud.cloud_rest._global._core.common.Timestamped;
import com.cloud.cloud_rest.corporate.CorporatePost;
import com.cloud.cloud_rest.corporateCommentLike.CorporateCommentLike;
import com.cloud.cloud_rest.user.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "corporate_post_comment_tb")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CorporatePostComment extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "corporate_post_comment_id") // ID 컬럼명 통일
    private Long id;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User author;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "corporate_post_id", nullable = false)
    private CorporatePost corporatePost;

    @Column(columnDefinition = "integer default 0", nullable = false)
    private int likeCount;

    @OneToMany(mappedBy = "comment", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CorporateCommentLike> likes = new ArrayList<>();

    @Builder
    public CorporatePostComment(String content, User author, CorporatePost corporatePost) {
        this.content = content;
        this.author = author;
        this.corporatePost = corporatePost;
    }

    public void update(String content) {
        this.content = content;
    }

    public void updateLikeCount(int count) {
        this.likeCount = count;
    }
}