package com.llzzhh.moments.summer.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.llzzhh.moments.summer.dto.LoginDTO;
import com.llzzhh.moments.summer.dto.RegisterDTO;
import com.llzzhh.moments.summer.entity.User;
import com.llzzhh.moments.summer.mapper.UserMapper;
import com.llzzhh.moments.summer.service.UserService;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import javax.crypto.SecretKey;
import java.util.Date;
import java.nio.charset.StandardCharsets;

@Service
public class UserServiceimpl implements UserService {

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expiration}")
    private long jwtExpirationMs;

    private final UserMapper userMapper;
    public UserServiceimpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public String register(RegisterDTO dto) {
        // 1. 参数校验（直接复用原正则）
        validateRegister(dto);

        // 2. 唯一性校验
        if (exists(User::getEmail, dto.getEmail())) {
            throw new RuntimeException("该邮箱已被注册");
        }

        if (exists(User::getTel, dto.getTel())) {  // 手机号必选，直接校验
            throw new RuntimeException("该手机号已被注册");
        }

        if (exists(User::getName, dto.getName())) {
            throw new RuntimeException("该用户名已被注册");
        }

        // 3. 保存
        User user = new User();
        user.setEmail(dto.getEmail());
        user.setTel(dto.getTel());
        user.setName(dto.getName());
        user.setPassword(dto.getPassword());
        userMapper.insert(user);
        return String.format("%06d", user.getUid());
    }

    @Override
    public String login(LoginDTO dto) {
        LambdaQueryWrapper<User> wrapper = Wrappers.lambdaQuery();
        String account = dto.getAccount();

        if (account.contains("@")) {
            wrapper.eq(User::getEmail, account);
        } else if (account.matches("\\d{11}")) {
            wrapper.eq(User::getTel, account);
        } else {
            wrapper.eq(User::getName, account);
        }
        wrapper.eq(User::getPassword, dto.getPassword());

        User user = userMapper.selectOne(wrapper);
        if (user == null) {
            // 明确区分账号不存在和密码错误
            boolean accountExists = exists(User::getEmail, account) ||
                    exists(User::getTel, account) ||
                    exists(User::getName, account);

            if (accountExists) {
                throw new RuntimeException("密码错误");
            } else {
                throw new RuntimeException("账号不存在");
            }
        }

        // 生成JWT令牌
        return generateJwtToken(String.valueOf(user.getUid()));
    }
    private String generateJwtToken(String userId) {
        SecretKey key = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
        return Jwts.builder()
                .setSubject(userId)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationMs))
                .signWith(key)
                .compact();
    }

    private boolean exists(SFunction<User, ?> column, String val) {
        LambdaQueryWrapper<User> wrapper = Wrappers.<User>lambdaQuery().eq(column, val);
        return userMapper.selectCount(wrapper) > 0;
    }

    private void validateRegister(RegisterDTO dto) {
        String email = dto.getEmail();
        String tel   = dto.getTel();
        String name  = dto.getName();
        String pwd   = dto.getPassword();

        if (tel == null || tel.trim().isEmpty()) {
            throw new RuntimeException("手机号不能为空");
        }
        // 手机号格式校验（11位数字）
        if (!tel.matches("\\d{11}")) {
            throw new RuntimeException("电话号码必须是11位数字");
        }

        // 密码必填校验
        if (pwd == null || pwd.trim().isEmpty()) {
            throw new RuntimeException("密码不能为空");
        }
        // 密码长度校验
        if (pwd.length() < 8 || pwd.length() > 16) {
            throw new RuntimeException("密码需要在8~16位之间");
        }

        if (email == null || email.trim().isEmpty()) {
            throw new RuntimeException("邮箱不能为空");
        }

        // 邮箱校验格式
        if (!email.matches("^[A-Za-z0-9]+@[A-Za-z0-9]+$")) {
            throw new RuntimeException("邮箱格式不正确");
        }

        if (name == null || name.trim().isEmpty()) {
            throw new RuntimeException("用户名不能为空");
        }
        // 用户名校验规则
        if (name.length() < 2 || name.length() > 10 || !name.matches(".*[a-zA-Z].*")) {
            throw new RuntimeException("用户名在2~10个字符之间，且包含至少一个字母");
        }
    }
}