package com.lg100m.storageservice.service;

import org.assertj.core.api.Assertions;
import org.junit.Test;

public class StorageUtilsTest {

    @Test
    public void testGetUUID() {
        String uuid = StorageUtils.getUUID(20);
        System.out.println(uuid);
        if (uuid.length() != 20)
            Assertions.fail("uuid length is incorrect " + uuid.length());
        else
            Assertions.assertThat(uuid.length() == 20);
    }

}