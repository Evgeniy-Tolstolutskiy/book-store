package com.tolstolutskyi.common;

public final class Constants {
    public static final String CLOUDINARY_IMAGES_URL_HOST = "http://res.cloudinary.com";

    private Constants() {
    }

    public static final class UrlRestrictions {
        public static final String[] EVERYBODY_ALLOWED_URLS = {
            "/users/registration"
        };
    }
}
