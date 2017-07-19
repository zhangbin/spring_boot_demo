package cn.skio.demo.mapper;

import cn.skio.demo.entity.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by zhangbin on 2017/7/19.
 */
@RunWith(SpringRunner.class)
@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
public class UserMapperTest {
    @Autowired
    private UserMapper userMapper;

    @Before
    public void setup(){

    }

    @Test
    public void findByStateTest() {
        User user = new User("admin@test.cn","admin");
        Long id = userMapper.save(user);
        System.out.println("insert into users and return id :" + id);

        User test_user = userMapper.findUserById(id);
        assertThat(user.getUsername()).isEqualTo("admin");
    }

}
