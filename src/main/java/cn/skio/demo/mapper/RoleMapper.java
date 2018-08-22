package cn.skio.demo.mapper;

import cn.skio.demo.entity.Role;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author zhangbin
 * @date 2018/8/22 11:29
 */
@Mapper
@Repository
public interface RoleMapper {

  List<Role> findByUserId(@Param("userId") Long userId);

  List<Role> findAll();
}
