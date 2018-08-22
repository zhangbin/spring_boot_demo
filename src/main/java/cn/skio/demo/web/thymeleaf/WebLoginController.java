package cn.skio.demo.web.thymeleaf;

import cn.skio.demo.constant.Constant;
import cn.skio.demo.entity.User;
import cn.skio.demo.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.security.auth.login.LoginException;
import javax.servlet.http.HttpSession;

/**
 * @author zhangbin
 * @date 2018/8/22 15:16
 */
@Slf4j
@Controller
public class WebLoginController {

  @PostMapping("webLogin")
  public String login(User user, Model model, RedirectAttributes redirectAttributes, HttpSession session) {
    try {
      User loginUser = userService.login(user);
      model.addAttribute("user", loginUser);
      model.addAttribute("users", userService.all());
      session.setAttribute(Constant.TOKEN, stringRedisTemplate.opsForValue().get(user.getUsername() + "_token"));
    } catch (LoginException e) {
      redirectAttributes.addFlashAttribute("error", e.getMessage());
      return "redirect:/index";
    }
    return "user/index";
  }

  @Autowired
  private UserService userService;
  @Autowired
  private StringRedisTemplate stringRedisTemplate;
}
