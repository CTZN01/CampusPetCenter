package com.ashen.petcommon.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Set;

public class JwtUtils {
    // 1. 定义过期时间 (例如 24 小时)
    private static final long EXPIRATION_TIME = 1000 * 60 * 60 * 24;

    // 2. 定义密钥字符串
    // 注意：HS256 算法要求密钥至少 32 个字符 (256 bit)，否则报错！
    // 生产环境建议放在配置文件中读取
    private static final String SECRET_STRING = "YourSup3rS3cr3tK3yMustBeAtL3ast32Byt3sL0ng!!";

    // 3. 将字符串转换为 SecretKey 对象 (这是新版本的强制要求)
    private static final SecretKey SECRET_KEY = Keys.hmacShaKeyFor(SECRET_STRING.getBytes(StandardCharsets.UTF_8));

    /**
     * 生成 Token
     */
    public static String generateToken(String username, Long userId, Set<String> roles) {
        return Jwts.builder()
                .subject(username)                 // 设置主题 (用户名)
                .claim("userId", userId)           // 自定义载荷
                .claim("roles", roles)             // 自定义载荷
                .issuedAt(new Date())              // 签发时间
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME)) // 过期时间
                .signWith(SECRET_KEY)              // 使用 Key 对象签名 (不需要指定算法，会自动根据 Key 推断)
                .compact();
    }

    /**
     * 解析 Token
     */
    public static Claims parseToken(String token) {
        // 新版写法：parser() -> verifyWith(key) -> build() -> parseSignedClaims()
        return Jwts.parser()
                .verifyWith(SECRET_KEY)
                .build()
                .parseSignedClaims(token) // 这里如果不合法或过期，会抛出异常
                .getPayload();            // 以前叫 getBody()，现在叫 getPayload()
    }

    /**
     * 校验 Token 是否有效 (建议增加此方法)
     * 在拦截器中调用
     */
    public static boolean validateToken(String token) {
        try {
            parseToken(token);
            return true;
        } catch (Exception e) {
            // 可能是过期(ExpiredJwtException)、签名错误(SignatureException)等
            return false;
        }
    }
}
