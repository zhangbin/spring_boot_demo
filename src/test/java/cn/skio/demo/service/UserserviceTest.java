package cn.skio.demo.service;

import cn.skio.demo.entity.User;
import cn.skio.demo.mapper.UserMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.Assert;

/**
 * Created by zhangbin on 2017/7/19.
 */
@RunWith(SpringRunner.class)
@ContextConfiguration
@ActiveProfiles("test")
public class UserserviceTest {


    @TestConfiguration
    static class EmployeeServiceImplTestContextConfiguration {

        @Bean
        public UserService userService() {
            return new UserService();
        }
    }

    @Autowired
    private UserService userService;

    @MockBean
    private UserMapper userMapper;


    @Before
    public void setUp() {
        User user = new User("admin@test.cn","admin");
        user.setId(1L);

        Mockito.when(userMapper.findUserById(user.getId())).thenReturn(user);
    }

    @Test
    public void createUserTest() {
        Long id = userService.createUser("admin", "admin@test.com");
        System.out.println("insert user into db and return id: " + id);
        Assert.notNull(id, "user is save in the database");
    }

    @Test
    public void findByIdTest() {
        User user = userService.findById(1L);
        Assert.isTrue(user.getUsername().equals("admin"), "find user by id success");
    }
}
