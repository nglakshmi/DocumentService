package com.lg100m.storageservice.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;

@Entity
@Table(name = "DocumentMetaData", schema = "cgnznt")

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(exclude = "docId")
public class DocumentMetaData {

    @Id
    // @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "docId")
    String docId;

    @Column(name = "docType")
    String docType;

    @Column(name = "docName")
    String docName;

    @Column(name = "docSize")
    long docSize;

    @Column(name = "userId")
    String userId;

    @Column(name = "insertTimeStamp")
    Timestamp insertTimeStamp;

    @Column(name = "updateTimeStamp")
    Timestamp updateTimeStamp;

}
