package cn.skio.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author zhangbin
 * @date 2018/8/22 11:06
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Permission extends BaseEntity {
  private Long id;
  private String name;
  private String permission;
  private String resourceType;
  private Integer available;
}
