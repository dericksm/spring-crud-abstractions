package com.derick.services.validation;

import com.derick.services.execeptions.FileException;
import org.apache.commons.io.FilenameUtils;
import org.imgscalr.Scalr;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

@Service
public class ImageService {

    public BufferedImage getJpgImgFromFile(MultipartFile multipartFile) {
        String ext = FilenameUtils.getExtension(multipartFile.getOriginalFilename());
        if (!"png".equals(ext) && !"jpg".equals(ext)) {
            throw new FileException("Only JPG and PNG images are allowed");
        }
        try {
            BufferedImage bufferedImage = ImageIO.read(multipartFile.getInputStream());
            if ("png".equals(ext)) {
                bufferedImage = pngToJpg(bufferedImage);
            }
            return bufferedImage;
        } catch (IOException e) {
            throw new FileException("Error while reading file");
        }
    }

    public BufferedImage pngToJpg(BufferedImage bufferedImage) {
        BufferedImage img = new BufferedImage(bufferedImage.getWidth(), bufferedImage.getHeight(), BufferedImage.TYPE_INT_RGB);
        img.createGraphics().drawImage(img, 0, 0, Color.WHITE, null);
        return img;
    }

    public InputStream getInputSream(BufferedImage bufferedImage, String extension) {
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ImageIO.write(bufferedImage, extension, byteArrayOutputStream);
            return new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
        } catch (IOException e) {
            throw new FileException("Error while reading file");
        }
    }

    public BufferedImage cropSquare(BufferedImage bufferedImage) {
        int min = (bufferedImage.getHeight() <= bufferedImage.getWidth()) ? bufferedImage.getHeight() : bufferedImage.getWidth();
        return Scalr.crop(
                bufferedImage,
                (bufferedImage.getWidth() / 2) - (min / 2),
                (bufferedImage.getHeight() / 2) - (min / 2),
                min,
                min
        );
    }

    public BufferedImage resize(BufferedImage bufferedImage, int size) {
        return Scalr.resize(bufferedImage, Scalr.Method.ULTRA_QUALITY, size);
    }
}
