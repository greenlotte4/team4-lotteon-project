package com.lotte4.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "banner")
public class Banner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
