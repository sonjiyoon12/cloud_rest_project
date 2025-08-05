package com.cloud.cloud_rest.career;

import lombok.Data;

import java.time.LocalDate;

public class CareerResponse {

    @Data
    public static class InfoDTO {
        private Long careerId;
        private String corpName;
        private String position;
        private String content;
        private LocalDate startAt;
        private LocalDate endAt;

        public InfoDTO(Career career) {
            this.careerId = careerId;
            this.corpName = corpName;
            this.position = position;
            this.content = content;
            this.startAt = startAt;
            this.endAt = endAt;
        }
    }
}
