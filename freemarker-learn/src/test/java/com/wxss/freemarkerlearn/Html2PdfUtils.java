package com.wxss.freemarkerlearn;

import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.html2pdf.attach.impl.layout.HtmlPageBreak;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.IBlockElement;
import com.itextpdf.layout.element.IElement;
import com.itextpdf.layout.font.FontProvider;
import com.itextpdf.text.DocumentException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * by 明明如月 github :https://github.com/chujianyun
 * 表格边框不显示
 */

public class Html2PdfUtils {

    /**
     * 字体所在目录
     */
    private static final String FONT_RESOURCE_DIR = "/font";

    /**
     * @param htmlContent html文本
     * @param dest        目的文件路径，如 /xxx/xxx.pdf
     * @throws IOException IO异常
     */
    public static void createPdf(String htmlContent, String dest) throws IOException, DocumentException {
        ConverterProperties props = new ConverterProperties();
        // props.setCharset("UFT-8"); 编码
        FontProvider fp = new FontProvider();
        fp.addStandardPdfFonts();
        fp.addSystemFonts();
        props.setFontProvider(fp);
        // 也可以使用本地字体文件：.ttf 字体所在目录
//        String resources = Html2PdfUtils.class.getResource(FONT_RESOURCE_DIR).getPath();
//        fp.addDirectory(resources);
        // html中使用的图片等资源目录（图片也可以直接用url或者base64格式而不放到资源里）
        // props.setBaseUri(resources);

        // 解析html字符串，获取
        List<IElement> elements = HtmlConverter.convertToElements(htmlContent, props);
        PdfDocument pdf = new PdfDocument(new PdfWriter(dest));
        Document document = new Document(pdf, PageSize.A4.rotate(), false);
        for (IElement element : elements) {
            // 分页符
            if (element instanceof HtmlPageBreak) {
                document.add((HtmlPageBreak) element);

                //普通块级元素
            } else {
                document.add((IBlockElement) element);
            }
        }
        document.close();
    }

    /**
     * 返回字节输出流
     *
     * @param htmlContent
     * @return
     * @throws IOException
     * @throws DocumentException
     */
    public static String createPdf(String htmlContent) throws IOException, DocumentException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        ConverterProperties props = new ConverterProperties();
        // props.setCharset("UFT-8"); 编码
        FontProvider fp = new FontProvider();
        fp.addStandardPdfFonts();
        fp.addSystemFonts();
        props.setFontProvider(fp);
        // 也可以使用本地字体文件：.ttf 字体所在目录
//        String resources = Html2PdfUtils.class.getResource(FONT_RESOURCE_DIR).getPath();
//        fp.addDirectory(resources);
        // html中使用的图片等资源目录（图片也可以直接用url或者base64格式而不放到资源里）
        // props.setBaseUri(resources);

        List<IElement> elements = HtmlConverter.convertToElements(htmlContent, props);
        PdfDocument pdf = new PdfDocument(new PdfWriter(baos));
        Document document = new Document(pdf, PageSize.A4.rotate(), false);
        for (IElement element : elements) {
            // 分页符
            if (element instanceof HtmlPageBreak) {
                document.add((HtmlPageBreak) element);

                //普通块级元素
            } else {
                document.add((IBlockElement) element);
            }
        }
        document.close();
        return new String(baos.toByteArray(), StandardCharsets.ISO_8859_1);
    }


}