package com.llzzhh.moments.summer.service;

import com.llzzhh.moments.summer.dto.ContentDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ContentService {
    List<ContentDTO> getContents();
    void saveContent(ContentDTO contentDTO);
    void updateContentState(String id, String state);
    void deleteContent(String id);
    String uploadFile(MultipartFile file);
    boolean deleteFile(String fileUrl);
}
