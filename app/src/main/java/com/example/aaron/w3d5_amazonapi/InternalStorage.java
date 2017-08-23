package com.example.aaron.w3d5_amazonapi;

import android.content.Context;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Created by aaron on 8/22/17.
 */

public final class InternalStorage {
    public InternalStorage() {}

    public static void writeString(Context context, String key, String string) throws IOException {
        FileOutputStream fileOutputStream = context.openFileOutput(key, Context.MODE_PRIVATE);
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
        objectOutputStream.writeObject(string);
        objectOutputStream.close();
        fileOutputStream.close();
    }
    public static String readString(Context context, String key) throws IOException, ClassNotFoundException {
        FileInputStream fileInputStream = context.openFileInput(key);
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
        String object = objectInputStream.readObject().toString();
        return object;

    }
    public static boolean fileExistance(String fname, Context context){
        File file = context.getFileStreamPath(fname);
        return file.exists();
    }
}
