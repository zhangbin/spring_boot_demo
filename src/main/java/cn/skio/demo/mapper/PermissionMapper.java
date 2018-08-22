package cn.skio.demo.mapper;

import cn.skio.demo.entity.Permission;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author zhangbin
 * @date 2018/8/22 14:19
 */
@Mapper
@Repository
public interface PermissionMapper {

  List<Permission> findByRoleId(@Param("roleId") Long roleId);
}
