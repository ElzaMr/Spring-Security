package com.example.demo.service;
import com.example.demo.model.Role;
import com.example.demo.model.User;
import com.example.demo.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepo userRepo;
    @Autowired
    public UserServiceImpl(UserRepo userRepo) {
        this.userRepo = userRepo;
//        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public List<User> getAllUsers() {
        return userRepo.findAll();
    }

    @Override
    @Transactional
    public User getUserById(int id) {
        User user = userRepo.findById(id).orElse(null);
//        Hibernate.initialize(user.getRoleSet());
        return user;
    }

    @Override
    @Transactional
    public void save(User user) {
//        user.setPass(passwordEncoder.encode(user.getPass()));
        userRepo.save(user);
    }

    @Override
    @Transactional
    public void update(User updatedUser) {
    userRepo.save(updatedUser);
    }

    @Override
    @Transactional
    public void delete(int id) {
        userRepo.deleteById(id);
    }

    @Override
    public User getUserByUsername(String username) {
        return userRepo.getUserByUsername(username);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.getUserByUsername(username);
        if(user == null){
           throw new UsernameNotFoundException("User not found");
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(),user.getPass(),mapRolesToAuthorities(user.getRoles()));
    }
    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles){
        return roles.stream().map(r -> new SimpleGrantedAuthority(r.getName())).collect(Collectors.toList());

    }
}
