package com.thoughtworks.backend.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Date;

public class JwtUtils {

    public static final long EXPIRE = 1000 * 60 * 60 * 24;

    public static final String APP_SECRET = "uioi1h23i1u9sash1|qlpas!]oIAUHI_koj0|";

    public static String generateJwtToken(Long uid, String channel) {
        return Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setHeaderParam("alg", "HS256")

                .setSubject("customer")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRE))
                // 设置token主体部分 存储用户信息
                .claim("uid", uid)

                .signWith(SignatureAlgorithm.HS256, APP_SECRET)
                .compact();
    }

    public static boolean checkToken(String token) {
        if (!StringUtils.hasLength(token)) {
            return false;
        }
        try {
            Jwts.parserBuilder()
                    .setSigningKey(APP_SECRET)
                    .build()
                    .parseClaimsJws(token);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static boolean checkToken(HttpServletRequest request) {
        try {
            String jwtToken = request.getHeader("Authorization");
            if (!StringUtils.hasLength(jwtToken)) {
                return false;
            }
            Jwts.parserBuilder()
                    .setSigningKey(APP_SECRET)
                    .build()
                    .parseClaimsJws(jwtToken);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static String getFiledFromJwtToken(String jwtToken, String fieldName) {
        if (!StringUtils.hasLength(jwtToken)) {
            return null;
        }
        Jws<Claims> claimsJws = Jwts.parserBuilder()
                .setSigningKey(APP_SECRET)
                .build()
                .parseClaimsJws(jwtToken);
        Claims claims = claimsJws.getBody();
        return (String) claims.get(fieldName);
    }

    public static String getFiledFromJwtToken(HttpServletRequest request, String fieldName) {
        if (request.getMethod().equals(RequestMethod.OPTIONS.name())) {
            return null;
        }
        String jwtToken = request.getHeader("authorization");
        return getFiledFromJwtToken(jwtToken, fieldName);
    }

}
