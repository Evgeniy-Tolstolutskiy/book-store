package com.tolstolutskyi.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.tolstolutskyi.common.FileUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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

    public String saveImage(MultipartFile image) throws IOException {
        Map uploadResult = new ConvertedFileUploader().convertAndUpload(image);
        return uploadResult.get("url").toString();
    }

    public void deleteImage(String url) throws IOException {
        String[] urlParts = url.split("/");
        String publicId = urlParts[urlParts.length - 1].split("\\.")[0];
        cloudinary.uploader().destroy(publicId, Collections.emptyMap());
    }

    private class ConvertedFileUploader {
        public Map convertAndUpload(MultipartFile image) throws IOException {
            File convertedImage = FileUtils.convert(image);
            Map uploadResult = cloudinary.uploader().upload(convertedImage, ObjectUtils.emptyMap());
            convertedImage.delete();
            return uploadResult;
        }
    }
}
