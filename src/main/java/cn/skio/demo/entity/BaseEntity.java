package cn.skio.demo.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author zhangbin
 * @date 2018/8/22 11:10
 */
@Data
public abstract class BaseEntity implements Serializable {
  private Long id;
  private Date createdAt;
  private Date updatedAt;
}
