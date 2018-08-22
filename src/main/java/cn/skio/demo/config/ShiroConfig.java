package cn.skio.demo.config;

import cn.skio.demo.auth.AuthRealm;
import cn.skio.demo.auth.JWTFilter;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.mgt.DefaultSessionStorageEvaluator;
import org.apache.shiro.mgt.DefaultSubjectDAO;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import javax.servlet.Filter;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author zhangbin
 * @date 2018/3/6 13:40
 */
@Slf4j
@Configuration
public class ShiroConfig {
  /**
   * 自定义域
   *
   * @return authRealm
   */
  @Bean
  public AuthRealm realm() {
    AuthRealm authRealm = new AuthRealm();
    return authRealm;
  }

  /**
   * ShiroFilterFactoryBean 处理拦截资源文件问题。
   * Filter Chain定义说明
   * 1、一个URL可以配置多个Filter，使用逗号分隔
   * 2、当设置多个过滤器时，全部验证通过，才视为通过
   * 3、部分过滤器可指定参数，如perms，roles
   *
   * @param securityManager securityManager
   * @return filter
   */
  @Bean
  public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager) {
    log.info("=========shiro config========");

    ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
    // 必须设置 SecurityManager
    shiroFilterFactoryBean.setSecurityManager(securityManager);

    //jwt过滤器
    Map<String, Filter> filtersMap = shiroFilterFactoryBean.getFilters();
    filtersMap.put("jwt", new JWTFilter());
    shiroFilterFactoryBean.setFilters(filtersMap);

    shiroFilterFactoryBean.setUnauthorizedUrl("/401");
    // 拦截器
    Map<String, String> filterChainDefinitionMap = new LinkedHashMap<String, String>();
    // swagger接口文档
    filterChainDefinitionMap.put("/swagger-ui.html", "anon");
    filterChainDefinitionMap.put("/configuration/security", "anon");
    filterChainDefinitionMap.put("/configuration/ui", "anon");
    filterChainDefinitionMap.put("/api-docs", "anon");
    filterChainDefinitionMap.put("/v2/api-docs", "anon");
    filterChainDefinitionMap.put("/api/**", "anon");
    // 对外API接口匿名访问
    filterChainDefinitionMap.put("/anon/**", "anon");
    filterChainDefinitionMap.put("/webjars/**", "anon");
    filterChainDefinitionMap.put("/swagger-resources", "anon");
    filterChainDefinitionMap.put("/swagger-resources/configuration/**", "anon");

    // 访问401和404页面不通过我们的Filter
    filterChainDefinitionMap.put("/401", "anon");
    filterChainDefinitionMap.put("/404", "anon");
    // 服务是否正常启动
    filterChainDefinitionMap.put("/", "anon");
    filterChainDefinitionMap.put("/index", "anon");
    // login
    filterChainDefinitionMap.put("/apiLogin", "anon");
    filterChainDefinitionMap.put("/webLogin", "anon");

    filterChainDefinitionMap.put("/**", "jwt");

    shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
    return shiroFilterFactoryBean;
  }

  /**
   * 自定义SecurityManager
   *
   * @return securityManager
   */
  @Bean
  public SecurityManager securityManager() {
    DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
    // 设置realm.
    securityManager.setRealm(realm());
    //注入缓存管理器
    securityManager.setCacheManager(ehCacheManager());
    //关闭shiro自带的session，详情见文档
    //http://shiro.apache.org/session-management.html#SessionManagement-StatelessApplications%28Sessionless%29
    DefaultSubjectDAO subjectDAO = new DefaultSubjectDAO();
    DefaultSessionStorageEvaluator defaultSessionStorageEvaluator = new DefaultSessionStorageEvaluator();
    defaultSessionStorageEvaluator.setSessionStorageEnabled(false);
    subjectDAO.setSessionStorageEvaluator(defaultSessionStorageEvaluator);
    securityManager.setSubjectDAO(subjectDAO);

    return securityManager;
  }

  /**
   * 开启shiro aop注解支持. 使用代理方式; 所以需要开启代码支持;
   *
   * @param securityManager 安全管理器
   * @return 授权Advisor
   */
  @Bean
  public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
    AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor =
      new AuthorizationAttributeSourceAdvisor();

    authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
    return authorizationAttributeSourceAdvisor;
  }

  /**
   * 注解使用的bean，没有则shiro的注解不生效
   *
   * @return bean
   */
  @Bean
  public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
    return new LifecycleBeanPostProcessor();
  }

  /**
   * 注解使用的bean，没有则shiro的注解不生效
   *
   * @return bean
   */
  @Bean
  public static DefaultAdvisorAutoProxyCreator getDefaultAdvisorAutoProxyCreator() {
    return new DefaultAdvisorAutoProxyCreator();
  }

  /**
   * shiro缓存管理器;
   * 需要注入对应的其它的实体类中：
   * 1、安全管理器：securityManager
   * 可见securityManager是整个shiro的核心；
   *
   * @return cacheManager
   */
  @Bean
  public EhCacheManager ehCacheManager() {
    EhCacheManager ehCacheManager = new EhCacheManager();
    ehCacheManager.setCacheManager(getEhCacheFactory().getObject());
    return ehCacheManager;
  }

  /**
   * cache 缓存
   *
   * @return factoryBean
   */
  @Bean
  public EhCacheManagerFactoryBean getEhCacheFactory() {
    EhCacheManagerFactoryBean factoryBean = new EhCacheManagerFactoryBean();
    factoryBean.setConfigLocation(new ClassPathResource("ehcache.xml"));
    factoryBean.setShared(true);
    return factoryBean;
  }
}
