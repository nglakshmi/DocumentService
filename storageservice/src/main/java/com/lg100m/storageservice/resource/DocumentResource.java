package com.lg100m.storageservice.resource;

import com.lg100m.storageservice.service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@RestController

public class DocumentResource {

    @Autowired
    StorageService storageService;

    @RequestMapping(value = "/documents", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<String> saveDocument(@RequestParam("file") MultipartFile file) {
        try {
            String docId = storageService.save(file);
            return ResponseEntity.status(HttpStatus.CREATED).
                    body(docId);
        } catch (Exception e) {
            return new ResponseEntity("Could not upload document", null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ResponseBody
    @RequestMapping(value = "/documents/{docId}", method = RequestMethod.GET)
    public ResponseEntity<Resource> downloadFile(@PathVariable String docId) {
        try {
            StorageService.DocumentDTO documentDTO = storageService.load(docId);

            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(documentDTO.getDocType()))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + docId + "\"")
                    .body(new ByteArrayResource(documentDTO.getDocContent()));
        } catch (Exception e) {
            return new ResponseEntity(null, null, HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/documents/{docId}", method = RequestMethod.PUT)
    public ResponseEntity<String> updateDocument(@PathVariable String docId,
                                                 @RequestParam("file") MultipartFile file) {
        if (storageService.save(file, Optional.of(docId))) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("");
        }
    }

    @RequestMapping(value = "/documents/{docId}", method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteDocument(@PathVariable("docId") String docId) {
        if (storageService.delete(docId)) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("");
        }
    }

}
