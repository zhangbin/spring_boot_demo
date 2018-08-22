package cn.skio.demo.cache;

import cn.skio.demo.entity.Permission;
import cn.skio.demo.entity.Role;
import cn.skio.demo.mapper.PermissionMapper;
import cn.skio.demo.mapper.RoleMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * cache role and permission
 *
 * @author zhangbin
 * @date 2018/8/22 14:14
 */
@Slf4j
@Component
public class RolePermissionLoader implements CommandLineRunner {

  @Override
  public void run(String... args) throws Exception {
    log.info("========load roles and permissions========");
    List<Role> roles = roleMapper.findAll();
    roles.forEach(role -> {
      role.setPermissions(permissionMapper.findByRoleId(role.getId()));
      redisTemplate.opsForValue().set("role_" + role.getId(), role);
    });
  }

  @Autowired
  private RoleMapper roleMapper;
  @Autowired
  private PermissionMapper permissionMapper;
  @Autowired
  private RedisTemplate redisTemplate;
}
