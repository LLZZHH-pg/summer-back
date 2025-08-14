package com.llzzhh.moments.summer.controller;

import com.llzzhh.moments.summer.dto.ContentDTO;
import com.llzzhh.moments.summer.service.SquareService;
import com.llzzhh.moments.summer.vo.ResultVO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor

public class SquareController {
    private final SquareService contentService;

    @GetMapping("/contentsSquare")
    public ResultVO<List<ContentDTO>> getContentsSquare(@RequestParam(defaultValue = "1") int page , @RequestParam (defaultValue = "10") int pageSize) {
        return ResultVO.ok(contentService.getContentsOrderedSquare(page, pageSize));
    }
}
