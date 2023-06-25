package com.xy.fedex.catalog.utils;

import com.alibaba.druid.sql.ast.statement.SQLSelect;
import com.xy.fedex.dsl.utility.SQLExprUtils;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    @Test
    public void testGetSelectItem() {
        String sql = "select sum(${1})/sum(${2})";
        SQLSelect sqlSelect = SQLExprUtils.parse(sql);
        System.out.println("-----");

        String pattern = "\\$\\{(.+?)\\}";
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(sql);
        while (m.find()) {
            System.out.println(m.group());
        }
    }
}
