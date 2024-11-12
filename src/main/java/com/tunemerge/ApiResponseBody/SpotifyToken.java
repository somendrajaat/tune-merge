package com.tunemerge.ApiResponseBody;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SpotifyToken {
    private String access_token;
    private String token_type;
    private String scope;
    private int expires_in;
    private String refresh_token;
}
