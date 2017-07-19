package cn.skio.demo.mapper;

import cn.skio.demo.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by zhangbin on 2017/6/13.
 */
@Mapper
public interface UserMapper {
    Integer count();

    Long save(User user);

    User findUserById(Long id);

    List<User> findAll();
}
