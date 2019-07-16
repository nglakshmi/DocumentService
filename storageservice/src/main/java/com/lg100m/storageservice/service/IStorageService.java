package com.lg100m.storageservice.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

public interface IStorageService {

    String save(MultipartFile file);

    boolean save(MultipartFile file, Optional<String> docId);

   StorageService.DocumentDTO load(String docId);

    boolean delete(String docId);

}
