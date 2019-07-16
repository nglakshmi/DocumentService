package com.lg100m.storageservice.service;

import com.lg100m.storageservice.model.DocumentContent;
import com.lg100m.storageservice.model.DocumentMetaData;
import com.lg100m.storageservice.repository.DocumentContentRepository;
import com.lg100m.storageservice.repository.DocumentRepository;
import lombok.Builder;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Timestamp;
import java.util.Optional;

@Service
public class StorageService implements IStorageService {

    private final StorageProperties properties;

    @Autowired
    DocumentRepository documentRepository;

    @Autowired
    DocumentContentRepository documentContentRepository;

    @Autowired
    public StorageService(StorageProperties properties) {
        this.properties = properties;
    }

    @Override
    public String save(MultipartFile file) {
        String filename = StringUtils.cleanPath(file.getOriginalFilename());

        int idLength = this.properties.getIdLength();
        String docId = StorageUtils.getUUID(idLength);

        try {
            if (saveContent(file, docId)) {
                saveMetadata(file, docId);
                return docId;
            }
        } catch (Exception e) {
            throw new StorageException("Failed to store file " + filename, e);
        }

        return null;
    }

    public boolean save(MultipartFile file, Optional<String> docId1) {

        String filename = StringUtils.cleanPath(file.getOriginalFilename());
        try {
            if (docId1.isPresent()) {
                if (docExists(docId1.get())) {
                    String docId = docId1.get();
                    if (saveContent(file, docId))
                        saveMetadata(file, docId);
                    return true;
                }
            } else {
                throw new StorageFileNotFoundException("Document Not Found " + docId1.get());
            }
        } catch (Exception e) {
            throw new StorageException("Failed to store file " + filename, e);
        }
        return false;
    }

    public boolean docExists(String docId) {
        Optional<DocumentMetaData> metadata = documentRepository.findByDocId(docId);
        if (metadata.isPresent())
            return true;
        return false;
    }

    public boolean saveContent(MultipartFile file, String docId) {
        String fileName = file.getOriginalFilename();
        try {
           DocumentContent documentContent =  DocumentContent.builder()
                    .docId(docId)
                    .docContent(StreamUtils.copyToByteArray(file.getInputStream()))
                    .insertTimeStamp(new Timestamp(System.currentTimeMillis()))
                    .updateTimeStamp(new Timestamp(System.currentTimeMillis()))
                    .build();
            documentContentRepository.save(documentContent);
            return true;
        } catch (Exception e) {
            throw new StorageException("Failed to store file " + fileName, e);
        }
    }

    public boolean saveMetadata(MultipartFile file, String docId) {

        String fileName = file.getOriginalFilename();
        try {
            DocumentMetaData documentMetaData = DocumentMetaData.builder()
                    .docId(docId)
                    .docName(fileName)
                    .docSize(file.getSize())
                    .docType(file.getContentType())
                    .insertTimeStamp(new Timestamp(System.currentTimeMillis()))
                    .updateTimeStamp(new Timestamp(System.currentTimeMillis())).build();

            documentRepository.save(documentMetaData);
            return true;
        } catch (Exception e) {
            throw new StorageException("Failed to store file " + fileName, e);
        }
    }

    @Override
    public DocumentDTO load(String docId) {
        try {
            Optional<DocumentContent> content = documentContentRepository.findById(docId);
            Optional<DocumentMetaData> metadata = documentRepository.findById(docId);

            if (content.isPresent() && metadata.isPresent()) {
                DocumentDTO documentDTO = DocumentDTO.builder().docId(docId)
                        .docName(metadata.get().getDocName())
                        .docType(metadata.get().getDocType())
                        .docSize(metadata.get().getDocSize())
                        .docContent(content.get().getDocContent()).build();
                return documentDTO;
            } else {
                throw new StorageFileNotFoundException(
                        "Could not read file: " + docId);

            }
        } catch (Exception e) {
            throw new StorageFileNotFoundException("Could not read file: " + docId, e);
        }
    }

    @Override
    public boolean delete(String docId) {
        if (docExists(docId)) {
            documentRepository.deleteById(docId);
            documentContentRepository.deleteById(docId);
            return true;
        }
        return false;
    }

    @Data
    @Builder
    public static class DocumentDTO {
        String docId;
        String docType;
        String docName;
        long docSize;
        byte[] docContent;
        Timestamp insertTimeStamp;
        Timestamp updateTimeStamp;
    }

}

