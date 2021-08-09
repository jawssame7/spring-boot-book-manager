package com.wiz.bookmanager.service.storage;

import com.wiz.bookmanager.exception.StorageException;
import com.wiz.bookmanager.exception.StorageFileNotFoundException;
import com.wiz.bookmanager.properties.StorageProperties;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.MalformedURLException;
import java.nio.file.*;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

/**
 * ファイルシステムサービスクラス
 */
@Service
public class FileSystemStorageServiceImpl implements StorageService {

    /**
     * パスロケーション
     */
    private final Path rootLocation;

    /**
     * コンストラクター
     * @param properties
     */
    @Autowired
    public FileSystemStorageServiceImpl(StorageProperties properties) {
        this.rootLocation = Paths.get(properties.getLocation());
    }

    /**
     * 初期処理 ディレクトリを作成
     */
    @Override
    public void init() {
        try {
            // ディレクトリがなければ新規作成
            if (!Files.exists(rootLocation)) {
                Files.createDirectories(rootLocation);
            }
        }
        catch (IOException e) {
            throw new StorageException("Could not initialize storage", e);
        }
    }

    /**
     * ファイル保存
     * @param file ファイル
     */
    @Override
    public Path store(MultipartFile file) {
        String newFileName = this.makeRandomFileName(file);
        Path newFilePath = this.rootLocation.resolve(
                                Paths.get(newFileName))
                            .normalize().toAbsolutePath();

        try {
            if (file.isEmpty()) {
                throw new StorageException("Failed to store empty file.");
            }

            if (!newFilePath.getParent().equals(this.rootLocation.toAbsolutePath())) {
                // This is a security check
                throw new StorageException(
                        "Cannot store file outside current directory.");
            }

            file.transferTo(newFilePath);

        } catch (IOException e) {
            throw new StorageException("Failed to store file.", e);
        }

        return newFilePath;
    }

    /**
     * ファイルロケーション内のファイルをすべてロードして返す
     * @return
     */
    @Override
    public Stream<Path> loadAll() {
        try {
            return Files.walk(this.rootLocation, 1)
                    .filter(path -> !path.equals(this.rootLocation))
                    .map(this.rootLocation::relativize);
        }
        catch (IOException e) {
            throw new StorageException("Failed to read stored files", e);
        }
    }

    /**
     * ファイルロケーション内で指定したファイルを返す
     * @param filename
     * @return ファイル
     */
    @Override
    public Path load(String filename) {
        return rootLocation.resolve(filename);
    }

    /**
     *
     * @param filename
     * @return
     */
    @Override
    public Resource loadAsResource(String filename) {
        try {
            Path file = load(filename);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            }
            else {
                throw new StorageFileNotFoundException(
                        "Could not read file: " + filename);

            }
        }
        catch (MalformedURLException e) {
            throw new StorageFileNotFoundException("Could not read file: " + filename, e);
        }
    }

    /**
     * ファイルロケーション内のすべてのファイルを削除
     */
    @Override
    public void deleteAll() {
        FileSystemUtils.deleteRecursively(rootLocation.toFile());
    }

    /**
     * ランダムな文字列でファイル名を作成して返す
     * @param file
     * @return
     */
    private String makeRandomFileName(MultipartFile file) {
        String ext = FilenameUtils.getExtension(file.getOriginalFilename());
        String fileName = RandomStringUtils.randomAlphanumeric(35).toUpperCase();
        StringBuilder randomFileName = new StringBuilder();
        randomFileName.append(fileName);
        randomFileName.append(".");
        randomFileName.append(ext);
        return randomFileName.toString();
    }
}
