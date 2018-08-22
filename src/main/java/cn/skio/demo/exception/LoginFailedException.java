package cn.skio.demo.exception;

/**
 * @author zhangbin
 * @date 2018/8/7 17:06
 */
public class LoginFailedException extends RuntimeException {

  public LoginFailedException(String s) {
    super(s);
  }
}
