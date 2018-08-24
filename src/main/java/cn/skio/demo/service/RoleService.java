package cn.skio.demo.service;

import cn.skio.demo.entity.Role;
import cn.skio.demo.mapper.RoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author zhangbin
 * @date 2018/8/23 17:01
 */
@Service
public class RoleService {

  public List<Role> findByUserId(Long userId) {
    return roleMapper.findByUserId(userId);
  }

  @Autowired
  private RoleMapper roleMapper;
}
