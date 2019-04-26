//package com.cxg.weChat.core.config;
//
//
//import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
//import com.cxg.weChat.core.shiro.RedisCacheManager;
//import com.cxg.weChat.core.shiro.RedisManager;
//import com.cxg.weChat.core.shiro.RedisSessionDAO;
//import com.cxg.weChat.core.shiro.UserRealm;
//import org.apache.shiro.cache.ehcache.EhCacheManager;
//import org.apache.shiro.mgt.SecurityManager;
//import org.apache.shiro.session.SessionListener;
//import org.apache.shiro.session.mgt.eis.MemorySessionDAO;
//import org.apache.shiro.session.mgt.eis.SessionDAO;
//import org.apache.shiro.spring.LifecycleBeanPostProcessor;
//import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
//import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
//import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
//import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import java.util.ArrayList;
//import java.util.Collection;
//import java.util.LinkedHashMap;
//
///**
//* @Description:    Shrio Config
//* @Author:         Cheney Master
//* @CreateDate:     2018/8/15 14:07
//* @Version:        1.0
//*/
//
//@Configuration
//public class ShiroConfig {
//
//    @Value("${spring.redis.host}")
//    private String host;
//
//    @Value("${spring.redis.password}")
//    private String password;
//
//    @Value("${spring.redis.port}")
//    private int port;
//
//    @Value("${spring.redis.timeout}")
//    private int timeout;
//
//    @Value("${cacheType}")
//    private String cacheType;
//
//    @Value("${server.session-timeout}")
//    private int tomcatTimeout;
//
//    /**
//     * LifecycleBeanPostProcessor
//     * 用来管理shiro一些bean的生命周期
//     * 将Initializable和Destroyable的实现类统一在其内部自动分别调用了
//     * Initializable.init()和Destroyable.destroy()方法，
//     * 从而达到管理shiro bean生命周期的目的
//     * @return
//     */
//    @Bean
//    public static LifecycleBeanPostProcessor getLifecycleBeanPostProcessor() {
//        return new LifecycleBeanPostProcessor();
//    }
//
//    /**
//     * ShiroDialect，为了在thymeleaf里使用shiro的标签的bean
//     * @return
//     */
//    @Bean
//    public ShiroDialect shiroDialect() {
//        return new ShiroDialect();
//    }
//
//    /**
//     * shiro过滤设置
//     * 创建一些默认的过滤器对客户端请求进行过滤
//     * @param securityManager
//     * @return
//     */
//	@Bean
//    ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager) {
//		ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
//		shiroFilterFactoryBean.setSecurityManager(securityManager);
//		shiroFilterFactoryBean.setLoginUrl("/login");
//		shiroFilterFactoryBean.setSuccessUrl("/index");
//		shiroFilterFactoryBean.setUnauthorizedUrl("/403");
//		LinkedHashMap<String, String> filterChainDefinitionMap = new LinkedHashMap<String, String>();
//		filterChainDefinitionMap.put("/css/**", "anon");
//		filterChainDefinitionMap.put("/js/**", "anon");
//		filterChainDefinitionMap.put("/fonts/**", "anon");
//		filterChainDefinitionMap.put("/img/**", "anon");
//		filterChainDefinitionMap.put("/docs/**", "anon");
//		filterChainDefinitionMap.put("/druid/**", "anon");
//		filterChainDefinitionMap.put("/upload/**", "anon");
//		filterChainDefinitionMap.put("/files/**", "anon");
//		filterChainDefinitionMap.put("/logout", "logout");
//		filterChainDefinitionMap.put("/", "anon");
//		filterChainDefinitionMap.put("/blog", "anon");
//		filterChainDefinitionMap.put("/blog/open/**", "anon");
//		filterChainDefinitionMap.put("/**", "authc");
//		shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
//		return shiroFilterFactoryBean;
//	}
//
//    /**
//     * 安全管理器 securityManager
//     * 即所有与安全有关的操作都会与 SecurityManager 交互，且它管理着所有 Subject
//     * Shiro 的核心
//     * 可以把它看成 DispatcherServlet 前端控制器
//     * @return
//     */
//    @Bean
//    public SecurityManager securityManager(){
//        DefaultWebSecurityManager securityManager =  new DefaultWebSecurityManager();
//        //设置realm.
//        securityManager.setRealm(userRealm());
//        // 自定义缓存实现 使用redis
//        if(Constant.CACHE_TYPE_REDIS.equals(cacheType)){
//            securityManager.setCacheManager(cacheManager());
//        }else {
//            securityManager.setCacheManager(ehCacheManager());
//        }
//        securityManager.setSessionManager(sessionManager());
//        return securityManager;
//    }
//
//    /**
//     * shiro用户角色验证
//     * @return
//     */
//	@Bean
//    UserRealm userRealm() {
//		UserRealm userRealm = new UserRealm();
//		return userRealm;
//	}
//
//    /**
//     *  开启shiro aop注解支持.
//     *  使用代理方式;所以需要开启代码支持;
//     * @param securityManager
//     * @return
//     */
//    @Bean
//    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager){
//        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
//        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
//        return authorizationAttributeSourceAdvisor;
//    }
//
//    /**
//     * 配置shiro redisManager
//     * @return
//     */
//    @Bean
//    public RedisManager redisManager() {
//        RedisManager redisManager = new RedisManager();
//        redisManager.setHost(host);
//        redisManager.setPort(port);
//        redisManager.setExpire(1800);// 配置缓存过期时间
//        //redisManager.setTimeout(1800);
//        redisManager.setPassword(password);
//        return redisManager;
//    }
//
//    /**
//     * cacheManager 缓存 redis实现
//     * 使用的是shiro-redis开源插件
//     * @return
//     */
//    public RedisCacheManager cacheManager() {
//        RedisCacheManager redisCacheManager = new RedisCacheManager();
//        redisCacheManager.setRedisManager(redisManager());
//        return redisCacheManager;
//    }
//
//
//    /**
//     * RedisSessionDAO shiro sessionDao层的实现 通过redis
//     * 使用的是shiro-redis开源插件
//     */
//    @Bean
//    public RedisSessionDAO redisSessionDAO() {
//        RedisSessionDAO redisSessionDAO = new RedisSessionDAO();
//        redisSessionDAO.setRedisManager(redisManager());
//        return redisSessionDAO;
//    }
//
//    @Bean
//    public SessionDAO sessionDAO(){
//        if(Constant.CACHE_TYPE_REDIS.equals(cacheType)){
//            return redisSessionDAO();
//        }else {
//            return new MemorySessionDAO();
//        }
//    }
//
//    /**
//     * shiro session的管理
//     */
//    @Bean
//    public DefaultWebSessionManager sessionManager() {
//        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
//        sessionManager.setGlobalSessionTimeout(tomcatTimeout*1000);
//        sessionManager.setSessionDAO(sessionDAO());
//        Collection<SessionListener> listeners = new ArrayList<SessionListener>();
//        listeners.add(new BDSessionListener());
//        sessionManager.setSessionListeners(listeners);
//        return sessionManager;
//    }
//
//    @Bean
//    public EhCacheManager ehCacheManager() {
//        EhCacheManager em = new EhCacheManager();
//        em.setCacheManagerConfigFile("classpath:config/ehcache.xml");
//        return em;
//    }
//
//}
