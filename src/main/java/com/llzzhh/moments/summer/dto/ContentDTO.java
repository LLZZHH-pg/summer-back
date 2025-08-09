package com.llzzhh.moments.summer.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ContentDTO {
    private String contentId;
    private Integer userId;
    private String content;
    private String state;
    private LocalDateTime createTime;
    private Integer likes;
}
