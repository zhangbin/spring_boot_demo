package cn.skio.demo.web.thymeleaf;

import cn.skio.demo.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author zhangbin
 * @date 2018/8/22 16:14
 */
@Slf4j
@Controller
@RequestMapping("users")
public class UserController {

  @GetMapping("")
  @RequiresPermissions("user:read")
  public String index(Model model) {
    model.addAttribute("users", userService.all());
    return "user/index";
  }

  @Autowired
  private UserService userService;
}
