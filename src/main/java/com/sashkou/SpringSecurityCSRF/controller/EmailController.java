package com.sashkou.SpringSecurityCSRF.controller;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class EmailController {
    private static final Logger logger = LoggerFactory.getLogger(EmailController.class.getSimpleName());

    //private final CustomerEmailService customerEmailService;

    @PostMapping("/registerEmail")
    public String registerCustomerEmail(@RequestParam String newEmail) {
        logger.info("Email: {}", newEmail);
        //customerEmailService.registerEmail(newEmail);
        return "emailChange";
    }
}