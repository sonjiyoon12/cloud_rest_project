package com.cloud.cloud_rest.faqBoard;

import com.cloud.cloud_rest._global.utils.DateUtil;
import com.cloud.cloud_rest.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.ToStringExclude;

import java.sql.Timestamp;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Table(name = "faq_board_tb")
@Entity
public class FAQBoard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long faqId;

    private String title;
    private String content;
    private Timestamp createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @ToStringExclude
    private User user;

    public String getFormatTime() {
        return DateUtil.timestampFormat(createdAt);
    }

    //public void update()

}
