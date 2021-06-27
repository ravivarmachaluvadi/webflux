package com.example.springboot.webflux.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Document(collection = "photo")
public class Photo {

    @Id
    private Integer id;
    private Integer albumId;
    private String title;
    private String url;
    private String thumbnailUrl;

}