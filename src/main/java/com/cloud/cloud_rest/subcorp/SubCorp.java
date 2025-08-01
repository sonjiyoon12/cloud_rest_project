package com.cloud.cloud_rest.subcorp;

import com.cloud.cloud_rest.corp.Corp;
import com.cloud.cloud_rest.user.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.ToStringExclude;

@Data
@NoArgsConstructor
@Table(name = "sub_corp_tb", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"user_id", "corp_id"})
})
@Entity
public class SubCorp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int subCorpId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @ToStringExclude
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "corp_id", nullable = false)
    @ToStringExclude
    private Corp corp;

    @Builder
    public SubCorp(int subCorpId, User user, Corp corp) {
        this.subCorpId = subCorpId;
        this.user = user;
        this.corp = corp;
    }
}
