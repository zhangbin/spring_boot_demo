package cn.skio.demo.mapper;

import cn.skio.demo.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by zhangbin on 2017/6/13.
 */
@Mapper
@Repository
public interface UserMapper {
  Integer count();

  User findUserById(Long id);

  List<User> findAll();
}
