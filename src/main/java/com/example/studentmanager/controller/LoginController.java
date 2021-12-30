package com.example.studentmanager.controller;

import com.example.studentmanager.entity.User;
import com.example.studentmanager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;

@Controller
@Transactional
public class LoginController {
    @Autowired
    private final UserService userService;

    @Autowired
    private UserService userServiceImpl;

    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @GetMapping("/register")
    public String fillFormRegister(Model model){
        User user = new User();
        // use interface model to transfer data from Controller to View
        model.addAttribute("user",user);
        return "register";
    }

    @PostMapping("/register")
    public RedirectView saveUser(@ModelAttribute("user") User user, RedirectAttributes redir,HttpServletRequest request) throws MessagingException, UnsupportedEncodingException {
        userService.saveUser(user,getSiteURL(request));
        RedirectView redirectView = new RedirectView("/register",true);
        redir.addFlashAttribute("message","Bạn đã đăng ký thành công tài khoản");
        redir.addFlashAttribute("message_verify","Vui lòng xác nhận qua email của bạn");
        return redirectView;
    }

    private String getSiteURL(HttpServletRequest request) {
        String siteURL = request.getRequestURL().toString();
        return siteURL.replace(request.getServletPath(), "");
    }


    @GetMapping("/verify")
    public String verifyUser(@Param("code") String code) {
        if (userService.verify(code)) {
            return "verify_success";
        } else {
            return "verify_fail";
        }
    }



}
