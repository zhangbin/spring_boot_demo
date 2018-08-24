package cn.skio.demo.service;

import cn.skio.demo.entity.Permission;
import cn.skio.demo.mapper.PermissionMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author zhangbin
 * @date 2018/8/24 11:07
 */
@Slf4j
@Service
public class PermissionService {

  public List<Permission> findByUserId(Long userId) {
    return permissionMapper.findByUserId(userId);
  }

  @Autowired
  private PermissionMapper permissionMapper;
}
