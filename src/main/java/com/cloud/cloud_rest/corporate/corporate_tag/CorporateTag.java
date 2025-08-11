package com.cloud.cloud_rest.corporate.corporate_tag;


import com.cloud.cloud_rest.corporate.CorporatePost;
import jakarta.persistence.*;
import lombok.*;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
@Entity
@Table(name = "corporate_tag_tb")
public class CorporateTag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long corporateTagId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "corporate_id")
    private CorporatePost corporatePost;

    @Column(name = "tag_name")
    private String tagName;

    @Builder
    public CorporateTag(CorporatePost corporatePost, String tagName) {
        this.corporatePost = corporatePost;
        this.tagName = tagName;
    }

}
