package com.tunemerge.Controller;

import com.tunemerge.ApiResponseBody.userLogin;
import com.tunemerge.Model.Client;
import com.tunemerge.Service.SpotifyService;
import com.tunemerge.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/home")
public class Home {
    @Autowired
    UserService userService;
    @Autowired
    SpotifyController spotifyController;
    @Autowired
    SpotifyService spotifyService;

    @GetMapping("/login")
    public ResponseEntity<?>  login(@RequestBody userLogin user) {

        if(userService.checkUser(user.getUsername(),user.getPassword()))
             return ResponseEntity.ok().build();
        else return ResponseEntity.badRequest().body("Invalid username or password");

    }
    @GetMapping("/register")
    public ResponseEntity<String> register(@RequestParam String username ,@RequestParam String email,@RequestParam String password) {
        if(userService.createUser(email,username,password)){
            return ResponseEntity.ok().build();
        }else return ResponseEntity.badRequest().body("Invalid email or username already exists");
    }
    @GetMapping("/all")
    public ResponseEntity<?> all() {
        return ResponseEntity.ok(userService.getAll());
    }
    @GetMapping(value = "/spotify")
    public ResponseEntity<?> spotify(@RequestParam String username) {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        String username=authentication.getName();

        Client client = userService.getUser(username);
        if(client==null || client.getSpotifyToken()==null){
            return spotifyController.login(username);
        }else return spotifyController.getplaylist(username);
    }

    @GetMapping()
    public String test(){
        return "Hello";
    }

    @DeleteMapping("/deleteUser")
    public ResponseEntity<?> deleteUser(@RequestParam String username){
        if( userService.deleteUser(username) ){
            return ResponseEntity.ok().build();
        }else return ResponseEntity.badRequest().body("User not found");
    }




}
