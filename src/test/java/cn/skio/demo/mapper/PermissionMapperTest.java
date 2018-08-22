package cn.skio.demo.mapper;

import cn.skio.demo.entity.Permission;
import com.ninja_squad.dbsetup.DbSetup;
import com.ninja_squad.dbsetup.Operations;
import com.ninja_squad.dbsetup.destination.DataSourceDestination;
import com.ninja_squad.dbsetup.operation.Operation;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.Assert.*;

/**
 * @author zhangbin
 * @date 2018/8/22 14:24
 */
@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@MybatisTest
@Transactional
public class PermissionMapperTest extends BaseTest {
  @Before
  public void setUp() throws Exception {
    Operation operation = Operations.sequenceOf(
      Operations.deleteAllFrom("user", "role", "users_roles", "permission", "roles_permissions"),
      Operations.insertInto("user")
        .columns("id", "username", "password", "salt", "alive")
        .values(1, "admin", "ffba7c673c2d5b79e4e0dd310f400c47", "YWRtaW5hZG1pbg==", 1)
        .values(2, "test", "ffba7c673c2d5b79e4e0dd310f400c47", "YWRtaW5hZG1pbg==", 1)
        .build(),
      Operations.insertInto("role")
        .columns("id", "name", "content", "state")
        .values(1, "admin", "管理员", 1)
        .values(2, "test", "测试", 1)
        .build(),
      Operations.insertInto("users_roles")
        .columns("id", "user_id", "role_id")
        .values(1, 1, 1)
        .values(2, 1, 2)
        .values(3, 2, 2)
        .build(),

      Operations.insertInto("permission")
        .columns("id", "name", "permission", "resource_type", "available")
        .values(1, "读取用户", "user:read","read", 1)
        .values(2, "删除用户", "user:delete","delete", 1)
        .values(3, "创建用户", "user:create","create", 1)
        .values(4, "编辑用户", "user:edit","edit", 1)
        .build(),
      Operations.insertInto("roles_permissions")
        .columns("id", "role_id", "permission_id")
        .values(1, 1, 1)
        .values(2, 1, 2)
        .values(3, 1, 3)
        .values(4, 1, 4)
        .values(5, 2, 1)
        .build()

    );

    DbSetup dbSetup = new DbSetup(new DataSourceDestination(dataSource), operation);
    dbSetupTracker.launchIfNecessary(dbSetup);
  }

  @Test
  public void findByRoleId() throws Exception {
    List<Permission> permissions = permissionMapper.findByRoleId(1L);
    assertEquals(permissions.size(), 4);
  }

  @Autowired
  private PermissionMapper permissionMapper;
}