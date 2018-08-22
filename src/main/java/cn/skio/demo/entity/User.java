package cn.skio.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by zhangbin on 2017/6/13.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User extends BaseEntity {

  private String username;
  private String password;
  private String salt;
  private Integer alive;

  private List<Role> roles;


  public String getRoleNames() {
    return this.getRoles().stream().map(Role::getName).collect(Collectors.joining(";"));
  }

}
