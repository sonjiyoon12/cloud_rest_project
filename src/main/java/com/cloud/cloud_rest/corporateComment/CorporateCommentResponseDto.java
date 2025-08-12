package com.cloud.cloud_rest.corporateComment;

import com.cloud.cloud_rest._global.SessionUser;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Objects;

public class CorporateCommentResponseDto {

    @Getter
    public static class CommentDto {
        private final Long id;
        private final String content;
        private final String authorName;
        private final int likeCount;
        private final LocalDateTime createdAt;
        private final boolean isOwner;

        public CommentDto(CorporatePostComment comment, SessionUser sessionUser) {
            this.id = comment.getId();
            this.content = comment.getContent();
            this.authorName = comment.getAuthor().getCorpName();
            this.likeCount = comment.getLikeCount();
            this.createdAt = comment.getCreatedAt();
            this.isOwner = sessionUser != null && Objects.equals(comment.getAuthor().getCorpId(), sessionUser.getId());
        }
    }
}