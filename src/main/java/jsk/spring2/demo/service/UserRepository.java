package jsk.spring2.demo.service;

import jsk.spring2.demo.dto.UserDto;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class UserRepository {

    private final List<UserDto> userList = new ArrayList<>();  // DB 대신 임시로..

    public void create(UserDto user) {
        userList.add(user);
    }

    public Optional<UserDto> findByUsername(String username) {
        return userList.stream()
                .filter(user -> user.getUserId().equals(username))
                .findFirst();
    }
}