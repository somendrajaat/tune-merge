package com.tunemerge.Controller;

import com.tunemerge.ApiResponseBody.AmazonToken;
import com.tunemerge.ApiResponseBody.SpotifyToken;
import com.tunemerge.Service.AuthenticationService;
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


    @GetMapping("/loginAmazonMusic")
    public RedirectView login() {
        String oAuth = AuthenticationService.getAmazonAuthURL();
        return new RedirectView(oAuth);
    }

    @GetMapping("/grantAmazon")
    public ResponseEntity<?> token(@RequestParam("code") String code) {
        AmazonToken AmazonToken=AuthenticationService.getAmazonAccessToken(code);
        if(AmazonToken!=null)
            return ResponseEntity.ok().build();
        else return ResponseEntity.noContent().build();
    }

}
