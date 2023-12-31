package com.example.demo.service;


import com.example.demo.model.User;

import java.util.List;

public interface UserService {
    public List<User> getAllUsers();
    public User getUserById(int id);
    public void save(User user);
    public void update(User updatedUser);
    public void delete(int id);
    public User getUserByUsername(String username);
}
