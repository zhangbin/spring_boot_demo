package cn.skio.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author zhangbin
 * @date 2018/8/22 11:06
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Role extends BaseEntity {
  private Long id;
  private String name;
  private String content;
  private int state;

  private List<Permission> permissions;


}
