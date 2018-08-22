package cn.skio.demo.web;

import cn.skio.demo.entity.User;
import cn.skio.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by zhangbin on 2017/6/13.
 */
@RestController
public class Hello {

    @Autowired
    private UserService userService;

    @RequestMapping("/users")
    public List<User> users(){
        return userService.all();
    }
}
