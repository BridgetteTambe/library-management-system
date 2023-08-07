package com.drycode.security;

import com.drycode.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserService userService;

    private static List<com.drycode.models.User> users = Arrays.asList(
            new com.drycode.models.User("foo", "foo"),
            new com.drycode.models.User("me", "me"),
            new com.drycode.models.User("you", "you"),
            new com.drycode.models.User("run", "run")
    );

    public UserDetails loadUserByUsernameExchange(String username) throws UsernameNotFoundException {
        for (com.drycode.models.User member : users) {
            if (member.getUsername().equals(username)) {
                return new User(member.getUsername(), member.getPassword(), new ArrayList<>());
            }
        }
        return null;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        com.drycode.models.User byUsername = userService.findByUsername(username);
        if (byUsername != null) {
            return new User(byUsername.getUsername(), byUsername.getPassword(), new ArrayList<>());
        } else {
            throw new UsernameNotFoundException("Invalid Username or password!.");
        }
    }
}
