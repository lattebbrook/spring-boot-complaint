package com.ssw.ssw_complaint.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthController {
    
    @RequestMapping(value = {"/login"})
    public String home(HttpServletRequest request) throws ServletException, IOException {
        HttpSession session = request.getSession(false); //we instantiate the interface HttpSession first and check if session is not already tokenized.
        if(session != null && session.getAttribute("token") != null) { //if token already assigned to session
            return "dashboard";               //go to dashboard
        }
        return "auth/login"; //else go to login
    }

    @PostMapping(value = {"/action-login-check"})
    public String checkLogin(HttpServletRequest request, HttpServletResponse response, @RequestParam String email, @RequestParam String password) {
        if(     "user@gmail.com".equals(email) && 
                "1234".equals(password)) {
            
            HttpSession session = request.getSession();
            session.setAttribute("token", "mySession");
            
            Cookie cookie = new Cookie("sessionId", session.getId());
            /*cookie.setMaxAge(30 * 60); //---> Set cookie expiry time to 30 minutes */
            response.addCookie(cookie);

            return "dashboard";
        } else {
            return "access-denied";
        }
    }

    
    @GetMapping(value = "/logout")
    public String logout(HttpSession session) {
        session.invalidate(); // close session
        return "auth/login"; //redirect to login 
    }    

}