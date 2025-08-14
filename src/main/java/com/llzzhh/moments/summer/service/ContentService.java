package com.llzzhh.moments.summer.service;

import com.llzzhh.moments.summer.dto.ContentDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ContentService {
    List<ContentDTO> getContentsOrdered(int page, int size);

    void saveContent(ContentDTO dto);
    boolean deleteFile(String fileUrl);

    void updateContentState(String id, String state);
    void deleteContent(String id);
    String uploadFile(MultipartFile file);
    void likeContent(String id);
    void commentContent(String contentId, String commentText);


}
