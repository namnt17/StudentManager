package com.example.studentmanager.config;

import com.example.studentmanager.entity.Role;
import com.example.studentmanager.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

// lớp này sẽ lưu thông tin xác thực và phân quyền
public class UserDetailConfig implements UserDetails {

    private User user;

    public UserDetailConfig(User user) {
        this.user = user;
    }

    // hàm getAuthorities lấy ra phân quyền của user
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<Role> roles = user.getRoles();
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        for (Role role : roles) {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        }
        return authorities;
    }

    // hàm getPassword lấy ra mật khẩu của user
    @Override
    public String getPassword() {
        if (user.getPassword() == null) {
            return null;
        }
        return user.getPassword();
    }

    // hàm lấy ra tên của user
    @Override
    public String getUsername() {
        if (user.getUsername() == null) {
            return null;
        }
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return user.isEnabled();
    }

}
