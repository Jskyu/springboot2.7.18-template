package jsk.spring2.demo.dto;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import java.util.ArrayList;
import java.util.List;

@Data
public class UserDto {

    private String userId;
    private String userPw;
    private List<GrantedAuthority> authorities = new ArrayList<>();

    public static UserDto create(String userId, String userPw) {
        UserDto user = new UserDto();
        user.setUserId(userId);
        user.setUserPw(userPw);
        return user;
    }
}
