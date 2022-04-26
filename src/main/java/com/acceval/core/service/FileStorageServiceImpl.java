package com.acceval.core.service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.acceval.core.filehandler.StorageException;
import com.acceval.core.filehandler.StorageFileNotFoundException;
import com.acceval.core.filehandler.StorageProperties;

@Service
@EnableConfigurationProperties(StorageProperties.class)
public class FileStorageServiceImpl implements FileStorageService {

    private final Path rootLocation;

    @Autowired
    public FileStorageServiceImpl(StorageProperties properties) {
        this.rootLocation = Paths.get(properties.getLocation());
    }
    
    @Override 
    public String store(MultipartFile file, Path archiveLocation) {
    	
        String filename = StringUtils.cleanPath(file.getOriginalFilename());
        
        try {
            if (file.isEmpty()) {
                throw new StorageException("Failed to store empty file " + filename);
            }
            if (filename.contains("..")) {
                // This is a security check
                throw new StorageException("Cannot store file with relative path outside current directory " + filename);
            }
            if (!Files.exists(archiveLocation)) {
		    	try {
					Files.createDirectories(archiveLocation);
				} catch (IOException e) {
					throw new StorageException("Cannot create archive location " + archiveLocation.toString());
				}
    		}
            

            String filenameNoExtension = filename.replaceFirst("[.][^.]+$", "");
            DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd-HHmmss") ;
            String extension = "";
            int index = filename.lastIndexOf(".");
            if (index != -1 && index < filename.length() - 1) {
            	extension = filename.substring(index + 1);
            }
            
            String reformatFile = filenameNoExtension + "-" + dateFormat.format(new Date()) 
            	+ "." + extension;
            
            Path reformatPath = archiveLocation.resolve(reformatFile);
            
            Files.copy(file.getInputStream(), reformatPath, StandardCopyOption.REPLACE_EXISTING);
                       
            return reformatPath.getFileName().toString();
            
        } catch (IOException e) {
        	
            throw new StorageException("Failed to store file " + filename, e);
        }
    }
        
    @Override 
    public String archive(MultipartFile file, Path archiveLocation) {
    	
        String filename = StringUtils.cleanPath(file.getOriginalFilename());
        
        try {
            if (file.isEmpty()) {
                throw new StorageException("Failed to store empty file " + filename);
            }
            if (filename.contains("..")) {
                // This is a security check
                throw new StorageException("Cannot store file with relative path outside current directory " + filename);
            }
            if (!Files.exists(archiveLocation)) {
		    	try {
					Files.createDirectories(archiveLocation);
				} catch (IOException e) {
					throw new StorageException("Cannot create archive location " + archiveLocation.toString());
				}
    		}
            
            Files.copy(file.getInputStream(), archiveLocation.resolve(filename), StandardCopyOption.REPLACE_EXISTING);
            
            String filenameNoExtension = filename.replaceFirst("[.][^.]+$", "");
            DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd-HHmmss") ;
            
            Path zipLocation = archiveLocation.resolve(filenameNoExtension + "-" + dateFormat.format(new Date()) + ".zip");
            Path toBeAdded = archiveLocation.resolve(filename);
            
            Map<String, String> env = new HashMap<String, String>();
            env.put("create", String.valueOf(Files.notExists(zipLocation)));
            
            URI fileUri = zipLocation.toUri();
            URI zipUri = new URI("jar:" + fileUri.getScheme(), fileUri.getPath(), null);           
            
            try (FileSystem zipfs = FileSystems.newFileSystem(zipUri, env)) {
                // Create internal path in the zipfs
                Path internalTargetPath = zipfs.getPath(file.getOriginalFilename());
                
                // copy a file into the zip file
                Files.copy(toBeAdded, internalTargetPath, StandardCopyOption.REPLACE_EXISTING);
                
                Files.delete(toBeAdded);
            }
            
            return zipLocation.getFileName().toString();
            
        } catch (IOException | URISyntaxException e) {
        	
            throw new StorageException("Failed to store file " + filename, e);
        }
    }
    
    

    @Override 
    public void store(String filename, String content) {
        
        try {
            if (content.isEmpty()) {
                throw new StorageException("Failed to store empty file " + filename);
            }
            if (filename.contains("..")) {
                // This is a security check
                throw new StorageException("Cannot store file with relative path outside current directory " + filename);
            }
            
            Files.copy(new ByteArrayInputStream(content.getBytes(StandardCharsets.UTF_8)), this.rootLocation.resolve(filename),
                    StandardCopyOption.REPLACE_EXISTING);
        }
        catch (IOException e) {
            throw new StorageException("Failed to store file " + filename, e);
        }
    }
    
    @Override 
    public void store(MultipartFile file) {
        String filename = StringUtils.cleanPath(file.getOriginalFilename());
        try {
            if (file.isEmpty()) {
                throw new StorageException("Failed to store empty file " + filename);
            }
            if (filename.contains("..")) {
                // This is a security check
                throw new StorageException("Cannot store file with relative path outside current directory " + filename);
            }
            if (!Files.exists(this.rootLocation)) {
		    	try {
					Files.createDirectories(this.rootLocation);
				} catch (IOException e) {
					throw new StorageException("Cannot create root location " + this.rootLocation.toString());
				}
    		}
            Files.copy(file.getInputStream(), this.rootLocation.resolve(filename),
                    StandardCopyOption.REPLACE_EXISTING);
        }
        catch (IOException e) {
            throw new StorageException("Failed to store file " + filename, e);
        }
    }

    @Override
    public Stream<Path> loadAll() {
        try {
            return Files.walk(this.rootLocation, 1)
                    .filter(path -> !path.equals(this.rootLocation))
                    .map(path -> this.rootLocation.relativize(path));
        }
        catch (IOException e) {
            throw new StorageException("Failed to read stored files", e);
        }

    }

    @Override
    public Path load(String filename) {
        return rootLocation.resolve(filename);
    }

    @Override
    public Resource loadAsResource(String filename) {
        try {
            Path file = this.load(filename);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new StorageFileNotFoundException("Could not read file: " + filename);
            }
        }
        catch (MalformedURLException e) {
            throw new StorageFileNotFoundException("Could not read file: " + filename, e);
        }
    }
    
    @Override
    public Resource loadAsResource(Path filePath) {
        try {            
            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new StorageFileNotFoundException("Could not read file: " + filePath.getFileName().toString());
            }
        } catch (MalformedURLException e) {
            throw new StorageFileNotFoundException("Could not read file: " + filePath.getFileName().toString(), e);
        }
    }

    @Override
    public void deleteAll() {
        FileSystemUtils.deleteRecursively(rootLocation.toFile());
    }

    @Override
    public void init() {
        try {
            Files.createDirectories(rootLocation);
        }
        catch (IOException e) {
            throw new StorageException("Could not initialize storage", e);
        }
    }
}
