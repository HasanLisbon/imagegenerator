package com.ai.imagegenerator;

import org.springframework.ai.image.ImageResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.InputStream;
import java.net.URL;
import java.util.Base64;
import java.util.List;

@RestController
public class GenAiController {

    private ImageGenService imageGenService;

    public GenAiController(ImageGenService imageGenService){
        this.imageGenService = imageGenService;
    }

    @GetMapping("/image-gen")
    public List<String> getImage(@RequestParam String prompt,
                                 @RequestParam(defaultValue = "hd") String quality,
                                 @RequestParam(defaultValue = "1") int n,
                                 @RequestParam(defaultValue = "1024") int height,
                                 @RequestParam(defaultValue = "1024") int width){
        ImageResponse imageResponse = imageGenService.generateImage(prompt, quality, n, height, width);
        return imageResponse.getResults().stream()
                .map(
                        result ->
                        {
                            try {
                                return convertImageToBase64(result.getOutput().getUrl());
                            } catch (Exception e) {
                                throw new RuntimeException(e);
                            }
                        })
                .toList();
    }

    private static String convertImageToBase64(String imageUrl) throws Exception {
        // Open a connection to the URL
        URL url = new URL(imageUrl);
        try (InputStream inputStream = url.openStream()) {
            // Read the content of the image into a byte array
            byte[] imageBytes = inputStream.readAllBytes();

            // Convert the byte array to a Base64 string
            return Base64.getEncoder().encodeToString(imageBytes);
        }
    }
}
