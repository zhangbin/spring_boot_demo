package cn.skio.demo.mapper;

import cn.skio.demo.entity.User;
import com.ninja_squad.dbsetup.DbSetup;
import com.ninja_squad.dbsetup.DbSetupTracker;
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

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by zhangbin on 2017/7/19.
 */
@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@MybatisTest
@Transactional
public class UserMapperTest extends BaseTest{
  @Autowired
  private UserMapper userMapper;

  @Before
  public void setup() {
    Operation operation = Operations.sequenceOf(
      Operations.deleteAllFrom("user"),
      Operations.insertInto("user")
        .columns("id", "username", "password", "salt", "alive")
        .values(1, "test", "ffba7c673c2d5b79e4e0dd310f400c47", "YWRtaW5hZG1pbg==", 1)
        .build()
    );

    DbSetup dbSetup = new DbSetup(new DataSourceDestination(dataSource), operation);
    dbSetupTracker.launchIfNecessary(dbSetup);
  }

  @Test
  public void testFindAll() {
    List<User> users = userMapper.findAll();
    assertEquals(users.size(),1);
    assertEquals(users.get(0).getUsername(), "test");
  }

}
