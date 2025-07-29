package org.junit.order;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;

public class OrderBackup {
    private Writer writer;

    public Writer getWriter() {
        return writer;
    }

    void createFile() throws FileNotFoundException {
        File file = new File("OrderBackup.txt");
        FileOutputStream fos = new FileOutputStream(file);
        OutputStreamWriter os = new OutputStreamWriter(fos);
        writer = new BufferedWriter(os);
    }

    void closeFie() throws IOException {
        writer.close();
    }

    void backUpOrder(Order order) throws IOException {
        if (writer == null) {
            throw new IOException("Backup file not created");
        }
        writer.append(order.toString());
    }
}
