package com.tini.tiniprojectbackend.common.config;

import com.tini.tiniprojectbackend.common.security.filter.JWTFilter;
import com.tini.tiniprojectbackend.common.security.handler.CustomLogoutHandler;
import com.tini.tiniprojectbackend.common.util.JWTUtil;
import com.tini.tiniprojectbackend.user.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Slf4j
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig implements WebMvcConfigurer {

  private final JWTUtil jwtUtil;
  private final UserService userService;
  private final CustomLogoutHandler customLogoutHandler;

  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration)
      throws Exception {
    return configuration.getAuthenticationManager();
  }


  public void addCorsMappings(CorsRegistry registry) {
    registry.addMapping("/**")
        .allowedOrigins("http://localhost:3000")
        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
        .allowedHeaders("*")
        .exposedHeaders("token-expired", "Authorization", "RefreshToken")
        .allowCredentials(true);
  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http,
      AuthenticationManager authenticationManager) throws Exception {
    http
        .csrf(csrf -> csrf.disable())
        .cors(cors -> {
        })
        .formLogin(form -> form.disable())
        .httpBasic(basic -> basic.disable())

        .authorizeHttpRequests(auth -> auth
            // 정적 리소스 및 공개 URL 허용
            .requestMatchers("/{spring:[a-zA-Z0-9-_]+}", "/{spring:[a-zA-Z0-9-_]+}/**").permitAll()
            .requestMatchers("/.well-known/**", "/error", "/photo/**").permitAll()
            .requestMatchers("/", "/index.html", "/favicon.ico", "/static/**", "/manifest.json",
                "/public/**", "/auth/**", "/css/**", "/js/**", "/*.png").permitAll()

            // 인증 없이 허용되는 API 경로 추가
            .requestMatchers(
                "/api/v1/tini/login",
                "/api/v1/tini/reissue",
                "/api/v1/tini/user/send-verification-code",
                "/api/v1/tini/user/confirm-verification-code",
                "/api/v1/tini/user/reset-password-by-email",
                "/api/v1/tini/user/kakao/**",  // 카카오 소셜 로그인 경로 허용
                "/api/v1/tini/user/google/**",  // 네이버 소셜 로그인 경로 허용
                "/api/v1/tini/test/apple/**",  // 소셜 로그인 테스트 경로 허용
                "/user/signup"
            ).permitAll()

            // 인증 필요
            .requestMatchers("/api/v1/tini/logout").authenticated()

            // 관리자 권한 필요
            .requestMatchers("/admin/**").hasRole("ADMIN")

            // 사용자/관리자 권한 모두 허용
            .requestMatchers(HttpMethod.DELETE, "/user/delete/*").hasAnyRole("USER", "ADMIN")
            .requestMatchers(HttpMethod.POST, "/user/update/*").hasAnyRole("USER", "ADMIN")
            .anyRequest().authenticated()
        )

        // JWT 인증 필터 먼저 실행
        .addFilterBefore(new JWTFilter(jwtUtil, userService),
            UsernamePasswordAuthenticationFilter.class)

        // 예외 처리 설정
        .exceptionHandling(ex -> ex
            .accessDeniedHandler((request, response, accessDeniedException) -> {
              response.setStatus(HttpServletResponse.SC_FORBIDDEN);
              response.setContentType("application/json;charset=UTF-8");
              response.getWriter().write("{\"error\": \"접근이 거부되었습니다.\"}");
            })
        )

        // 로그아웃 설정
        .logout(logout -> logout
            .logoutUrl("/api/v1/tini/logout")
            .addLogoutHandler(customLogoutHandler)
            .logoutSuccessHandler((request, response, authentication) -> {
              response.setStatus(HttpServletResponse.SC_OK);
              response.getWriter().write("로그아웃 성공");
            })
            .invalidateHttpSession(true)
            .clearAuthentication(true)
            .deleteCookies("JSESSIONID")
        );

    return http.build();
  }

}

