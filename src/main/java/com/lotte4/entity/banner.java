package com.lotte4.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "banner")
public class banner {
    @Id
    private int banner_No;
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
