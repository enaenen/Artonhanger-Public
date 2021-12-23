package com.artonhanger.manage.service;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailSendService {

    private final JavaMailSender javaMailSender;

    public void sendNewPassword(String email,String newPassword) {
        mailSender(email,"아트온행거 비밀번호 초기화 메일입니다.", "초기화된 비밀번호 : " + newPassword);
    }

    public void mailSender(String email, String title, String contents) {
        SimpleMailMessage simpleMessage = new SimpleMailMessage();
        // simpleMessage.setFrom("보낸사람@naver.com"); // NAVER, DAUM, NATE일 경우 넣어줘야 함
        simpleMessage.setTo(email);
        simpleMessage.setSubject(title);
        simpleMessage.setText(contents);
        simpleMessage.setFrom("artonhanger@gmail.com");
        javaMailSender.send(simpleMessage);
    }

}
