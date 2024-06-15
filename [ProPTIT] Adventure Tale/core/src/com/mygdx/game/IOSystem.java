package com.mygdx.game;

import java.io.*;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

public class IOSystem {

    private static IOSystem ioSystemInstance;
    public IOSystem() {
    }
    public static IOSystem getInstance() {
        if (ioSystemInstance == null) {
            ioSystemInstance = new IOSystem();
        }
        return ioSystemInstance;
    }
    public  <T> List<T> read(String filePath) throws IOException, ClassNotFoundException {
        List<T> list = new ArrayList<>();
        FileInputStream fileStream = null;
        ObjectInputStream a = null;
        try {
            fileStream = new FileInputStream(filePath);
            a = new ObjectInputStream(fileStream);
            list = (List<T>) a.readObject();
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException("File not found!");
        } catch (IOException e) {
            throw new IOException("Read file failed!");
        } catch (ClassNotFoundException e) {
            throw new ClassNotFoundException("Class not found!");
        } finally {
            a.close();
            fileStream.close();
        }
        return list;
    }

    public  <T> void write( List<T> list, String filePath) throws IOException {
        FileOutputStream fileStream = null;
        ObjectOutputStream a = null;
        try {
            fileStream = new FileOutputStream(filePath);
            a = new ObjectOutputStream(fileStream);
            a.writeObject(list);
            System.out.println("Write file " + filePath + " successfully!");
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException("File not found!");
        } catch (SocketException e) {
            throw new SocketException("Socket exception!");
        } catch (UnknownHostException e) {
            throw new UnknownHostException("Unknown host exception!");
        } catch (IOException e) {
            throw new IOException("Write file failed!");
        } finally {
            a.close();
            fileStream.close();
        }
    }

    public  <T> void clearData(String filePath) throws IOException {
        List<T> list = new ArrayList<>();
        FileOutputStream fileStream = null;
        ObjectOutputStream a = null;
        try {
            fileStream = new FileOutputStream(filePath);
            a = new ObjectOutputStream(fileStream);
            a.writeObject(list);

        } catch (IOException e) {
            throw new IOException("Clear data failed!");
        } finally {
            a.close();
            fileStream.close();
        }
    }
}