package com.example.postservice.source.model;

import lombok.Data;

@Data
public class PostModel {
    private Long authorId;

    private String text;

    private String topic;
}
