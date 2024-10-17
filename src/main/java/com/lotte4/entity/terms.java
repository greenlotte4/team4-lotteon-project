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
@Table(name = "terms")
public class terms {
    @Id
    private int terms_No;
    private String term;
    private String tax;
    private String finance;
    private String privacy;
    private String location;
}
