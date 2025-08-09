package com.llzzhh.moments.summer.controller;

import com.llzzhh.moments.summer.dto.LoginDTO;
import com.llzzhh.moments.summer.dto.RegisterDTO;
import com.llzzhh.moments.summer.entity.User;
import com.llzzhh.moments.summer.service.UserService;
import com.llzzhh.moments.summer.vo.ResultVO;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/auth/register")
    public ResultVO<String> register(@RequestBody RegisterDTO dto) {
        try {
            return ResultVO.ok(userService.register(dto));
        } catch (Exception e) {
            return ResultVO.fail(e.getMessage());
        }
    }

    @PostMapping("/auth/login")
    public ResultVO<String> login(@RequestBody LoginDTO dto) {
        try {
            return ResultVO.ok(userService.login(dto));
        } catch (Exception e) {
            return ResultVO.unauthorized(e.getMessage());
        }
    }

//    @GetMapping("/profile")
//    public ResultVO<User> getProfile() {
//        try {
//            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//            if (auth == null || auth.getPrincipal() == null) {
//                return ResultVO.unauthorized("用户未登录");
//            }
//            User user = (User) auth.getPrincipal();
//            return ResultVO.ok(user);
//        } catch (Exception e) {
//            return ResultVO.serverError("获取用户信息失败");
//        }
//    }

//    @PutMapping("/profile")
//    public ResultVO<String> updateProfile(@RequestBody User updateUser) {
//        try {
//            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//            if (auth == null || auth.getPrincipal() == null) {
//                return ResultVO.unauthorized("用户未登录");
//            }
//            User currentUser = (User) auth.getPrincipal();
//            updateUser.setUid(currentUser.getUid());
//            userService.updateProfile(updateUser);
//            return ResultVO.ok("更新成功");
//        } catch (Exception e) {
//            return ResultVO.fail(e.getMessage());
//        }
//    }

//    @PostMapping("/logout")
//    public ResultVO<String> logout() {
//        try {
//            SecurityContextHolder.clearContext();
//            return ResultVO.ok("退出登录成功");
//        } catch (Exception e) {
//            return ResultVO.serverError("退出登录失败");
//        }
//    }
}