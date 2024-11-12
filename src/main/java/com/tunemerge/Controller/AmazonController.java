package com.tunemerge.Controller;

import com.tunemerge.ApiResponseBody.AmazonToken;
import com.tunemerge.ApiResponseBody.SpotifyToken;
import com.tunemerge.Model.Client;
import com.tunemerge.Service.AmazonService;
import com.tunemerge.Service.AuthenticationService;
import com.tunemerge.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

@RestController
@RequestMapping("/Amazon")
public class AmazonController {
    @Autowired
    UserService userService;
    @Autowired
    AmazonService amazonService;

    @GetMapping("/loginAmazonMusic")
    public RedirectView login(String username) {
        String oAuth = AuthenticationService.getAmazonAuthURL(username);
        return new RedirectView(oAuth);
    }

    @GetMapping("/grantAmazon")
    public ResponseEntity<?> token(@RequestParam("code") String code, @RequestParam("state") String username) {
        Client client = userService.getUser(username);
        if (client.getSpotifyToken() == null) {
            SpotifyToken spotifyToken = AuthenticationService.getSpotifyAccessToken(code);
            if (spotifyToken != null) {
                client.setSpotifyToken(spotifyToken);
                userService.updateUser(client);
                return amazonService.getplaylist(username);
            } else return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok().build();
    }



}
