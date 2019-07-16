package com.lg100m.storageservice.repository;

import com.lg100m.storageservice.model.DocumentContent;
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
public class DocumentContentRepositoryTest {

    @Autowired
    DocumentContentRepository documentContentRepository;

    @Test
    public void shouldSaveDocument() {
        // given
        byte[] content = "AC123456789123456789".getBytes();
        DocumentContent documentContent = DocumentContent.builder()
                .docId("AC123456789123456789")
                .docContent(content)
                .insertTimeStamp(new Timestamp(System.currentTimeMillis()))
                .updateTimeStamp(new Timestamp(System.currentTimeMillis())).build();

        // when - action
        documentContentRepository.save(documentContent);

        // then
        Optional<DocumentContent> byId = documentContentRepository.findById("AC123456789123456789");
        Assertions.assertThat(byId.get().equals(documentContent));
    }

    @Test
    public void shouldLoadDocument() {
        String docId = "AC123456789123456789";
        Optional<DocumentContent> byId = documentContentRepository.findById(docId);
        if (byId.isPresent())
            Assertions.assertThat(byId.get().getDocId().equals(docId));
        else
            Assertions.fail(docId + " does not exist");
    }
}
