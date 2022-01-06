package org.alqj.coder.cchat.config;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public final class FileCopy {

    public void copy(InputStream in, File file){
        try{
            OutputStream out = new FileOutputStream(file);
            byte[] buf = new byte[1924];
            int len;
            while((len = in.read(buf)) > 0){
                out.write(buf, 0, len);
            }
            out.close();
            in.close();
        } catch(Exception ex){
            ex.printStackTrace();
        }
    }
}
