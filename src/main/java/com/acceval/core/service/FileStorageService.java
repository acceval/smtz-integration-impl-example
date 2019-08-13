package com.acceval.core.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.stream.Stream;

public interface FileStorageService {

    void init();

    void store(MultipartFile file);

    Stream<Path> loadAll();

    Path load(String filename);

    Resource loadAsResource(String filename);
    
    Resource loadAsResource(Path filePath);

    void deleteAll();

	void store(String filename, String content);
	
	String archive(MultipartFile file, Path archiveLocation);

}
