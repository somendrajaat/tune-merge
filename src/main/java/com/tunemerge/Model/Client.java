package com.tunemerge.Model;

import com.tunemerge.ApiResponseBody.AmazonToken;
import com.tunemerge.ApiResponseBody.SpotifyToken;
import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

import java.util.List;

@Getter
@Setter
public class Client {

    @Id
    private ObjectId id;
    private String username;
    private String password;
    private String email;
    private AmazonToken amazonToken;
    private SpotifyToken spotifyToken;
    private List<String> roles;
}
