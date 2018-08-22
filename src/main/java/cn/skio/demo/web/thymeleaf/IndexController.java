package cn.skio.demo.web.thymeleaf;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author zhangbin
 * @date 2018/8/22 14:43
 */
@Controller
public class IndexController {

  @GetMapping("/index")
  public String index() {
    return "index";
  }
}
