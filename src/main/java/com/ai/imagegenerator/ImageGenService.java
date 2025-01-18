package com.ai.imagegenerator;

import org.springframework.ai.image.ImagePrompt;
import org.springframework.ai.image.ImageResponse;
import org.springframework.ai.openai.OpenAiImageModel;
import org.springframework.ai.openai.OpenAiImageOptions;
import org.springframework.stereotype.Service;

@Service
public class ImageGenService {
    private OpenAiImageModel imageModel;

    public ImageGenService(OpenAiImageModel imageModel) {
        this.imageModel = imageModel;
    }

    public ImageResponse generateImage(String prompt, String quality, int n, int height, int width){
        return imageModel.call(
                new ImagePrompt(prompt,
                        OpenAiImageOptions.builder()
                                .model("dall-e-2")
                                .quality(quality)
                                .N(n)
                                .height(height)
                                .width(width).build())
        );
    }
}
