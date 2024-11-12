package com.tunemerge.Configuration;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.context.annotation.Configuration;
@Configuration
public class Config {
    private static final Dotenv dotenv = Dotenv.configure().load();



    public static String getAmazonClientId() {
        return dotenv.get("AMAZON_CLIENT_ID");
    }

    public static String getAmazonClientSecret() {
        return dotenv.get("AMAZON_CLIENT_SECRET");
    }

    public static String getSpotifyClientId() {
        return dotenv.get("SPOTIFY_CLIENT_ID");
    }
    public static String getSpotifyClientSecret() {
        return dotenv.get("SPOTIFY_CLIENT_SECRET");
    }

}
