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
    public boolean addUsers(List<User> usersToAdd) {
        var newUsers = usersToAdd.stream().filter(u -> !users.contains(u)).toList();
        users.addAll(newUsers);
        return newUsers.size() == usersToAdd.size();
    }

    @Override
    public List<User> getAllUsers() {
        return Collections.unmodifiableList(users);
    }
}
