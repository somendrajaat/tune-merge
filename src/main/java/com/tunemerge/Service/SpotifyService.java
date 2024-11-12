package com.tunemerge.Service;


import com.tunemerge.ApiResponseBody.PlaylistResponse;
import com.tunemerge.ApiResponseBody.UserProfile;
import com.tunemerge.Model.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Service
public class SpotifyService {
    @Autowired
    UserService userService;

    private static final String SPOTIFY_URL = "https://api.spotify.com/v1/me";

    public PlaylistResponse getPlaylists(String username) {
        RestTemplate restTemplate = new RestTemplate();
        Client client = userService.getUser(username);
        String accessToken = client.getSpotifyToken().getAccess_token();
        HttpEntity<String> entity = getHttpHeader(accessToken);
        ResponseEntity<PlaylistResponse> response = restTemplate.exchange(SPOTIFY_URL+"/playlists", HttpMethod.GET, entity, PlaylistResponse.class);
        return response.getBody();
    }


    public UserProfile getProfile(String accessToken) {
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<String> entity = getHttpHeader(accessToken);
        ResponseEntity<UserProfile> response = restTemplate.exchange(SPOTIFY_URL, HttpMethod.GET, entity, UserProfile.class);
        return response.getBody();
    }


    private HttpEntity<String> getHttpHeader(String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new HttpEntity<>(headers);
    }



}

