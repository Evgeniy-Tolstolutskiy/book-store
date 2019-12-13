package com.tolstolutskyi.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.Map;

@Service
public class ImageService {
    private final Cloudinary cloudinary;

    public ImageService(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }

    public String saveImage(File image) throws IOException {
        Map uploadResult = cloudinary.uploader().upload(image, ObjectUtils.emptyMap());
        return uploadResult.get("url").toString();
    }

    public void deleteImage(String url) throws IOException {
        String[] urlParts = url.split("/");
        String publicId = urlParts[urlParts.length - 1].split("\\.")[0];
        cloudinary.uploader().destroy(publicId, Collections.emptyMap());
    }
}
