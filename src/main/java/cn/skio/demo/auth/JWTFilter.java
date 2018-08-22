package cn.skio.demo.auth;

import cn.skio.demo.constant.Constant;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author zhangbin
 * @date 2018/3/6 13:40
 */
@Slf4j
public class JWTFilter extends BasicHttpAuthenticationFilter {

  /**
   * 不需要登录的请求已经在shiro中过滤
   */
  @Override
  protected boolean isLoginAttempt(ServletRequest request, ServletResponse response) {
    HttpServletRequest req = (HttpServletRequest) request;
    String authorization = req.getHeader(Constant.TOKEN);
    if (authorization == null) {
      authorization = (String) req.getSession().getAttribute(Constant.TOKEN);
      return StringUtils.isNotBlank(authorization);
    } else {
      return true;
    }
  }

  @Override
  protected boolean executeLogin(ServletRequest request, ServletResponse response) throws Exception {
    HttpServletRequest httpServletRequest = (HttpServletRequest) request;
    String authorization = httpServletRequest.getHeader(Constant.TOKEN);

    authorization = StringUtils.isBlank(authorization)
      ? (String) ((HttpServletRequest) request).getSession().getAttribute(Constant.TOKEN) : authorization;

    JWTToken token = new JWTToken(authorization);
    // 提交给realm进行登入，如果错误他会抛出异常并被捕获
    getSubject(request, response).login(token);
    // 如果没有抛出异常则代表登入成功，返回true
    return true;
  }


  /**
   * 如果在这里返回了false，请求会被直接拦截，用户看不到任何东西
   * 所以我们在这里返回true，Controller中可以通过 subject.isAuthenticated() 来判断用户是否登入
   * 如果有些资源只有登入用户才能访问，我们只需要在方法上面加上 @RequiresAuthentication 注解即可
   * 但是这样做有一个缺点，就是不能够对GET,POST等请求进行分别过滤鉴权(因为我们重写了官方的方法)，但实际上对应用影响不大
   */
  @Override
  protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
    if (isLoginAttempt(request, response)) {
      try {
        executeLogin(request, response);
      } catch (Exception e) {
        response401(request, response);
      }
    }
    return true;
  }

  /**
   * 对跨域提供支持
   */
  @Override
  protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
    HttpServletRequest httpServletRequest = (HttpServletRequest) request;
    HttpServletResponse httpServletResponse = (HttpServletResponse) response;
    httpServletResponse.setHeader("Access-control-Allow-Origin", httpServletRequest.getHeader("Origin"));
    httpServletResponse.setHeader("Access-Control-Allow-Methods", "GET,POST,OPTIONS,PUT,DELETE");
    httpServletResponse.setHeader("Access-Control-Allow-Headers",
      httpServletRequest.getHeader("Access-Control-Request-Headers"));
    // 跨域时会首先发送一个option请求，这里我们给option请求直接返回正常状态
    if (httpServletRequest.getMethod().equals(RequestMethod.OPTIONS.name())) {
      httpServletResponse.setStatus(HttpStatus.OK.value());
      return false;
    }
    return super.preHandle(request, response);
  }

  /**
   * 将非法请求跳转到 /401
   *
   * @param req  request
   * @param resp response
   */
  private void response401(ServletRequest req, ServletResponse resp) {
    try {
      HttpServletResponse httpServletResponse = (HttpServletResponse) resp;
      httpServletResponse.sendRedirect("/401");
    } catch (IOException e) {
      log.error(e.getMessage());
    }
  }
}
