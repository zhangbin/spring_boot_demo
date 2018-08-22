package cn.skio.demo.auth;

import cn.skio.demo.entity.User;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.util.Date;

/**
 * @author zhangbin
 * @date 2018/3/6 13:40
 */
public final class JWTUtil {
  private static final Logger LOGGER = LoggerFactory.getLogger(JWTUtil.class);

  private JWTUtil() {
  }

  // 过期时间1天
  private static final long EXPIRE_TIME = 60 * 24 * 60 * 1000;

  /**
   * 校验token是否正确
   *
   * @param token token
   * @param user  用户信息
   * @return 是否正确
   */
  public static boolean verify(String token, User user) {
    try {
      Algorithm algorithm = Algorithm.HMAC256(user.getSalt());
      JWTVerifier verifier = JWT.require(algorithm)
        .withClaim("user_id", user.getId())
        .withClaim("username", user.getUsername())
        .build();
      DecodedJWT jwt = verifier.verify(token);
      return true;
    } catch (Exception exception) {
      LOGGER.error("校验token失败", exception);
      return false;
    }
  }

  /**
   * 获得token中的信息无需secret解密也能获得
   *
   * @param token token
   * @return token中包含的username
   */
  public static String getUsername(String token) {
    try {
      DecodedJWT jwt = JWT.decode(token);
      return jwt.getClaim("username").asString();
    } catch (JWTDecodeException e) {
      return null;
    }
  }



  /**
   * 生成签名
   *
   * @param user 用户信息
   * @return 加密的token
   */
  public static String sign(User user) {
    try {
      Date date = new Date(System.currentTimeMillis() + EXPIRE_TIME);
      Algorithm algorithm = Algorithm.HMAC256(user.getSalt());
      // 附带username信息
      return JWT.create()
        .withClaim("user_id", user.getId())
        .withClaim("username", user.getUsername())
        .sign(algorithm);
    } catch (UnsupportedEncodingException e) {
      return null;
    }
  }

}
