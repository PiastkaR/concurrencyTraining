package org.multihread.atomic.reference;

import java.util.concurrent.atomic.AtomicReference;

public class Main {
    public static void main(String[] args) {
        String oldName = "old name";
        String newName = "new name";

        AtomicReference<String> atomicReference = new AtomicReference<>(oldName);
        atomicReference.set("UnexpectedName");
        if (atomicReference.compareAndSet(oldName, newName)) {
            System.out.println("New Value is: " + atomicReference.get());
        } else {
            System.out.println("Nothing changed");
        }
    }
}
