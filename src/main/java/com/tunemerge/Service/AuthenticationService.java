package com.tunemerge.Service;

import com.tunemerge.ApiResponseBody.AmazonToken;
import com.tunemerge.ApiResponseBody.SpotifyToken;
import com.tunemerge.Configuration.Config;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Service
public class AuthenticationService {

        public static String getSpotifyAuthURL(String username) {
            return SpotifyOAuth.getAuthURL(username);
        }

        public static SpotifyToken getSpotifyAccessToken(String code) {
            return new SpotifyOAuth().getAccessToken(code);
        }

        public static String getAmazonAuthURL(String username) {
            return AmazonOAuth.getAmazonAuthURL(username);
        }

        public static AmazonToken getAmazonAccessToken(String code) {
            return new AmazonOAuth().getAccessTokenAmazon(code);
        }

}
@Service
class AmazonOAuth{

    private static final String AMAZON_CLIENT_ID = Config.getAmazonClientId();
    private static final String AMAZON_CLIENT_SECRET = Config.getAmazonClientSecret();
    private static final String REDIRECT_URI = "http://localhost:8080/";

    public static String getAmazonAuthURL(String username) {
        String scope = "catalog"; // Start with a basic scope
        String RedirectUri = "http://localhost:8080/tune_merge"; // Ensure this matches your registered URL
        return HttpHeader.getAuthUrl("https://www.amazon.com/ap/oa?", AMAZON_CLIENT_ID, RedirectUri,username);
//        try {
//            return "https://www.amazon.com/ap/oa?"
//                    + "client_id=" + URLEncoder.encode(AMAZON_CLIENT_ID, "UTF-8")
//                    + "&scope=" + URLEncoder.encode(scope, "UTF-8")
//                    + "&response_type=code"
//                    + "&redirect_uri=" + URLEncoder.encode(RedirectUri, "UTF-8");
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//            return null; // Handle error gracefully
//        }
    }

    public AmazonToken getAccessTokenAmazon(String code) {
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<MultiValueMap<String, String>> request = HttpHeader.getHttpHeader(code, AMAZON_CLIENT_ID, AMAZON_CLIENT_SECRET, REDIRECT_URI);
        ResponseEntity<AmazonToken> response = restTemplate.postForEntity("https://api.amazon.com/auth/o2/token", request, AmazonToken.class);
        return response.getBody();
    }

}

@Service
class SpotifyOAuth{

    private static final String CLIENT_ID = Config.getSpotifyClientId();
    private static final String CLIENT_SECRET = Config.getSpotifyClientSecret();
    private static final String REDIRECT_URI = "http://localhost:8080/Spotify/grantSpotify";


    public static String getAuthURL(String username) {
        String scope = "playlist-read-public playlist-read-private";
        return HttpHeader.getAuthUrl("https://accounts.spotify.com/authorize?", CLIENT_ID, REDIRECT_URI, username);
    }


    public SpotifyToken getAccessToken(String code) {
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<MultiValueMap<String, String>> request= HttpHeader.getHttpHeader(code, CLIENT_ID, CLIENT_SECRET, REDIRECT_URI);
        ResponseEntity<SpotifyToken> response = restTemplate.postForEntity("https://accounts.spotify.com/api/token", request, SpotifyToken.class);
        return response.getBody();
    }
}

class HttpHeader{

    protected static HttpEntity<MultiValueMap<String, String>> getHttpHeader(String code, String CLIENT_ID, String CLIENT_SECRET, String REDIRECT_URI) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setBasicAuth(CLIENT_ID, CLIENT_SECRET);
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("grant_type", "authorization_code");
        map.add("code", code);
        map.add("redirect_uri", REDIRECT_URI);
        return new HttpEntity<>(map, headers);
    }

    protected static String getAuthUrl(String BaseUrl, String CLIENT_ID, String REDIRECT_URI, String username) {
        return BaseUrl
                + "client_id=" + CLIENT_ID
                + "&response_type=code"
                + "&redirect_uri=" + URLEncoder.encode(REDIRECT_URI,StandardCharsets.UTF_8)
                +"&state="+URLEncoder.encode(username, StandardCharsets.UTF_8);
    }
}