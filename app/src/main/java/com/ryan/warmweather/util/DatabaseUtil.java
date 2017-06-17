package com.ryan.warmweather.util;


import com.ryan.warmweather.MyApplication;



import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by Think on 2017/6/16.
 */

public class DatabaseUtil {
    public static String copyDatabase(String SQLiteFileName)throws IOException {
        File dir = new File("data/data/" + MyApplication.getContext().getPackageName() + "/databases");
        if (!dir.exists() || !dir.isDirectory()) {
            dir.mkdir();
        }
        File file = new File(dir, SQLiteFileName);
        InputStream is = null;
        FileOutputStream fos = null;
        if (!file.exists()) {
            try {
                file.createNewFile();
                is = MyApplication.getContext().getClass().getClassLoader().
                        getResourceAsStream("assets/" + SQLiteFileName);
                fos = new FileOutputStream(file);

                byte[] bytes = new byte[1024];
                int len;
                while ((len = is.read(bytes)) != -1) {
                    fos.write(bytes, 0, len);
                }

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (fos != null) {
                        fos.flush();
                        fos.close();
                }
                if (is != null) {
                        is.close();
                    }
                }
            }


            return file.getPath();
        }
    }
