package cn.skio.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by zhangbin on 2017/6/13.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"salt", "password"})
public class User extends BaseEntity {

  private String username;
  private String password;
  private String salt;
  private Integer alive;

  private List<Role> roles = new ArrayList<>();
  private List<Permission> permissions = new ArrayList<>();


  public String getRoleNames() {
    return this.getRoles().stream().map(Role::getContent).collect(Collectors.joining(";"));
  }

}
