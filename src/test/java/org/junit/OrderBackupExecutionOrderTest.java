package org.junit;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class OrderBackupExecutionOrderTest {

    @Test
    void callingBackupWithoutCreatingAFile() {
        OrderBackup orderBackup = new OrderBackup();
        assertThrows(IOException.class, ()->orderBackup.backUpOrder(new Order()));
    }
}
