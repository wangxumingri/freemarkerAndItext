package com.wxss.freemarkerlearn;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RunWith(SpringRunner.class)
@SpringBootTest
public class FreemarkerlearnApplicationTests {
    private static final Configuration configuration = new Configuration(Configuration.getVersion());
    private static String TEMPLATES_DIRECTORY = "F:/ideaWorkPlace/anonymousP/freemarker-learn/src/main/resources/templates";
    private static String HTML_OUTPUT_DIRECTORY = "D:/freemarker/";

    @Before
    public void beforeTest() {
        try {
            configuration.setDirectoryForTemplateLoading(new File(TEMPLATES_DIRECTORY));
            configuration.setDefaultEncoding("utf-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String translateFtlToHtml2(String ftlFileName, Object model) {
        StringWriter writer = null;
        try {
            // 获取指定模板
            Template template = configuration.getTemplate(ftlFileName);
            writer = new StringWriter();
            // 填充数据
            template.process(model, writer);
            writer.flush();
            return writer.toString();
        } catch (IOException | TemplateException e) {
            e.printStackTrace();
        } finally {
            try {
                assert writer != null;
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return "";
    }

    public void translateFtlToHtml(String ftlFileName, Object model) {
        FileWriter writer = null;
        String outPurFileName;
        try {
            // 获取指定模板
            Template template = configuration.getTemplate(ftlFileName);
            // 输出目录与文件名
            String[] fileNameSplit = ftlFileName.split("\\.");
            assert fileNameSplit.length >= 2;

            String suffix = fileNameSplit[1];
            if ("ftl".equalsIgnoreCase(suffix) || "html".equalsIgnoreCase(suffix)) {
                outPurFileName = ftlFileName.replace(suffix, "html");
            } else {
                return;
            }
            writer = new FileWriter(HTML_OUTPUT_DIRECTORY + outPurFileName);
            // 填充数据
            template.process(model, writer);
        } catch (IOException | TemplateException e) {
            e.printStackTrace();
        } finally {
            try {
                assert writer != null;
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void testMap1() {
        Goods goods = new Goods("马永贞", 666);
        Map<String, Object> modelMap = new HashMap<>();
        modelMap.put("man", goods);
        String s = translateFtlToHtml2("map1.ftl", modelMap);
        System.out.println(s);
    }

    @Test
    public void testObject() {
        Goods goods = new Goods("111", 2222);
        translateFtlToHtml("object.html", goods);
    }

    @Test
    public void testMap() {
        Goods goods = new Goods("马永贞", 666);
        Map<String, Object> modelMap = new HashMap<>();
        modelMap.put("man", goods);
        translateFtlToHtml("map.ftl", modelMap);
    }
    @Test
    public void testList() {
        Goods goods1 = new Goods("111", 111);
        Goods goods2 = new Goods("222", 222);
        Goods goods3 = new Goods("333", 333);
        Goods goods4 = new Goods("444", 444);

        List<Goods> mlist = new ArrayList<>();
        mlist.add(goods1);
        mlist.add(goods2);
        mlist.add(goods3);
        mlist.add(goods4);
        Map<String,Object> map = new HashMap<>();
        map.put("myList", mlist);
        translateFtlToHtml("list.ftl", map);
    }


}
