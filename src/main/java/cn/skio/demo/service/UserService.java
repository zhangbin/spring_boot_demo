package cn.skio.demo.service;

import cn.skio.demo.entity.User;
import cn.skio.demo.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by zhangbin on 2017/6/13.
 */
@Service
public class UserService {

    @Autowired
    private UserMapper userDao;

    public User findById(Long id) {
        try {
            return userDao.findUserById(id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<User> all(){
        return userDao.findAll();
    }
}
