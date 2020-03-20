package com.example.justloginregistertest.ClassSpace;
import java.io.ByteArrayOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;

public class convert {
    public static Object b2o(byte[] buffer) {
        Object obj = null;
        try {
            ByteArrayInputStream buffers = new ByteArrayInputStream(buffer);
            ObjectInputStream in = new ObjectInputStream(buffers);
            obj = in.readObject();
            in.close();
        } catch (Exception e) {
            System.out.println("error");
        }
        return obj;
    }
    public static byte[] o2b(Object s) {
        ByteArrayOutputStream buffers = new ByteArrayOutputStream();
        try {
            ObjectOutputStream out = new ObjectOutputStream(buffers);
            out.writeObject(s);
            out.close();
        } catch (Exception e) {
            System.out.println("error");
            return null;
        }
        return buffers.toByteArray();
    }

}
