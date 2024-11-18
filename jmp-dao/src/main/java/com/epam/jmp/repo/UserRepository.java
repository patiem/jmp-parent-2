package com.epam.jmp.repo;

import com.epam.jmp.dto.User;

import java.util.List;

public interface UserRepository {

    boolean addUser(User user);
    boolean addUsers(List<User> users);
    List<User> getAllUsers();
}
