package com.example.util;

import com.alibaba.fastjson.JSONObject;
import io.jsonwebtoken.*;

import java.util.Date;
import java.util.UUID;

/**
 * @author LiangJingSong
 * @version 1.0
 * @date 2023/8/17 21:06
 * @description JWT. The util class is used to generate token and check token.
 * The token is valid for 24 hours. And the class have paese methond to parse token.
 */
public class JWT {
    private static long time = 2592000000L; //30 days
    private static final String signature = "SCUEC12345";

    /**
     * 生成token，有效期为30天
     * @param username 用户名
     * @param power 权限
     * @return token
     */
    public static String getToken(String username, boolean power) {
        JwtBuilder jwtBuilder = Jwts.builder();
        return jwtBuilder
                .setHeaderParam("typ", "JWT")
                .setHeaderParam("alg", "HS256")
                .claim("username", username)
                .claim("power", power)
                .setSubject("ClockInJWT")
                .setExpiration(new Date(System.currentTimeMillis() + time))
                .setId(UUID.randomUUID().toString())
                .signWith(SignatureAlgorithm.HS256, signature)
                .compact();
    }

    /**
     * 检查token是否有效
     * @param token token
     * @return true/false
     */
    public static boolean checkToken(String token) {
        if(null == token) {
            return false;
        }
        try {
            Jwts.parser().setSigningKey(signature).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 解析token
     * @param token token
     * @return 解析后的json对象
     */
    public static JSONObject parse(String token) {
        if(null == token) {
            return null;
        }
        try {
            JwtParser jwtParser = Jwts.parser();
            Jws<Claims> claimsJwt = jwtParser.setSigningKey(signature).parseClaimsJws(token);
            Claims claims = claimsJwt.getBody();
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("username", claims.get("username"));
            jsonObject.put("power", claims.get("power"));
            return jsonObject;
        } catch (Exception e) {
            return null;
        }
    }
}
