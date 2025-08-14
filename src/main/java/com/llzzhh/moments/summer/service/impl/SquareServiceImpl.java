package com.llzzhh.moments.summer.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.llzzhh.moments.summer.dto.ContentDTO;
import com.llzzhh.moments.summer.service.ContentService;
import com.llzzhh.moments.summer.service.impl.ContentServiceImpl;
import com.llzzhh.moments.summer.entity.Content;
import com.llzzhh.moments.summer.mapper.ContentMapper;
import com.llzzhh.moments.summer.service.SquareService;
import com.llzzhh.moments.summer.entity.User;
import com.llzzhh.moments.summer.entity.Like;
import com.llzzhh.moments.summer.mapper.LikeMapper;
import com.llzzhh.moments.summer.entity.Comment;
import  com.llzzhh.moments.summer.mapper.CommentMapper;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class SquareServiceImpl implements SquareService {

    private final ContentMapper contentMapper;
    private final LikeMapper likeMapper;
    private final CommentMapper commentMapper;

@Override
public List<ContentDTO> getContentsOrderedSquare(int page, int size) {
    int offset = (page - 1) * size;
    List<Content> contents = contentMapper.selectSquareContentsWithUsername(offset, size);

    return contents.stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
}


    private ContentDTO convertToDTO(Content content) {
        ContentDTO dto = new ContentDTO();
        dto.setContentId(content.getContentId());
        dto.setUserId(content.getUserId());
        dto.setUsername(content.getUsername());
        dto.setContent(content.getContent());
        dto.setState(content.getState());
        dto.setCreateTime(content.getCreateTime()); // 修正字段名
        dto.setLikes(content.getLikes());
        dto.setIsLiked(isLike(content.getContentId()));
        List<Comment> comments = commentMapper.selectCommentsWithUsername(content.getContentId());
        dto.setComments(comments);
        return dto;
    }
    private boolean isLike(String id){
        return likeMapper.exists(new QueryWrapper<Like>()
                .eq("likeID", "like"+id + "_" + getCurrentUserId()));
    }
    private Integer getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()
                && authentication.getPrincipal() instanceof User currentUser) {
            // 直接从User对象获取uid
            return currentUser.getUid();
        }
        throw new SecurityException("用户未认证");
    }

}
