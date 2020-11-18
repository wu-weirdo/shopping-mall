package com.edaochina.shopping.common.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.edaochina.shopping.common.utils.cache.RedisTool;
import com.edaochina.shopping.domain.base.jwt.JWTBean;
import com.edaochina.shopping.domain.constants.RedisConstants;
import lombok.experimental.UtilityClass;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author :         Jason
 * @createDate :     2018/10/12 20:11
 * @description :
 */
@UtilityClass
public class JWTUtil {

    /**
     * 创建 JWT
     *
     * @param info   用户信息
     * @param expire 过期时间(当前时间的多少秒后)
     * @return 生成的 JWT
     * @throws UnsupportedEncodingException 无法加密异常
     */
    public String createToken(JWTBean info, long expire)
            throws UnsupportedEncodingException {
        String secretKey = "shopping-application";
        Algorithm al = Algorithm.HMAC256(secretKey);
        Map<String, Object> header = new HashMap<>(2);
        header.put("typ", "JWT");
        header.put("alg", "HS256");
        return JWT.create()
                .withHeader(header)
                .withClaim("info", JsonUtils.toJson(info))
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + expire * 1000))
                .sign(al);
    }

    /**
     * 检验JWT
     *
     * @param token 令牌
     * @return 包装好的 JWT bean
     */
    public JWTBean verifyToken(String token) {
        if (token == null || "".equals(token)) {
            return null;
        }
        // JWT 解码
        DecodedJWT decode = JWT.decode(token);
        // 获取过期时间
        Date expiresTime = decode.getExpiresAt();
        Claim infoClaim = decode.getClaim("info");
        // 获取信息 json 串
        String info = infoClaim.asString();
        // json 转换成 bean
        JWTBean jwtBean = JsonUtils.toBean(info, JWTBean.class);
        if (jwtBean != null) {
            jwtBean.setExpiresTime(expiresTime);
            try {
                RedisTool.expire(jwtBean.getTokenKey(), RedisConstants.JWT_EXPIRE);
            } catch (Exception e) {

            }
        }
        return jwtBean;
    }

    /**
     * 存放 jwt  token
     *
     * @param jwtBean
     */
    public static String setJwtBean(JWTBean jwtBean, int type) throws Exception {
        String token = JWTUtil.createToken(jwtBean, RedisConstants.JWT_EXPIRE);
        RedisTool.set(jwtBean.getTokenKey(), token, RedisConstants.JWT_EXPIRE);
        return token;
    }


}
