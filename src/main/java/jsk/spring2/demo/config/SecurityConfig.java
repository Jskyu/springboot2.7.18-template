package jsk.spring2.demo.config;

import jsk.spring2.demo.dto.UserDto;
import jsk.spring2.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import javax.servlet.DispatcherType;
import java.util.Optional;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig implements UserDetailsService {

    private final UserService userService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .cors(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(request ->
                        request.dispatcherTypeMatchers(DispatcherType.FORWARD).permitAll()
                                .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll() // 정적자원 허용
                                .anyRequest().authenticated() // 어떠한 요청이라도 인증필요 O
//                                .anyRequest().permitAll() // 어떠한 요청이라도 인증필요 X
                )
                .formLogin(login -> login // form 방식 로그인 사용
                        .loginPage("/login") // 로그인 url
                        .loginProcessingUrl("/login-process") // 로그인 submit url
                        .usernameParameter("userId") // form userId
                        .passwordParameter("userPw") // form userPw
                        .defaultSuccessUrl("/mypage", true) // 성공 시 main
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/home")
                        .permitAll()
                );

        return http.build();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserDto> findUser = userService.findByUsername(username);
        UserDto user = findUser.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원"));

        UserDetails userDetail = User.withUsername(user.getUserId())
                .password(user.getUserPw())
                .authorities(user.getAuthorities())
//                .roles(user.getRoles())
                .build();
        return userDetail;
    }
}