package com.lg100m.storageservice.model;

import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "DocumentContent", schema = "cgnznt")

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(exclude = "docId")
public class DocumentContent {
    @Id
    @Column(name = "docId")
    String docId;

    @Lob
    @Column(name = "docContent")
    byte[] docContent;

    @Column(name = "insertTimeStamp")
    Timestamp insertTimeStamp;

    @Column(name = "updateTimeStamp")
    Timestamp updateTimeStamp;
}
