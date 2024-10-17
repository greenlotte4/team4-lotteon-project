package com.lotte4.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "board_Cate")
public class board_Cate {
    @Id
    private int board_Cate_Id;
    //
    private int parent;
    // 이름
    private String name;


    //외래키 목록
    private int child;
}
