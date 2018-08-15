package com.baihoo.blog.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;

import com.baihoo.blog.handler.MyAccessDeniedHandler;
import com.baihoo.blog.service.UserService;
import com.baihoo.blog.service.impl.UserServiceImpl;

/**
 * springSecurity安全配置类
 * 		注意：該類上的注解包含"@Configuration"因此只會被初始化加載
 * 
 * 		springSecurity5.0.0以後角色是角色，權限是權限，兩者不能混爲一談
 * 
 * @author Administrator
 *
 */

@EnableWebSecurity // 配置注解，啓動web認證安全
//@EnableGlobalMethodSecurity(prePostEnabled = true) // 启用方法级别的权限认证
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	private static final String KEY="www.baihoo.com";
	
	/**
	 * 
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		//  允许所有用户访问"/bootstrap/**"和"/index"
		http.authorizeRequests()
				.antMatchers("/bootstrap/css/**", "/bootstrap/js/**", "/bootstrap/fonts/**", "/index").permitAll()// 都可以訪問
				.antMatchers("/h2-console/**").permitAll()																	// 都可以訪問
				.antMatchers("/admins/**").hasAnyAuthority("ROLE_ADMIN")											// 需要相應的授權才能訪問
				.and().formLogin() 																										// 基於form表單登陸驗證
				.loginPage("/login").failureUrl("/login-error") 																// 若用戶錯誤，則跳轉自定義的登陸界面
				.and().rememberMe().key(KEY)																					//启用 remember me
//				.and().exceptionHandling().accessDeniedHandler(accessDeniedHandler())  			// 处理异常，handler方式，拒绝访问就重定向到 403 页面
				.and().exceptionHandling().accessDeniedPage("/403")												// 处理异常，拒绝访问就重定向到 403 页面
				;
		http.csrf().ignoringAntMatchers("/h2-console/**"); 				// 禁用 H2 控制台的 CSRF 防护
		http.headers().frameOptions().sameOrigin();							//允许来自同一来源的H2 控制台的请求
	}
	/**
	 * 
	 */
	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userService());
		auth.authenticationProvider(authenticationProvider());
		
//		若要方便與測試那麽就要和passwordEncoder() 返回的 passwordEncoder() 實現類前呼後應 。認證信息儲存與内存中
//		 auth.inMemoryAuthentication()
//		 .withUser("baihoo").password("12345").roles("ADMIN"); 
	}
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(); // 使用 BCrypt 加密，注意：原生密碼也得采用這個加密類加密
//		return (NoOpPasswordEncoder) NoOpPasswordEncoder.getInstance(); 
	}
	@Bean
	public UserService userService() {
		return new UserServiceImpl();
	}
	@Bean
	public AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
//		UserService 繼承UserDetailsService，實現其 loadUserByUsername() 方法。
		authenticationProvider.setUserDetailsService(userService());
//		设置密码加密方式
		authenticationProvider.setPasswordEncoder(passwordEncoder()); 
		return authenticationProvider;
	}
	@Bean
	public AccessDeniedHandler accessDeniedHandler() {
		return new MyAccessDeniedHandler();
	}
}
