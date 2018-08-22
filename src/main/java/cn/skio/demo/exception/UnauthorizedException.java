package cn.skio.demo.exception;

/**
 * @author zhangbin
 * @date 2018/3/6 13:40
 */
public class UnauthorizedException extends RuntimeException {
  public UnauthorizedException(String msg) {
    super(msg);
  }

  public UnauthorizedException() {
    super();
  }
}
