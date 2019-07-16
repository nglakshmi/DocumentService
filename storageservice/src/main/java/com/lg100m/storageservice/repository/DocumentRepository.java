package com.lg100m.storageservice.repository;

import com.lg100m.storageservice.model.DocumentMetaData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DocumentRepository extends JpaRepository<DocumentMetaData, String> {
    Optional<DocumentMetaData> findByDocId(String docId);
}
