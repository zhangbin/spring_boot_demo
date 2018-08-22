package cn.skio.demo.config;

import cn.skio.demo.constant.Constant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

/**
 * swagger api config
 *
 * @author zhangbin
 * @date 2018/3/6 13:40
 */
@Slf4j
@Configuration
@EnableSwagger2
public class Swagger2Config {
  /**
   * 创建docket
   *
   * @return docket
   */
  @Bean
  public Docket createRestApi() {
    log.info("========swagger config========");
    ApiInfo apiInfo = new ApiInfoBuilder()
      .title("api")
      .description("spring boot demo api")
      .version("1.0")
      .build();

    ParameterBuilder tokenPar = new ParameterBuilder();
    List<Parameter> pars = new ArrayList<>();
    tokenPar.name(Constant.TOKEN).description("令牌").modelRef(
      new ModelRef("string")).parameterType("header").required(false).build();
    pars.add(tokenPar.build());

    return new Docket(DocumentationType.SWAGGER_2)
      .apiInfo(apiInfo)
      .select()
      .apis(RequestHandlerSelectors.basePackage("cn.skio.demo"))
      .paths(PathSelectors.any())
      .build().globalOperationParameters(pars);
  }

}
