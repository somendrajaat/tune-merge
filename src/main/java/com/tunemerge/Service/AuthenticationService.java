package com.tunemerge.Service;

import com.tunemerge.ApiResponseBody.AmazonToken;
import com.tunemerge.ApiResponseBody.SpotifyToken;
import com.tunemerge.Configuration.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class AuthenticationService {

        public static String getSpotifyAuthURL() {
            return SpotifyOAuth.getAuthURL();
        }

        public static SpotifyToken getSpotifyAccessToken(String code) {
            return new SpotifyOAuth().getAccessToken(code);
        }
        public static String getAmazonAuthURL() {
            return AmazonOAuth.getAmazonAuthURL();
        }

}

class AmazonOAuth{
    private static final String AMAZON_CLIENT_ID = Config.getAmazonClientId();
    private static final String AMAZON_CLIENT_SECRET = Config.getAmazonClientSecret();
    private static final String REDIRECT_URI = "http://localhost:8080/";

    public static String getAmazonAuthURL() {
        String scope = "catalog"; // Start with a basic scope
        String RedirectUri = "http://localhost:8080/tune_merge"; // Ensure this matches your registered URL

        try {
            return "https://www.amazon.com/ap/oa?"
                    + "client_id=" + URLEncoder.encode(AMAZON_CLIENT_ID, "UTF-8")
                    + "&scope=" + URLEncoder.encode(scope, "UTF-8")
                    + "&response_type=code"
                    + "&redirect_uri=" + URLEncoder.encode(RedirectUri, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null; // Handle error gracefully
        }
    }

    public AmazonToken getAccessTokenAmazon(String code) {
        RestTemplate restTemplate = new RestTemplate();
        Logger logger = LoggerFactory.getLogger(this.getClass());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setBasicAuth(AMAZON_CLIENT_ID, AMAZON_CLIENT_SECRET);

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("grant_type", "authorization_code");
        map.add("code", code);
        map.add("redirect_uri", REDIRECT_URI);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);
        ResponseEntity<AmazonToken> response = restTemplate.postForEntity("https://api.amazon.com/auth/o2/token", request, AmazonToken.class);
        AmazonToken token = response.getBody();
        return token;
    }

}
class SpotifyOAuth{
    private static final String CLIENT_ID = Config.getSpotifyClientId();
    private static final String CLIENT_SECRET = Config.getSpotifyClientSecret();
    private static final String REDIRECT_URI = "http://localhost:8080/grantSpotify";


    public static String getAuthURL() {
        String scope = "playlist-read-public playlist-read-private";
        String authURL = "https://accounts.spotify.com/authorize?"
                + "client_id=" + CLIENT_ID
                + "&response_type=code"
                + "&redirect_uri=" + REDIRECT_URI;
        return authURL;
    }

    public SpotifyToken getAccessToken(String code) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setBasicAuth(CLIENT_ID, CLIENT_SECRET);

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("grant_type", "authorization_code");
        map.add("code", code);
        map.add("redirect_uri", REDIRECT_URI);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);

        ResponseEntity<SpotifyToken> response = restTemplate.postForEntity("https://accounts.spotify.com/api/token", request, SpotifyToken.class);
        SpotifyToken token = response.getBody();
        return token;
    }
}
