package cn.skio.demo.web.graphql;

import cn.skio.demo.entity.Permission;
import cn.skio.demo.entity.Role;
import cn.skio.demo.entity.User;
import cn.skio.demo.service.PermissionService;
import cn.skio.demo.service.RoleService;
import com.coxautodev.graphql.tools.GraphQLResolver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author zhangbin
 * @date 2018/8/24 10:33
 */
@Slf4j
@Component
public class UserResolver implements GraphQLResolver<User> {

  public List<Role> roles(User user) {
    return roleService.findByUserId(user.getId());
  }

  public List<Permission> permissions(User user) {
    return permissionService.findByUserId(user.getId());
  }

  @Autowired
  private RoleService roleService;
  @Autowired
  private PermissionService permissionService;
}
