package jsk.spring2.demo.service;

import jsk.spring2.demo.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserRegistService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public void createUser(String username, String userPw) {
        UserDto user = UserDto.create(username, passwordEncoder.encode(userPw));
        userRepository.create(user);
    }
}
