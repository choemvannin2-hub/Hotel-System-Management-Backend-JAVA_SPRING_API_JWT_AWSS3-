package com.choem_vannin.service;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.choem_vannin.execption.BadRequestException;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.UUID;

@Service
public class AwsS3Service {

    @Value("${aws.s3.bucket-name:choemvannin-hotel-images}")
    private String bucketName;

    @Value("${aws.s3.access-key}")
    private String awsS3AccessKey;

    @Value("${aws.s3.secret-key}")
    private String awsS3SecretKey;

    private AmazonS3 s3Client;

    // Initialize s3Client once on bean startup, not on every method call
    @PostConstruct
    private void initS3Client() {
        BasicAWSCredentials awsCredentials = new BasicAWSCredentials(awsS3AccessKey, awsS3SecretKey);
        this.s3Client = AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
                .withRegion(Regions.AP_SOUTHEAST_2)
                .build();
    }

    public String saveImageToS3(MultipartFile photo) {
        if (photo == null || photo.isEmpty()) {
            throw new BadRequestException("Image file cannot be empty");
        }

        try {
            // 1. Generate unique file name to avoid overwriting existing files
            String originalFilename = StringUtils.cleanPath(
                    photo.getOriginalFilename() != null ? photo.getOriginalFilename() : "image"
            );
            String extension = "";
            if (originalFilename.contains(".")) {
                extension = originalFilename.substring(originalFilename.lastIndexOf("."));
            }
            String s3FileName = "rooms/" + UUID.randomUUID() + extension;

            // 2. Set dynamic Content Type and required Content Length
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentType(photo.getContentType() != null ? photo.getContentType() : "image/jpeg");
            metadata.setContentLength(photo.getSize()); // Crucial for S3 InputStream performance

            // 3. Upload stream
            try (InputStream inputStream = photo.getInputStream()) {
                PutObjectRequest putObjectRequest = new PutObjectRequest(
                        bucketName,
                        s3FileName,
                        inputStream,
                        metadata
                );

                s3Client.putObject(putObjectRequest);
            }

            // 4. Return standard public S3 URL for ap-southeast-2
            return String.format("https://%s.s3.ap-southeast-2.amazonaws.com/%s", bucketName, s3FileName);

        } catch (Exception ex) {
            throw new BadRequestException("Unable to upload image to s3 bucket: " + ex.getMessage());
        }
    }
}