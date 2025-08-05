package com.cloud.cloud_rest.corpskill;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CorpSkillId implements Serializable {
    private Long corpId;
    private Long skillId;
}
