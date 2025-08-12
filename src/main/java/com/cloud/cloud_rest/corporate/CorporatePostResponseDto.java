package com.cloud.cloud_rest.corporate;

import lombok.Getter;

import java.util.List;
import java.time.LocalDateTime;

public class CorporatePostResponseDto {

    @Getter
    public static class ListDto {
        private final Long id;
        private final String title;
        private final String authorName;
        private final int viewCount;
        private final int likeCount;
        private final LocalDateTime createdAt;
        private final List<String> tags;

        public ListDto(CorporatePost post) {
            this.id = post.getId();
            this.title = post.getTitle();
            this.authorName = post.getAuthor().getCorpName();
            this.viewCount = post.getViewCount();
            this.likeCount = post.getLikeCount();
            this.createdAt = post.getCreatedAt();
            this.tags = post.getTags().stream().map(tag -> tag.getName()).collect(java.util.stream.Collectors.toList());
        }
    }

    @Getter
    public static class DetailDto {
        private final Long id;
        private final String title;
        private final String content;
        private final String authorName;
        private final int viewCount;
        private final int likeCount;
        private final LocalDateTime createdAt;
        private final List<String> tags;

        public DetailDto(CorporatePost post) {
            this.id = post.getId();
            this.title = post.getTitle();
            this.content = post.getContent();
            this.authorName = post.getAuthor().getCorpName();
            this.viewCount = post.getViewCount();
            this.likeCount = post.getLikeCount();
            this.createdAt = post.getCreatedAt();
            this.tags = post.getTags().stream().map(tag -> tag.getName()).collect(java.util.stream.Collectors.toList());
        }
    }
}