package com.llzzhh.moments.summer.service;

import com.llzzhh.moments.summer.dto.LoginDTO;
import com.llzzhh.moments.summer.dto.RegisterDTO;

public interface UserService {
    String register(RegisterDTO dto);
    String login(LoginDTO dto);
}
