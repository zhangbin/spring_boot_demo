package cn.skio.demo.web.rest;

import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author zhangbin
 * @date 2018/8/22 17:05
 */
public class BaseRestController {
  /**
   * 数据校验失败
   *
   * @param bindingResult 校验信息
   * @return map
   */
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  protected Map<String,Object> validateError(BindingResult bindingResult) {
    List<Map<String, String>> errors = bindingResult.getFieldErrors().stream().map(error -> {
      Map<String, String> map = new HashMap<>();
      if (error.getDefaultMessage().contains(DATE_ERROR)) {
        map.put(error.getField(), "时间格式不正确");
      } else {
        map.put(error.getField(), error.getDefaultMessage());
      }
      return map;
    }).collect(Collectors.toList());

    Map<String, Object> result = new HashMap<>(1);
    result.put("errors", errors);
    return result;
  }

  private static final String DATE_ERROR = "'java.lang.String' to required type 'java.util.Date'";
}
