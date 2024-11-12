package com.tunemerge.ApiResponseBody;


import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public class UserProfile{
    private String display_name;
    private String id;
    private List<Image> images;
    @Getter
    @Setter
    public static class Image{
        private String url;
        private int height;
        private int width;
    }
}

