package com.llzzhh.moments.summer.service;

import com.llzzhh.moments.summer.dto.ContentDTO;
import com.llzzhh.moments.summer.entity.Content;

import java.util.List;


public interface SquareService {
    List<ContentDTO> getContentsOrderedSquare(int page, int size);

}
