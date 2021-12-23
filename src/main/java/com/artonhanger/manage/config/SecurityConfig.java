package com.artonhanger.manage.config;

import com.artonhanger.manage.enums.UserRole;
import com.artonhanger.manage.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @Autowired
    private MemberService memberService;


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.rememberMe()
                .key("regnahnotra")
                .userDetailsService(this.memberService)
                .rememberMeParameter("remember-me")
                .tokenValiditySeconds(604800);//토큰 유지 시간(초단위) - 일주일

        http.formLogin()
                .loginPage("/login")
                .permitAll()
        .defaultSuccessUrl("/")
                .and()
                .logout() // 9
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login") // 로그아웃 성공시 리다이렉트 주소
                .invalidateHttpSession(true) // 세션 날리기
                .deleteCookies("remember-me", "JSESSIONID"); //자동 로그인 쿠키, Tomcat이 발급한 세션 유지 쿠키 삭제

        http.authorizeRequests()
                .antMatchers("/collector/**")
                .permitAll()

                .mvcMatchers("/test/**")
                .permitAll()

                .mvcMatchers("/healthz/**")
                .permitAll()

                .mvcMatchers("/forgot")
                .permitAll()

                .mvcMatchers("/register")
                .permitAll()

                .mvcMatchers("/", "/info")
                .hasRole(UserRole.USER.name())

                .antMatchers("/upload")
                .hasRole(UserRole.ARTIST.name())

                .antMatchers("/modify/**")
                .hasAnyRole(UserRole.ARTIST.name(), UserRole.ADMIN.name())

                .antMatchers("/admin/**")
                .hasRole(UserRole.ADMIN.name())
                .anyRequest().authenticated();
    }
    @Override
    protected void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder.authenticationProvider(customAuthenticationProvider());
    }
    @Bean
    public CustomAuthenticationProvider customAuthenticationProvider() {
        return new CustomAuthenticationProvider(passwordEncoder);
    }
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/assets/**");
        web.ignoring().antMatchers("/static/**");
    }

}

