package com.wiz.bookmanager.service.storage;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.Resource;

import java.io.File;
import java.nio.file.Path;
import java.util.stream.Stream;

public interface StorageService {

    public void init();

    public Path store(MultipartFile file);

    public Stream<Path> loadAll();

    public Path load(String filename);

    public Resource loadAsResource(String filename);

    public void deleteAll();
}
