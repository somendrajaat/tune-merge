package com.tunemerge.Controller;

import com.tunemerge.ApiResponseBody.PlaylistResponse;
import com.tunemerge.ApiResponseBody.SpotifyToken;
import com.tunemerge.Model.Client;
import com.tunemerge.Service.AuthenticationService;
import com.tunemerge.Service.SpotifyService;
import com.tunemerge.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;
@RestController
@RequestMapping("/Spotify")
public class SpotifyController {
    @Autowired
    UserService userService;
    @Autowired
    SpotifyService spotifyService;



    public ResponseEntity<?> login( String username) {
        return ResponseEntity.ok(new RedirectView(AuthenticationService.getSpotifyAuthURL(username)));
    }


    @GetMapping("/grantSpotify")
    public ResponseEntity<?> token(@RequestParam("code") String code,@RequestParam("state") String username) {
        Client client = userService.getUser(username);

        if(!userService.checkUser(username)){

            return ResponseEntity.badRequest().body("User not found");

        }
        if (client.getSpotifyToken() == null) {

            SpotifyToken spotifyToken = AuthenticationService.getSpotifyAccessToken(code);

            if (spotifyToken != null) {

                client.setSpotifyToken(spotifyToken);

                userService.updateUser(client);

                return ResponseEntity.ok(spotifyService.getPlaylists(username));

            } else return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok().build();
    }




    public ResponseEntity<?> getplaylist(String username) {
        return ResponseEntity.ok(spotifyService.getPlaylists(username));
    }





    @GetMapping("/profile")
    public ResponseEntity<?> getProfile(@RequestParam String username) {
        Client client = userService.getUser(username);
        if(client==null || client.getSpotifyToken()==null){
            return login(username);
        }else return ResponseEntity.ok(spotifyService.getProfile(client.getSpotifyToken().getAccess_token()));
    }


}
