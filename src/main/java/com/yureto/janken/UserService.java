package com.yureto.janken;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserService {
    private final Map<String, User> users = new HashMap<>();

    public User login(String id, String password) {
        if (users.containsKey(id)) {
            User user = users.get(id);
            if (user.getPassword().equals(password)) {
                return user;
            } else {
                return null;
            }
        } else {
            User newUser = new User(id, password);
            users.put(id, newUser);
            return newUser;
        }
    }
}


