package com.demo.erpmanage.config;

import com.demo.erpmanage.entity.SysPermission;
import com.demo.erpmanage.service.SysPermissionService;
import com.demo.erpmanage.service.SysUserService;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.OncePerRequestFilter;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import javax.servlet.Filter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


@Configuration
public class ShiroConfig {
    @Resource
    private SysUserService userService;
    @Resource
    private SysPermissionService permissionService;

    //注入redis参数
    //@Value("${spring.redis.host}")
    //private String host;
    //@Value("${spring.redis.port}")
    // int port;
    /*@Value("${spring.redis.password}")
    private String password;*/
    //@Value("${spring.redis.timeout}")
    //private int timeout;

    /**
     * 配置thymeleaf页面对shiro标签的支持
     */
    /*@Bean(name = "shiroDialect")
    public ShiroDialect shiroDialect() {
        return new ShiroDialect();
    }*/

    /**
     * cookie管理对象
     *
     * @return
     */
    @Bean
    public CookieRememberMeManager rememberMeManager() {
        CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
        cookieRememberMeManager.setCookie(rememberMeCookie());
        //rememberMe cookie加密的密钥 建议每个项目都不一样 默认AES算法 密钥长度(128 256 512 位)
        cookieRememberMeManager.setCipherKey(Base64.decode("4AvVhmFLUs0KTA3Kprsdag=="));
        return cookieRememberMeManager;
    }

    @Bean
    public SimpleCookie rememberMeCookie() {
        //这个参数是cookie的名称，对应前端的checkbox的name = rememberMe
        SimpleCookie simpleCookie = new SimpleCookie("rememberMe");
        //setcookie的httponly属性如果设为true的话，会增加对xss防护的安全系数。它有以下特点：

        //setcookie()的第七个参数
        //设为true后，只能通过http访问，javascript无法访问
        //防止xss读取cookie
        simpleCookie.setHttpOnly(true);
        simpleCookie.setPath("/");
        //<!-- 记住我cookie生效时间,单位秒;-->
        simpleCookie.setMaxAge(30);
        return simpleCookie;
    }

    @Bean
    public MyShiroRealm myShiroRealm() {
        System.out.println("2：myShiroRealm..........");
        MyShiroRealm myShiroRealm = new MyShiroRealm();
        //启动缓存及设置缓存名称
        /*myShiroRealm.setCachingEnabled(true);
        myShiroRealm.setAuthorizationCachingEnabled(true);
        myShiroRealm.setAuthorizationCacheName("authorizationCache");*/
        return myShiroRealm;
    }

    @Bean
    public SecurityManager securityManager() {
        System.out.println("1：securityManager..........");
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRememberMeManager(rememberMeManager());
        securityManager.setRealm(myShiroRealm());
        // 自定义缓存实现 使用redis
        //securityManager.setCacheManager(cacheManager());
        // 自定义session管理 使用redis
        //securityManager.setSessionManager(sessionManager());
        return securityManager;
    }

    /**
     * cacheManager 缓存 redis实现
     * 使用的是shiro-redis开源插件
     *
     * @return
     */
   /* public RedisCacheManager cacheManager() {
        RedisCacheManager redisCacheManager = new RedisCacheManager();
        redisCacheManager.setRedisManager(redisManager());
        //缓存名称
        redisCacheManager.setPrincipalIdFieldName("loginname");
        // 配置缓存过期时间  单位是秒   1800秒/60秒=30分钟
        redisCacheManager.setExpire(1800);
        return redisCacheManager;
    }*/

    /**
     * Session Manager
     * 使用的是shiro-redis开源插件
     */
    /*@Bean
    public DefaultWebSessionManager sessionManager() {
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        sessionManager.setSessionDAO(redisSessionDAO());
        return sessionManager;
    }*/

    /**
     * 配置shiro redisManager
     * 使用的是shiro-redis开源插件
     *
     * @return
     */
    /*public RedisManager redisManager() {
        RedisManager redisManager = new RedisManager();
        redisManager.setHost(host);
        redisManager.setPort(port);
        //redisManager.setPassword(password);
        redisManager.setTimeout(timeout);
        return redisManager;
    }*/

    /**
     * RedisSessionDAO shiro sessionDao层的实现 通过redis
     * 使用的是shiro-redis开源插件
     */
    /*@Bean
    public RedisSessionDAO redisSessionDAO() {
        RedisSessionDAO redisSessionDAO = new RedisSessionDAO();
        redisSessionDAO.setRedisManager(redisManager());
        return redisSessionDAO;
    }*/
    @Bean
    public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager) {
        System.out.println("3：ShiroConfiguration.shiroFilter()：配置权限控制规则");
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);

        //登录提交地址
        shiroFilterFactoryBean.setLoginUrl("/sys/toLogin");
        //访问没有授权的资源
        shiroFilterFactoryBean.setUnauthorizedUrl("/resource/page/404");
        Map<String, String> filtrChainDefinitionMap = new LinkedHashMap<String, String>();
        //不登录可以访问的地址
        filtrChainDefinitionMap.put("/sys/toLogin", "anon");
        filtrChainDefinitionMap.put("/sys/login", "anon");
        filtrChainDefinitionMap.put("/resource/css/**", "anon");
        filtrChainDefinitionMap.put("/resource/images/**", "anon");
        filtrChainDefinitionMap.put("/resource/js/**", "anon");
        filtrChainDefinitionMap.put("/resource/json/**", "anon");
        filtrChainDefinitionMap.put("/resource/layui/*/**", "anon");
        filtrChainDefinitionMap.put("/resource/layui_ext/*/**", "anon");
        filtrChainDefinitionMap.put("/resource/page/**", "anon");
        //配置退出（记住我状态下，可清除记住我的cookie）
        filtrChainDefinitionMap.put("/sys/loginOut", "logout");

        //设置会被拦截的请求
        List<SysPermission> permissions = permissionService.findAllPermission();
        for (SysPermission permission : permissions) {
            if (permission.getType().equals("menu") && permission.getHref() != null && !permission.getHref().equals("")) {
                filtrChainDefinitionMap.put(permission.getHref(), "perms[" + permission.getTitle() + "]");
            }
        }

        //所有路径必须授权访问（登录），且必须放在最后
        filtrChainDefinitionMap.put("/**", "user");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filtrChainDefinitionMap);

        //解决session丢失
        Map<String, Filter> fmap = new HashMap<>();
        fmap.put("addPrincipal", addPrincipalToSessionFilter());
        shiroFilterFactoryBean.setFilters(fmap);

        return shiroFilterFactoryBean;
    }


    /**
     * Shiro自定义过滤器
     *
     * @return
     */
    @Bean
    public OncePerRequestFilter addPrincipalToSessionFilter() {
        return new AddPrincipalToSessionFilter();
    }

}
