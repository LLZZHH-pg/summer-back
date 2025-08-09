package com.llzzhh.moments.summer.controller;

import com.llzzhh.moments.summer.dto.ContentDTO;
import com.llzzhh.moments.summer.service.ContentService;
import com.llzzhh.moments.summer.vo.ResultVO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class ContentController {

    private final ContentService contentService;

    // 获取用户内容
    @GetMapping("/contents")
    public ResultVO<List<ContentDTO>> getContents() {
        return ResultVO.ok(contentService.getContents());
    }

    // 保存内容
    @PostMapping("/contents")
    public ResultVO<Void> saveContent(@RequestBody ContentDTO contentDTO) {
        contentService.saveContent(contentDTO);
        return ResultVO.ok(null);
    }

    // 更新状态
//    @PostMapping("/contents/state")
//    public ResultVO<Void> updateState(@RequestBody UpdateStateRequest request) {
//        contentService.updateContentState(contentDTO.getContentId(), request.getState());
//        return ResultVO.ok(null);
//    }
    @PostMapping("/contents/state")
    public ResultVO<Void> updateState(@RequestBody ContentDTO contentDTO) {
        contentService.updateContentState(contentDTO.getContentId(), contentDTO.getState());
        return ResultVO.ok(null);
    }

    // 删除内容
    @PostMapping("/contents/delete")
    public ResultVO<Void> deleteContent(@RequestBody ContentDTO contentDTO) {
        contentService.deleteContent(contentDTO.getContentId());
        return ResultVO.ok(null);
    }

    // 文件上传
    @PostMapping("/upload")
    public ResultVO<String> uploadFile(@RequestParam("file") MultipartFile file) {
        return ResultVO.ok(contentService.uploadFile(file));
    }

    // 文件删除
    @PostMapping("/deleteFile")
    public ResultVO<Boolean> deleteFile(@RequestParam String fileUrl) {
        return ResultVO.ok(contentService.deleteFile(fileUrl));
    }

    // 内部类用于接收更新状态的请求
//    public static class UpdateStateRequest {
//        private String id;
//        private String state;
//
//        // getters and setters
//        public String getId() {
//            return id;
//        }
//
//        public void setId(String id) {
//            this.id = id;
//        }
//
//        public String getState() {
//            return state;
//        }
//
//        public void setState(String state) {
//            this.state = state;
//        }
//    }
}