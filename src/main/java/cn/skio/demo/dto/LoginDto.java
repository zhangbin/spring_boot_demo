package cn.skio.demo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author zhangbin
 * @date 2018/8/22 16:58
 */
@Data
@ApiModel(value = "loginDto", description = "登录参数")
public class LoginDto {

  @ApiModelProperty(value = "用户名", example = "test")
  @NotBlank(message = "用户名必须填写")
  private String username;

  @ApiModelProperty(value = "密码", example = "password")
  @NotBlank(message = "密码必须听写")
  private String password;
}
