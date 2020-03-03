package com.safe.online.journal;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
public class LoginController {
    @Autowired
    public LoginHelper login;

    @GetMapping("/")
    public String home() {
        return "index";
    }

    @PostMapping(value = "/validateUser")
    public String validateUser(@ModelAttribute User user, HttpSession session) {
        if(login.isValidUser(user.getUserName(), user.getPassword())) {
            session.setAttribute("UserID",user.getUserName());
            return "home";
        } else {
            return "createUser";
        }
    }

    @PostMapping("/createUser")
    public String createUser(@ModelAttribute User user) {
        login.createUser(user.getNewUserName(),user.getPassword());
        return "index";
    }

    @PostMapping("/checkAlreadyExist")
    public @ResponseBody String
    checkAlreadyExist(@ModelAttribute User user) {
        boolean isUser = login.checkAlreadyExist(user.getNewUserName());
        JSONObject jason = new JSONObject();
        jason.put("userExist",String.valueOf(isUser));
        return jason.toString();
    }

}
