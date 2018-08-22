package cn.skio.demo.auth;

import cn.skio.demo.entity.User;
import cn.skio.demo.entity.Permission;
import cn.skio.demo.entity.Role;
import cn.skio.demo.service.UserService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.data.redis.core.StringRedisTemplate;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * 用户鉴权
 *
 * @author zhangbin
 */
public class AuthRealm extends AuthorizingRealm {

  @Resource
  private UserService userService;

  @Resource
  private StringRedisTemplate stringRedisTemplate;


  @Override
  public boolean supports(AuthenticationToken token) {
    return token instanceof JWTToken;
  }

  @Override
  protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {

    String username = JWTUtil.getUsername(principalCollection.toString());

    User user = userService.getByUsername(username);
    if (user == null) {
      return null;
    }

    SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();

    List<Role> roles = user.getRoles();
    authorizationInfo.addRoles(roles.stream().map(Role::getName).collect(Collectors.toList()));

    List<Permission> permissions = user.getPermissions();
    for (Permission permission : permissions) {
      authorizationInfo.addStringPermission(permission.getPermission());
    }
    return authorizationInfo;
  }

  @Override
  protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken auth) throws AuthenticationException {
    String token = (String) auth.getCredentials();

    String username = JWTUtil.getUsername(token);

    String redisToken = stringRedisTemplate.opsForValue().get(username + "_token");
    if (null == redisToken || !token.equals(redisToken)) {
      throw new AuthenticationException("token失效");
    } else {
      //自动续命1天
      stringRedisTemplate.opsForValue().set(username + "_token", token, 1, TimeUnit.DAYS);
    }

    if (username == null) {
      throw new AuthenticationException("token失效");
    }

    User user = userService.getByUsername(username);

    if (user == null) {
      throw new UnknownAccountException("用户不存在或被禁用");
    }
    if (JWTUtil.verify(token, user)) {
      return new SimpleAuthenticationInfo(token, token, "auth_realm");
    } else {
      throw new AuthenticationException("token失效");
    }
  }
}
