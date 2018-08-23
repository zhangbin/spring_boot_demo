package cn.skio.demo.service;

import cn.skio.demo.auth.JWTUtil;
import cn.skio.demo.auth.ShiroKit;
import cn.skio.demo.dto.LoginDto;
import cn.skio.demo.entity.Role;
import cn.skio.demo.entity.User;
import cn.skio.demo.exception.LoginFailedException;
import cn.skio.demo.mapper.RoleMapper;
import cn.skio.demo.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by zhangbin on 2017/6/13.
 */
@Slf4j
@Service
public class UserService {

  public List<User> all() {
    return userMapper.findAll();
  }

  public User login(LoginDto loginUser) throws LoginFailedException {
    User user = getByUsername(loginUser.getUsername());
    if (user == null) {
      throw new LoginFailedException("用户不存在");
    }
    String encodedPassword = ShiroKit.md5(loginUser.getPassword(), user.getSalt());
    if (user.getPassword().equals(encodedPassword)) {
      String token = JWTUtil.sign(user);
      stringRedisTemplate.opsForValue().set(user.getUsername() + "_token", token, 1, TimeUnit.DAYS);
      log.info("用户：" + user.getUsername() + "登录成功");
      return user;
    } else {
      throw new LoginFailedException("用户密码错误");
    }
  }

  public User getByUsername(String username) {
    User user = userMapper.getByUsername(username);
    if (user == null) {
      return null;
    }

    List<Role> roles = roleMapper.findByUserId(user.getId());

    roles.forEach(role -> {
      Role cacheRole = (Role) redisTemplate.opsForValue().get("role_" + role.getId());
      cacheRole.getPermissions().forEach(permission -> {
        if (!user.getPermissions().contains(permission)) {
          user.getPermissions().add(permission);
        }
      });
    });

    user.setRoles(roles);
    return user;
  }

  @Autowired
  private UserMapper userMapper;

  @Autowired
  private RoleMapper roleMapper;
  @Autowired
  private RedisTemplate redisTemplate;
  @Autowired
  private StringRedisTemplate stringRedisTemplate;
}
