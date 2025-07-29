package org.junit.order;

import org.junit.jupiter.api.Test;
import org.junit.order.Order;
import org.junit.order.OrderBackup;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class OrderBackupExecutionOrderTest {

    @Test
    void callingBackupWithoutCreatingAFile() {
        OrderBackup orderBackup = new OrderBackup();
        assertThrows(IOException.class, ()->orderBackup.backUpOrder(new Order()));
    }
}
