package com.llzzhh.moments.summer.dto;

import com.llzzhh.moments.summer.entity.Comment;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class ContentDTO {
    private String contentId;
    private Integer userId;
    private String content;
    private String state;
    private LocalDateTime createTime;
    private Integer likes;
    private Boolean isLiked;
    private String commentText;
    private List<Comment> comments;

    private List<String> uploadedImages;
    private List<String> usedImages;
    // 分页相关字段
    private Integer page;
    private Integer pageSize;
}
