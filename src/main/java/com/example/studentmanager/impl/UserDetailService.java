package com.example.studentmanager.impl;

import com.example.studentmanager.config.UserDetailConfig;
import com.example.studentmanager.entity.User;
import com.example.studentmanager.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


// lớp này thực hiện việc lấy dữ liệu từ nhập của client
@Service
public class UserDetailService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = null;
        try {
            user = userRepository.getUserByUsername(username);
        } catch (NullPointerException e) {
            System.out.println("Couldn't found the account");
        }
        return new UserDetailConfig(user);
    }
}
