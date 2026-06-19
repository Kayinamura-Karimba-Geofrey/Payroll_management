package com.payroll.service;

public interface MailService {
    void sendSimpleMail(String to, String subject, String body);
}
