package com.cloud.cloud_rest.corporate;

import com.cloud.cloud_rest._global._core.common.Timestamped;
import com.cloud.cloud_rest.corp.Corp;
import com.cloud.cloud_rest.corporate.corporate_tag.CorporateTag;
import com.cloud.cloud_rest.corporateComment.CorporatePostComment;
import com.cloud.cloud_rest.corporatePostLike.CorporatePostLike;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "corporate_post_tb")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CorporatePost extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "corporate_post_id")
    private Long id;

    @Column(nullable = false, length = 100)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "corp_id", nullable = false)
    private Corp author;

    @Column(columnDefinition = "integer default 0", nullable = false)
    private int viewCount;

    @Column(columnDefinition = "integer default 0", nullable = false)
    private int likeCount;

    @OneToMany(mappedBy = "corporatePost", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CorporatePostLike> likes = new ArrayList<>();

    @OneToMany(mappedBy = "corporatePost", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CorporatePostComment> comments = new ArrayList<>();

    @Builder
    public CorporatePost(String title, String content, Corp author) {
        this.title = title;
        this.content = content;
        this.author = author;
    }

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public void increaseViewCount() {
        this.viewCount++;
    }

    public void updateLikeCount(int count) {
        this.likeCount = count;
    }
    @OneToMany(mappedBy = "corporatePost", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<CorporateTag> tags = new ArrayList<>();
}