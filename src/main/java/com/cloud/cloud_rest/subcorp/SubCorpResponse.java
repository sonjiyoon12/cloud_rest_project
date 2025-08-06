package com.cloud.cloud_rest.subcorp;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
public class SubCorpResponse {

    @Schema(name = "SubCorpSaveDTO")
    @Data
    public static class SaveDTO {
        private Long subCorpId;
        private Long corpId;
        private Long userId;
        private String createdAt;

        public SaveDTO(SubCorp subCorp) {
            this.subCorpId = subCorp.getSubCorpId();
            this.corpId = subCorp.getCorp().getCorpId();
            this.userId = subCorp.getUser().getUserId();
            this.createdAt = subCorp.getTime();
        }
    }

    @Schema(name = "SubCorpDetailDTO")
    @Data
    public static class DetailDTO {
        private Long subCorpId;
        private Long corpId;
        private Long userId;
        private String createdAt;

        public DetailDTO(SubCorp subCorp) {
            this.subCorpId = subCorp.getSubCorpId();
            this.corpId = subCorp.getCorp().getCorpId();
            this.userId = subCorp.getUser().getUserId();
            this.createdAt = subCorp.getTime();
        }
    }
}

