package com.yevmie.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.UUID;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

import org.apache.commons.io.FilenameUtils;
import org.springframework.web.multipart.MultipartFile;

import com.yevmie.exception.BusinessException;
import com.yevmie.exception.ValidationException;

import lombok.experimental.UtilityClass;

@UtilityClass
public class FileUtil {
    
    private static final String[] ALLOWED_FILE_TYPES = {
        "image/jpeg", "image/png", "application/pdf"
    };
    
    private static final long MAX_FILE_SIZE = 5 * 1024 * 1024; // 5MB

    public static void validateFile(MultipartFile file) {
        if (file.isEmpty()) {
            throw new ValidationException("Dosya boş olamaz");
        }

        if (file.getSize() > MAX_FILE_SIZE) {
            throw new ValidationException("Dosya boyutu 5MB'dan büyük olamaz");
        }

        String contentType = file.getContentType();
        if (!Arrays.asList(ALLOWED_FILE_TYPES).contains(contentType)) {
            throw new ValidationException("Desteklenmeyen dosya türü. " +
                "İzin verilen türler: JPEG, PNG, PDF");
        }
    }

    public static String generateUniqueFileName(String originalFileName) {
        String extension = FilenameUtils.getExtension(originalFileName);
        return UUID.randomUUID().toString() + "." + extension;
    }

    public static void createDirectoryIfNotExists(Path directory) {
        try {
            if (!Files.exists(directory)) {
                Files.createDirectories(directory);
            }
        } catch (IOException e) {
            throw new BusinessException("Dizin oluşturulamadı", e);
        }
    }

    public static byte[] compressImage(byte[] data) throws IOException {
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            Deflater deflater = new Deflater();
            deflater.setLevel(Deflater.BEST_COMPRESSION);
            deflater.setInput(data);
            deflater.finish();

            byte[] buffer = new byte[4096];
            while (!deflater.finished()) {
                int count = deflater.deflate(buffer);
                outputStream.write(buffer, 0, count);
            }
            return outputStream.toByteArray();
        }
    }

    public static byte[] decompressImage(byte[] data) throws IOException {
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            Inflater inflater = new Inflater();
            inflater.setInput(data);

            byte[] buffer = new byte[4096];
            while (!inflater.finished()) {
                int count = inflater.inflate(buffer);
                outputStream.write(buffer, 0, count);
            }
            return outputStream.toByteArray();
        } catch (DataFormatException e) {
            throw new IOException("Sıkıştırılmış veri açılamadı", e);
        }
    }
} 