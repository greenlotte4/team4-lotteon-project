package com.lotte4.document;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Setter
@Getter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Document(value = "reviews") // Mongdb의 Collection
public class ReviewDocument {

    @Id
    private String _id;

    private String uid;
    private int variantId;
    private int prodId;
    private int rating;
    private String content;
    private String regIp;
    private String img1;
    private String img2;

    @CreationTimestamp
    private String regDate;

}
