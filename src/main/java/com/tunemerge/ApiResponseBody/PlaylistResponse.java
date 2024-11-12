package com.tunemerge.ApiResponseBody;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public class PlaylistResponse{
    private String href;
    private int total;
    @JsonProperty("items")
    private List<Item> items;

    @Getter
    @Setter
    public static class ExternalUrls{
        private String spotify;
    }
    @Getter
    @Setter
    public static class Image{
        private String url;
    }
    @Getter
    @Setter
    public static class Item {
        private String id;
        private List<Image> images;
        private String name;
    }
    @Getter
    @Setter
    public static class Tracks{
        private String href;
        private int total;
    }
}


