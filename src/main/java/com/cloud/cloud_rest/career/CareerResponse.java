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
            this.careerId = career.getCareerId();
            this.corpName = career.getCorpName();
            this.position = career.getPosition();
            this.content = career.getContent();
            this.startAt = career.getStartAt();
            this.endAt = career.getEndAt();
        }
    }
}
