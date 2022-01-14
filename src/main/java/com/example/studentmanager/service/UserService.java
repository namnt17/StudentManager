package com.example.studentmanager.service;

import com.example.studentmanager.entity.User;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;


public interface UserService {
    void saveUser(User user, String siteURL) throws MessagingException, UnsupportedEncodingException;

    void sendVerificationEmail(User user, String siteURL) throws MessagingException, UnsupportedEncodingException;

    boolean verify(String verificationCode);

    User getUserByEmail(String email);

}
