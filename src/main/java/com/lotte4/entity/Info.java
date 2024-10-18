package com.lotte4.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "info")
public class Info {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int InfoId;

    // 제목
    private String title;
    private String subTitle;

    // 로고
    private String headerLogo;
    private String favicon;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "info_footer_id")
    private InfoFooter infoFooter;

}
