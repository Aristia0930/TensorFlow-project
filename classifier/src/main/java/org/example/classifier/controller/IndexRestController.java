package org.example.classifier.controller;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.tensorflow.SavedModelBundle;
import org.tensorflow.Session;
import org.tensorflow.Tensor;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.FloatBuffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.stream.IntStream;

@RestController
public class IndexRestController {

    @Value("${custom.model-path}")
    private String path;

    private  SavedModelBundle model;
    private final String[] categories = {"cats", "dogs"}; // 클래스 이름

    public IndexRestController() {

    }
    @PostConstruct
    public void init() {
        try {
            model = SavedModelBundle.load(path, "serve");
        } catch (Exception e) {
            e.printStackTrace();
            // 경로 문제나 모델 로딩 실패 시 로그 또는 에러 처리
            System.out.println("Failed to load the model from path: " + path);
        }
    }

    @PostMapping("/classify")
    public String classifyImage(@RequestParam("file") MultipartFile file ) throws IOException {

        // 업로드된 파일을 임시 경로에 저장
        Path tempFile = Files.createTempFile("image", file.getOriginalFilename());
        Files.write(tempFile, file.getBytes());

        System.out.println("Temporary file created: " + tempFile.toString());

        // 이미지를 읽어 Tensor로 변환
        BufferedImage img = ImageIO.read(tempFile.toFile());
        Tensor inputTensor = preprocessImage(img);


        // 모델 예측 실행
        Session session = model.session();
        Tensor result = session.runner().feed("serving_default_conv2d_input:0", inputTensor) // input layer name 수정
                .fetch("StatefulPartitionedCall") // output layer name 수정
                .run().get(0);


        // 예측 결과를 배열로 변환
        float[][] output = new float[1][categories.length];  // 카테고리 수에 맞춘 배열 크기
        result.copyTo(output);

        // 가장 높은 확률을 가진 클래스 찾기
        int predictedClassIndex = getMaxIndex(output[0]);
        System.out.println("Prediction output: " + Arrays.toString(output[0]));

        return categories[predictedClassIndex];

    }

    private Tensor preprocessImage(BufferedImage img) {
        // 이미지 리사이즈 및 정규화 (예: 150x150, [0, 1] 범위로 스케일링)
        BufferedImage resizedImg = resizeImage(img, 150, 150);
        float[] flatImageArray = new float[150 * 150 * 3]; // 1차원 배열 생성

        // 2D 배열을 1D로 변환
        int index = 0;
        for (int y = 0; y < 150; y++) {
            for (int x = 0; x < 150; x++) {
                int rgb = resizedImg.getRGB(x, y);
                flatImageArray[index++] = ((rgb >> 16) & 0xFF) / 255.0f; // R
                flatImageArray[index++] = ((rgb >> 8) & 0xFF) / 255.0f;  // G
                flatImageArray[index++] = (rgb & 0xFF) / 255.0f;         // B
            }
        }

        return Tensor.create(new long[]{1, 150, 150, 3}, FloatBuffer.wrap(flatImageArray)); // 1D 배열을 Tensor로 생성
    }

    private BufferedImage resizeImage(BufferedImage originalImage, int targetWidth, int targetHeight) {
        BufferedImage resizedImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_RGB);
        resizedImage.getGraphics().drawImage(originalImage, 0, 0, targetWidth, targetHeight, null);
        return resizedImage;
    }

    private int getMaxIndex(float[] predictions) {
        return IntStream.range(0, predictions.length)
                .reduce((i, j) -> predictions[i] > predictions[j] ? i : j)
                .orElse(-1);
    }
}
