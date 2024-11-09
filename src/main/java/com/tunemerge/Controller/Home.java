package com.tunemerge.Controller;

import com.tunemerge.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/home")
public class Home {
    @Autowired
    UserService userService;

    @GetMapping("/login")
    public ResponseEntity<?>  login(@RequestParam String username,@RequestParam String password) {
        if(userService.checkUser(username,password))
             return ResponseEntity.ok().build();
        else return ResponseEntity.badRequest().body("Invalid username or password");

    }

    @GetMapping("/register")
    public ResponseEntity<String> register(@RequestParam String email, @RequestParam String username,@RequestParam String password) {
        if(userService.createUser(email,username, password)){
            return ResponseEntity.ok().build();
        }else return ResponseEntity.badRequest().body("Invalid email or username already exists");
    }


}
