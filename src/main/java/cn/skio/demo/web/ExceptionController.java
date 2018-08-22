package cn.skio.demo.web;

import cn.skio.demo.exception.DuplicateException;
import cn.skio.demo.exception.LoginFailedException;
import cn.skio.demo.exception.NotFoundException;
import cn.skio.demo.exception.UnauthorizedException;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.ShiroException;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authz.UnauthenticatedException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * @author zhangbin
 * @date 2018/3/6 13:40
 */
@Slf4j
@RestControllerAdvice
public class ExceptionController {

  /**
   * 捕捉shiro的异常
   *
   * @param e exception
   * @return 异常消息
   */
  @ResponseStatus(HttpStatus.FORBIDDEN)
  @ExceptionHandler({ShiroException.class})
  public String handle401(ShiroException e) {
    log.error(e.getMessage());
    return "没有操作权限";
  }

  @ResponseStatus(HttpStatus.FORBIDDEN)
  @ExceptionHandler({UnauthenticatedException.class})
  public String handle403(UnauthenticatedException e) {
    log.error(e.getMessage());
    return "需要登录才能访问";
  }

  /**
   * LoginFailedException
   *
   * @param ex 捕获的异常
   * @return 异常消息
   */
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler({LoginFailedException.class})
  public String handleLoginFailed(Throwable ex) {
    log.error(ex.getMessage(), ex);
    return ex.getMessage();
  }

  /**
   * 捕捉AuthenticationException
   *
   * @param ex 捕获的异常
   * @return 异常消息
   */
  @ResponseStatus(HttpStatus.UNAUTHORIZED)
  @ExceptionHandler({AuthenticationException.class})
  public String handle401(Throwable ex) {
    log.error(ex.getMessage(), ex);
    return ex.getMessage();
  }

  /**
   * 捕获UnauthorizedException
   *
   * @param ex 捕获的异常
   * @return 异常消息
   */
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler({UnauthorizedException.class})
  public String handle400(Throwable ex) {
    log.error(ex.getMessage(), ex);
    return ex.getMessage();
  }

  /**
   * 捕捉NotFoundException
   *
   * @param ex 捕获的异常
   * @return 异常消息
   */
  @ResponseStatus(HttpStatus.NOT_FOUND)
  @ExceptionHandler({NotFoundException.class, NullPointerException.class})
  public String handle404(Throwable ex) {
    log.error(ex.getMessage(), ex);
    return "没有找找到对应的数据";
  }

  /**
   * 捕捉其他所有异常
   *
   * @param request request
   * @param ex      exception
   * @return 消息
   */

  @ExceptionHandler(Exception.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public String globalException(HttpServletRequest request, Throwable ex) {
    log.error(ex.getMessage(), ex);
    return "系统异常";
  }

  /**
   * 参数校验失败异常处理
   *
   * @param request request
   * @param ex      exception
   * @return responseBean
   */
  @ExceptionHandler({DuplicateKeyException.class})
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public String bindException(HttpServletRequest request, Throwable ex) {
    log.info(ex.getMessage());
    return "参数错误";
  }

  /**
   * 重复操作,重复数据异常处理
   *
   * @param ex exception
   * @return responseBean
   */
  @ExceptionHandler({DuplicateException.class})
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public String duplicateException(Throwable ex) {
    log.info(ex.getMessage());
    return ex.getMessage();
  }

  /**
   * 获取请求状态
   *
   * @param request request
   * @return status
   */
  private HttpStatus getStatus(HttpServletRequest request) {
    Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
    if (statusCode == null) {
      return HttpStatus.INTERNAL_SERVER_ERROR;
    }
    return HttpStatus.valueOf(statusCode);
  }
}
