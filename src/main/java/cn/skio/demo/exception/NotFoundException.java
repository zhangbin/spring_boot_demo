package cn.skio.demo.exception;

/**
 * 404
 *
 * @author zhangbin
 * @date 2018/3/29 16:36
 */
public class NotFoundException extends Exception {
  public NotFoundException(String message) {
    super(message);
  }
}
