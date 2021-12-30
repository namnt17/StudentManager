package com.example.studentmanager.impl;

import com.example.studentmanager.dto.UserDTO;
import com.example.studentmanager.entity.User;
import com.example.studentmanager.repository.UserRepository;
import com.example.studentmanager.service.UserService;
import net.bytebuddy.utility.RandomString;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ModelMapper mapper;


    @Autowired
    private JavaMailSender mailSender;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public void saveUser(User user,String siteURL)
            throws MessagingException, UnsupportedEncodingException {
        String encoded = this.passwordEncoder.encode(user.getPassword());
        String verify_code = RandomString.make(64);
        user.setPassword(encoded);
        user.setEnabled(false);
        user.setVerification_code(verify_code);
        userRepository.save(user);
        sendVerificationEmail(user,siteURL);
    }

    @Override
    public void sendVerificationEmail(User user, String siteURL) throws MessagingException, UnsupportedEncodingException {
        String toAddress = user.getUsername();
        String fromAddress = "prodota1720@gmail.com";
        String senderName = "Student Manager Application";
        String subject = "Please verify your account";
        String content = "Welcome [[name]],<br>"
                + "Please click the link below to verify your registration:<br>"
                + "<h3><a href=\"[[URL]]\" target=\"_self\">VERIFY</a></h3>"
                + "Thank you,<br>"
                + "Author.";
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message,true);
        helper.setFrom(fromAddress, senderName);
        helper.setTo(toAddress);
        helper.setSubject(subject);
        content = content.replace("[[name]]", user.getUsername());
        String verifyURL = siteURL + "/verify?code=" + user.getVerification_code();
        content = content.replace("[[URL]]", verifyURL);
        helper.setText(content, true);
        user.setEnabled(true);
        this.mailSender.send(message);
    }

    @Override
    public boolean verify(String verificationCode) {
        User user = userRepository.findByVerificationCode(verificationCode);
        if (user == null || user.isEnabled()) {
            return false;
        } else {
            user.setVerification_code(null);
            user.setEnabled(true);
            userRepository.verifySuccessAccount(user.getId());
            return true;
        }
    }


    // get all user without field password
    public List<UserDTO> getAllUser(){
        return userRepository.findAll()
                // trả về luồng xử lý theo tuần tự
                .stream()
                // giúp ánh xạ các phần tử có trong List trả về kết quả ham convertEntityToDTO
                .map(this::convertEntityToDTO)
                // giúp luồng thu thập kết quả cuối cùng
                .collect(Collectors.toList());
    }

    // method giúp convert đối tượng User sang đối tượng DTO
    // giúp loại bỏ field password khi trả về cho client
    private UserDTO convertEntityToDTO(User user){
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setUsername(user.getUsername());
        userDTO.setRole(user.getRole());
        return userDTO;
    }

    public UserDTO getUserById(Long id){
        // get User from Database
        User user = userRepository.findById(id).get();
        // map thành UserDTO
        UserDTO userDTO = mapper.map(user,UserDTO.class);
        return userDTO;
    }



}
