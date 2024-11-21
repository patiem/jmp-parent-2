package com.epam.jmp.repo;

import com.epam.jmp.dto.User;

import java.util.List;

public interface UserRepository {

    void addUser(User user);
    List<User> getAllUsers();
}
