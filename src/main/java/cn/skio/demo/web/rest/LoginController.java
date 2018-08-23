package cn.skio.demo.web.rest;

import cn.skio.demo.constant.Constant;
import cn.skio.demo.dto.LoginDto;
import cn.skio.demo.entity.User;
import cn.skio.demo.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zhangbin
 * @date 2018/8/22 16:56
 */
@Slf4j
@RestController
@Api(value = "Rest登录", description = "Rest登录")
@RequestMapping("api")
public class LoginController extends BaseRestController {

  @ApiOperation("login")
  @PostMapping("login")
  public Map<String, Object> login(@Validated LoginDto loginUser, BindingResult bindingResult) {

    if (bindingResult.hasErrors()) {
      return validateError(bindingResult);
    }

    User user = userService.login(loginUser);
    Map<String, Object> result = new HashMap<>(1);
    result.put("user", user);
    result.put(Constant.TOKEN, stringRedisTemplate.opsForValue().get(user.getUsername() + "_token"));
    return result;

  }

  @Autowired
  private UserService userService;
  @Autowired
  private StringRedisTemplate stringRedisTemplate;
}
