package cn.skio.demo.mapper;

import com.ninja_squad.dbsetup.DbSetupTracker;

import javax.annotation.Resource;
import javax.sql.DataSource;

/**
 * @author zhangbin
 * @date 2018/8/22 13:57
 */
public abstract class BaseTest {
  protected static DbSetupTracker dbSetupTracker = new DbSetupTracker();
  @Resource
  protected DataSource dataSource;
}
