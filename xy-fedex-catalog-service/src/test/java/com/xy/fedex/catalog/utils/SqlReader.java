package com.xy.fedex.catalog.utils;

import org.springframework.core.io.ClassPathResource;

import java.io.FileInputStream;
import java.io.InputStream;

public class SqlReader {

    public static String read(String file) {
        try {
            ClassPathResource classPathResource = new ClassPathResource(file);
            InputStream in = classPathResource.getInputStream();
            StringBuilder sb = new StringBuilder();
            byte[] buff = new byte[1024];
            int byteRead = 0;
            while ((byteRead = in.read(buff)) != -1) {
                sb.append(new String(buff,0,byteRead,"utf-8"));
            }
            in.close();
            return sb.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
