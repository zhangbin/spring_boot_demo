package cn.skio.demo.web.graphql;

import cn.skio.demo.entity.User;
import cn.skio.demo.service.UserService;
import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author zhangbin
 * @date 2018/8/23 09:33
 */
@Component
public class Query implements GraphQLQueryResolver {

  public List<User> findAll() {
    return userService.all();
  }

  @RequiresPermissions("user:read")
  public User findByUsername(String username) {
    return userService.getByUsername(username);
  }

  @Autowired
  private UserService userService;
}
