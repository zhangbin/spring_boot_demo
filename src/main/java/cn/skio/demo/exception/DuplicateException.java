package cn.skio.demo.exception;

/**
 * 重复操作异常
 *
 * @author zhangbin
 * @date 2018/3/24 14:15
 */
public class DuplicateException extends Exception {

  public DuplicateException(String message) {
    super(message);
  }
}
