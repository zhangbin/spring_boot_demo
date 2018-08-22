package cn.skio.demo.auth;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * @author zhangbin
 * @date 2018/3/6 13:40
 */
public class JWTToken implements AuthenticationToken {

  // 密钥
  private String token;

  public JWTToken(String token) {
    this.token = token;
  }

  @Override
  public Object getPrincipal() {
    return token;
  }

  @Override
  public Object getCredentials() {
    return token;
  }

  public String getToken() {
    return token;
  }
}
