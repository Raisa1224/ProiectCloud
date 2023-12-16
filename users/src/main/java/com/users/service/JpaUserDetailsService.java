package com.users.service;

import com.users.entity.Role;
import com.users.entity.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import org.springframework.security.core.userdetails.UserDetailsService;
import com.users.repository.UsersRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class JpaUserDetailsService implements UserDetailsService {

    @Autowired
    private RedisService redisService;
    private final UsersRepository userRepository;

    public JpaUserDetailsService(UsersRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> userOpt = userRepository.findByEmail(email);
        User user = userOpt.get();
        if (user != null) {
            redisService.saveData("userId", String.valueOf(user.getUserId()));
            return new org.springframework.security.core.userdetails.User(user.getEmail(),
                    user.getUserPassword(),
                    mapRolesToAuthorities(user.getRole()));
        } else {
            throw new UsernameNotFoundException("Invalid username or password.");
        }
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Role userRole) {
        List<Role> roles = new ArrayList<>();
        roles.add(userRole);
        Collection<? extends GrantedAuthority> mapRoles = roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getRoleName()))
                .collect(Collectors.toList());
        return mapRoles;

    }
}