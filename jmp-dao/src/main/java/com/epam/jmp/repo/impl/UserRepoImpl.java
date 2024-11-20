package com.epam.jmp.repo.impl;

import com.epam.jmp.dto.User;
import com.epam.jmp.repo.UserRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class UserRepoImpl implements UserRepository {

    private final List<User> users = new ArrayList<>();

    @Override
    public boolean addUser(User user) {
        if (users.contains(user)) return false;
        return users.add(user);
    }

    @Override
    public List<User> getAllUsers() {
        return Collections.unmodifiableList(users);
    }
}
