package com.lotte4.dto;

import lombok.*;

import java.time.LocalDateTime;
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BannerDTO {
    private int bannerId;
    private String img;
    private String name;
    private String size;
    private String background;
    private String link;
    private String location;
    private String type;
    private LocalDateTime sDate;
    private LocalDateTime eDate;
    private LocalDateTime sTime;
    private LocalDateTime eTime;
    private int state;
}
