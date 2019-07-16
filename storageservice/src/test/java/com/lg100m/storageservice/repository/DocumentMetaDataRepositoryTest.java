package com.lg100m.storageservice.repository;

import com.lg100m.storageservice.model.DocumentMetaData;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Timestamp;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DocumentMetaDataRepositoryTest {

    @Autowired
    DocumentRepository documentRepository;

    @Test
    public void shouldSaveDocument() {

        // given
        DocumentMetaData documentMetaData = DocumentMetaData.builder()
                .docId("A123456789123456789")
                .docName("DNameA123456789123456789")
                .docSize(100)
                .docType("PDF")
                .insertTimeStamp(new Timestamp(System.currentTimeMillis()))
                .updateTimeStamp(new Timestamp(System.currentTimeMillis())).build();

        // when - action
        documentRepository.save(documentMetaData);

        // then
        Optional<DocumentMetaData> byId = documentRepository.findById("A123456789123456789");
        Assertions.assertThat(byId.get().equals(documentMetaData));

    }

    @Test
    public void shouldLoadDocument() {
        String docId = "A123456789123456789";
        Optional<DocumentMetaData> byId = documentRepository.findById(docId);
        if (byId.isPresent())
            Assertions.assertThat(byId.get().getDocId().equals(docId));
        else
            Assertions.fail(docId + " does not exist");
    }

}