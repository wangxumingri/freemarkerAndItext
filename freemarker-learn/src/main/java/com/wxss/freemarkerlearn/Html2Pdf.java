package com.wxss.freemarkerlearn;

/**
 * Author:Created by wx on 2019/6/18
 * Desc:
 */

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerFontProvider;
import com.itextpdf.tool.xml.XMLWorkerHelper;

/**
 * 样式有问题
 */
public class Html2Pdf {
    public static void main(String[] args) throws Exception {
        String urlsource = getURLSource(new File("F:\\ideaWorkPlace\\anonymousP\\freemarker-learn\\src\\main\\resources\\templates\\child.html"));
        String cssSource = getURLSource(new File("F:\\ideaWorkPlace\\anonymousP\\freemarker-learn\\src\\main\\resources\\templates\\child.css"));
        htmlToPdf(urlsource, cssSource);
    }

    // 支持中文
    public static void htmlToPdf(String htmlstr, String cssSource) throws Exception {
        String outputFile = "D:/te11s11st.pdf";
        Document document = new Document();
        PdfWriter writer = null;
        writer = PdfWriter.getInstance(document, new FileOutputStream(outputFile));
        document.open();

        InputStream bis = new ByteArrayInputStream(htmlstr.getBytes());
        InputStream cssis = new ByteArrayInputStream(cssSource.getBytes());
        XMLWorkerHelper.getInstance().parseXHtml(writer, document, bis, (InputStream) null, new XMLWorkerFontProvider() {
            @Override
            public Font getFont(final String fontname, final String encoding, final boolean embedded, final float size, final int style, final BaseColor color) {
                BaseFont bf = null;
                try {
                    bf = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Font font = new Font(bf, size, style, color);
                font.setColor(color);
                return font;
            }
        });
        document.close();
    }

    /**
     * 通过网站域名URL获取该网站的源码
     *
     * @param url
     * @return String
     * @throws Exception
     */
    public static String getURLSource(File url) throws Exception {
        InputStream inStream = new FileInputStream(url);
        // 通过输入流获取html二进制数据
        byte[] data = readInputStream(inStream); // 把二进制数据转化为byte字节数据
        String htmlSource = new String(data);

        inStream.close();
        return htmlSource;
    }

    /**
     * 把二进制流转化为byte字节数组
     *
     * @param instream
     * @return byte[]
     * @throws Exception
     */
    public static byte[] readInputStream(InputStream instream) throws Exception {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1204];
        int len = 0;
        while ((len = instream.read(buffer)) != -1) {
            outStream.write(buffer, 0, len);
        }
        instream.close();
        return outStream.toByteArray();
    }
}

