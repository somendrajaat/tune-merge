package com.tunemerge.Controller;

import com.tunemerge.ApiResponseBody.PlaylistResponse;
import com.tunemerge.ApiResponseBody.SpotifyToken;
import com.tunemerge.Model.Client;
import com.tunemerge.Service.AuthenticationService;
import com.tunemerge.Service.SpotifyService;
import com.tunemerge.Service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.view.RedirectView;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class SpotifyControllerTest {

    @Mock
    private UserService userService;

    @Mock
    private SpotifyService spotifyService;

    @Mock
    private AuthenticationService authenticationService;

    @InjectMocks
    private SpotifyController spotifyController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    void loginRedirectsToSpotifyAuthURL() {
        String username = "testuser";
        String expectedUrl = "http://spotify.com/auth";

        when(authenticationService.getSpotifyAuthURL(username)).thenReturn(expectedUrl);

        ResponseEntity<?> response = spotifyController.login(username);

        assertEquals(RedirectView.class, response.getBody().getClass());
        assertEquals(expectedUrl, ((RedirectView) response.getBody()).getUrl());
    }

    void tokenReturnsBadRequestForNonExistentUser() {
        String code = "authCode";
        String username = "nonexistentuser";

        when(userService.getUser(username)).thenReturn(null);
        when(userService.checkUser(username)).thenReturn(false);

        ResponseEntity<?> response = spotifyController.token(code, username);

        assertEquals(ResponseEntity.badRequest().body("User not found"), response);
    }

    void tokenReturnsNoContentForNullSpotifyToken() {
        String code = "authCode";
        String username = "testuser";
        Client client = new Client();
        client.setUsername(username);

        when(userService.getUser(username)).thenReturn(client);
        when(userService.checkUser(username)).thenReturn(true);
        when(AuthenticationService.getSpotifyAccessToken(code)).thenReturn(null);

        ResponseEntity<?> response = spotifyController.token(code, username);

        assertEquals(ResponseEntity.noContent().build(), response);
    }

    void tokenUpdatesUserWithSpotifyToken() {
        String code = "authCode";
        String username = "testuser";
        Client client = new Client();
        client.setUsername(username);
        SpotifyToken spotifyToken = new SpotifyToken();

        when(userService.getUser(username)).thenReturn(client);
        when(userService.checkUser(username)).thenReturn(true);
        when(AuthenticationService.getSpotifyAccessToken(code)).thenReturn(spotifyToken);
        when(spotifyService.getPlaylists(username)).thenReturn(new PlaylistResponse());

        ResponseEntity<?> response = spotifyController.token(code, username);

        assertEquals(ResponseEntity.ok(new PlaylistResponse()), response);
    }

    void getPlaylistReturnsPlaylistResponse() {
        String username = "testuser";
        PlaylistResponse playlistResponse = new PlaylistResponse();

        when(spotifyService.getPlaylists(username)).thenReturn(playlistResponse);

        ResponseEntity<?> response = spotifyController.getplaylist(username);

        assertEquals(ResponseEntity.ok(playlistResponse), response);
    }
}