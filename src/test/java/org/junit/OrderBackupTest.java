package org.junit;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class OrderBackupTest {
    private static OrderBackup orderBackup;

    @BeforeAll
    static void setup() throws FileNotFoundException {
        orderBackup = new OrderBackup();
        orderBackup.createFile();
    }
    @BeforeEach
    void appendAtTheStartOfTheLine() throws IOException{
        orderBackup.getWriter().append("NewOrder:");
    }

    @BeforeEach
    void appendAtTheEndOfTheLine() throws IOException{
        orderBackup.getWriter().append("backed");
    }

    @AfterAll
    static void tearDown() throws IOException {
        orderBackup.closeFie();
    }

    @Test
    void backupOrderWithIbeMeal() throws IOException {
        //given
        Meal meal1 = new Meal(8, "Soup");
        Order order = new Order();
        order.addMealToOrdedr(meal1);
        //when
        orderBackup.backUpOrder(order);
        //then
        System.out.println("Order: " + order + "backed up.");
    }


}