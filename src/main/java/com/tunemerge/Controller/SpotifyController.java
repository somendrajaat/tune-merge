package com.tunemerge.Controller;

import com.tunemerge.ApiResponseBody.SpotifyToken;
import com.tunemerge.Service.AuthenticationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;
@RestController
@RequestMapping("/Spotify")
public class SpotifyController {


    @GetMapping("/login")
    public ResponseEntity<?> login() {
        return ResponseEntity.ok(new RedirectView(AuthenticationService.getSpotifyAuthURL()));
    }
    @GetMapping("/grantSpotify")
    public ResponseEntity<?> token(@RequestParam("code") String code) {
        SpotifyToken spotifyToken=AuthenticationService.getSpotifyAccessToken(code);
        if(spotifyToken!=null)
        return ResponseEntity.ok().build();
        else return ResponseEntity.noContent().build();
    }

}

