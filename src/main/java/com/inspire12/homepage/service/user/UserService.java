package com.inspire12.homepage.service.user;

import com.inspire12.homepage.domain.model.User;
import com.inspire12.homepage.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public List<User> getAdminUsers() {
        List<User> users = new ArrayList<>();
        List<String> names = Arrays.asList("inspire12", "hygoni", "Sinyoung3016", "MoonDD99", "wilook");
        for (String name : names) {
            try {
                users.add(userRepository.findById(name).get());
            } catch (Exception e) {
                log.warn("user not found: " + name + e.toString());
            }
        }
        Collections.shuffle(users);
        return users;
    }

    public User getUser(String username){
        return userRepository.findById(username).get();
    }

    public User modifyUser(String username, User modifiedUser) throws Exception {
        if(userRepository.existsById(username)) {
            return userRepository.save(modifiedUser);
        }
        throw new Exception();
    }

}

