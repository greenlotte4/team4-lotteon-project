package com.lotte4.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "board_Cate")
public class BoardCate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int boardCateId;
    private int parent;
    private int child;
    private String name;


}
