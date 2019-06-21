package com.wxss.freemarkerlearn;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import com.itextpdf.tool.xml.XMLWorkerFontProvider;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.List;

/**
 * Author:Created by wx on 2019/6/14
 * Desc:
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ItextTests {

    /**
     * 创建一个最简单的PDF文档
     */
    @Test
    public void testCreateSimplePdf() {
        Document document = new Document();
        PdfWriter pdfWriter = null;
        try {
            pdfWriter = PdfWriter.getInstance(document, new FileOutputStream("D:/中文支持.pdf"));
            /**中文字体支持*/
            BaseFont baseFont = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
            Font font = new Font(baseFont);
            font.setSize(12);
            // 文档基本属性
            document.open();
            document.addAuthor("wx");// 作者
            document.addTitle("PDF生成测试"); // 标题
            document.addSubject("this is subject"); //主题
            document.addKeywords("Keywords"); //关键字
            document.addCreationDate();//创建时间
            document.addCreator("LOL");// 创建程序
            Paragraph paragraph = new Paragraph("让iText生成的PDF支持中文字体", font);// 段落

            document.add(paragraph);
        } catch (DocumentException | IOException e) {
            e.printStackTrace();
        } finally {
            document.close();
            assert pdfWriter != null;
            pdfWriter.close();
        }
    }

    @Test
    public void addImage() {
        Document document = new Document();
        PdfWriter pdfWriter = null;
        try {
            pdfWriter = PdfWriter.getInstance(document, new FileOutputStream("D:/test2.pdf"));
            document.open();
            // Itext的Image类
            Image image1 = Image.getInstance("C:\\Users\\Public\\Pictures\\Sample Pictures\\Git常用命令.jpg");
            //设置图片位置的x轴和y周: 以pdf页面为基准，x轴和y轴从页面左下角开始为0点定位。
            image1.setAbsolutePosition(100f, 550f);
            //设置图片的宽度和高度
            image1.scaleAbsolute(400, 200);
            document.add(image1);
        } catch (DocumentException | IOException e) {
            e.printStackTrace();
        } finally {
            document.close();
            assert pdfWriter != null;
            pdfWriter.close();
        }
    }


    @Test
    public void createOnlyByApi() throws Exception {
        // 默认A4
        try {
            Document document = new Document();
            FileOutputStream outputFile = new FileOutputStream("D:/国家免疫规划疫苗儿童免疫程序表11111.pdf");
            PdfWriter writer = PdfWriter.getInstance(document, outputFile);
            document.open();
            /**中文字体支持*/
            BaseFont baseFont = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
            Font bigFont = new Font(baseFont);
            bigFont.setSize(20);
            Font font = new Font(baseFont);
            font.setSize(10);

            // 标题
            Paragraph paragraph = new Paragraph("国家免疫规划疫苗儿童免疫程序表", bigFont);
            paragraph.setAlignment(Element.ALIGN_CENTER);
            document.add(paragraph);

            // 创建table
            PdfPTable table = new PdfPTable(9);
            /**设置表格属性*/
            // 没有设置宽度：com.itextpdf.text.DocumentException: java.lang.RuntimeException: The table width must be greater than zero.
            // 设置表格总宽度
            table.setTotalWidth(500);
            // 设置表格的宽度2：也可以为每一列指定宽度：没有设置就会 根据列数平均分配
            table.setTotalWidth(new float[]{70, 70, 50, 50, 50, 50, 50, 50, 50});
            // 设置表格占页面的宽度比：100%
            table.setWidthPercentage(100);

            // 锁住宽度
            table.setLockedWidth(true);
            // 设置表格上面空白宽度
            table.setSpacingBefore(10f);
            // 设置表格下面空白宽度
            table.setSpacingAfter(10f);
            // 设置表格默认为无边框
            table.getDefaultCell().setBorder(1);

            List<PdfPCell> pCellList = new ArrayList<>();

            /** 创建单元格:*/
            // 第一行
            PdfPCell cell1OfFirstRow = new PdfPCell();
            cell1OfFirstRow.setColspan(2);
            Paragraph p = new Paragraph("疫苗种类", font);
            p.setAlignment(Element.ALIGN_CENTER);  // 对文本单独设置：可以解决水平居中，但无法解决垂直居中
            cell1OfFirstRow.addElement(p);

            pCellList.add(cell1OfFirstRow);

            PdfPCell cell2OfFirstRow = new PdfPCell();
            cell2OfFirstRow.setColspan(7);
            cell2OfFirstRow.addElement(new Paragraph("接种年月（龄）", font));
            pCellList.add(cell2OfFirstRow);

            // 第二行
            PdfPCell cell1OfSecondRow = new PdfPCell();
            cell1OfSecondRow.addElement(new Paragraph("名称", font));
            pCellList.add(cell1OfSecondRow);

            PdfPCell cell2OfSecondRow = new PdfPCell();
            cell2OfSecondRow.addElement(new Paragraph("缩写", font));
            pCellList.add(cell2OfSecondRow);

            for (int i = 0; i < 7; i++) {
                PdfPCell cell = new PdfPCell();
                String content = i + "月";
                cell.addElement(new Paragraph(content, font));
                pCellList.add(cell);
            }
            // 第三行

            PdfPCell cell1OfThirdRow = new PdfPCell();
            cell1OfThirdRow.addElement(new Paragraph("乙肝疫苗", font));

            PdfPCell cell2OfThirddRow = new PdfPCell();
            cell2OfThirddRow.addElement(new Paragraph("HepB", font));

            for (int i = 0; i < 7; i++) {
                PdfPCell cell = new PdfPCell();
                pCellList.add(cell);
            }

            setTable(table, pCellList);
            document.add(table);

            document.close();
        } catch (DocumentException | IOException e) {
            e.printStackTrace();
        }
    }

    public void setTable(PdfPTable table, List<PdfPCell> pCellList) {
        assert pCellList != null;
        for (PdfPCell cell : pCellList) {
            // 设置单元格左外边距
//            cell.setPaddingLeft(10);
            // 设置单元格右外边距
//            cell.setPaddingRight(10f);
            // 设置高度
            cell.setFixedHeight(30);

//            cell.setMinimumHeight(17); //设置单元格高度
//            cell.setUseAscender(true);
//            cell.setUseDescender(true);
            cell.setUseAscender(true);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            // 设置水平居中
//            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            // 设置单元格内容垂直居中
//            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            // 设置高度
            /**在此处调用，内容不显示*/
//            cell.setFixedHeight(30);
            // 将单元格放入表中
            table.addCell(cell);
        }
    }
    /**
     * 表格各种属性综合使用
     *
     * @throws IOException
     * @throws DocumentException
     */
    @Test
    public void exampleFromNet() throws IOException, DocumentException {
        Document document = new Document();
        // 创建PdfWriter对象
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("D:/complexTable22.pdf"));
        // 打开文档
        document.open();

        // 添加表格，4列
        PdfPTable table = new PdfPTable(4);
        //// 设置表格宽度比例为%100
        table.setWidthPercentage(100);
        // 设置表格的宽度
        table.setTotalWidth(500);
        // 也可以每列分别设置宽度
//         table.setTotalWidth(new float[]{160, 70, 130, 100,200}); // 和列数对应
        // 锁住宽度
        table.setLockedWidth(true);
        // 设置表格上面空白宽度
        table.setSpacingBefore(10f);
        // 设置表格下面空白宽度
        table.setSpacingAfter(10f);
        // 设置表格默认为无边框
        table.getDefaultCell().setBorder(0);
        PdfContentByte cb = writer.getDirectContent();

        // 构建每个单元格
        PdfPCell cell1 = new PdfPCell(new Paragraph("Cell 1"));
        // 边框颜色
        cell1.setBorderColor(BaseColor.BLUE);
        // 设置背景颜色
        cell1.setBackgroundColor(BaseColor.ORANGE);
        // 设置跨两行
        cell1.setRowspan(2);
        // 设置距左边距
        cell1.setPaddingLeft(10);
        // 设置高度
        cell1.setFixedHeight(20);
        // 设置内容水平居中显示
        cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
        // 设置垂直居中
        cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
        table.addCell(cell1);

        PdfPCell cell2 = new PdfPCell(new Paragraph("Cell 2"));
        cell2.setBorderColor(BaseColor.GREEN);
        cell2.setPaddingLeft(10);
        cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell2.setVerticalAlignment(Element.ALIGN_MIDDLE);
        table.addCell(cell2);

        PdfPCell cell3 = new PdfPCell(new Paragraph("Cell 3"));
        cell3.setBorderColor(BaseColor.RED);
        cell3.setPaddingLeft(10);
        // 设置无边框
        cell3.setBorder(Rectangle.NO_BORDER);
        cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell3.setVerticalAlignment(Element.ALIGN_MIDDLE);
        table.addCell(cell3);

        // 在表格添加图片
        Image cellimg = Image.getInstance("C:\\Users\\Public\\Pictures\\Sample Pictures\\Git常用命令.jpg");
        PdfPCell cell4 = new PdfPCell(cellimg, true);
        cell4.setBorderColor(BaseColor.RED);
        cell4.setPaddingLeft(10);
        cell4.setFixedHeight(30);
        cell4.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell4.setVerticalAlignment(Element.ALIGN_MIDDLE);
        table.addCell(cell4);

        // 增加一个条形码到表格
        Barcode128 code128 = new Barcode128();
        code128.setCode("14785236987541");
        code128.setCodeType(Barcode128.CODE128);
        // 生成条形码图片
        Image code128Image = code128.createImageWithBarcode(cb, null, null);
        // 加入到表格
        PdfPCell cellcode = new PdfPCell(code128Image, true);
        cellcode.setHorizontalAlignment(Element.ALIGN_CENTER);
        cellcode.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cellcode.setFixedHeight(30);
        table.addCell(cellcode);

        PdfPCell cell5 = new PdfPCell(new Paragraph("Cell 5"));
        cell5.setPaddingLeft(10);
        // 设置占用列数
        cell5.setColspan(2);
        cell5.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell5.setVerticalAlignment(Element.ALIGN_MIDDLE);
        table.addCell(cell5);
        document.add(table);

        // 关闭文档
        document.close();

    }

    /**
     * 分离html和css:格式异常
     */
    @Test
    public void createChildByHtml() {
        Document document = new Document();
        try {
            InputStream html = this.getClass().getClassLoader().getResourceAsStream("templates/child.html");
            // 格式异常
            InputStream css = this.getClass().getClassLoader().getResourceAsStream("templates/child.css");
            PdfWriter pdfWriter = PdfWriter.getInstance(document, new FileOutputStream("D:/直接编写html来生成PDF7.pdf"));
            document.open();
            // 这种方式也行，将字符串转成字节流
//            String htmlStr = testGetHtmlStr();
//            ByteArrayInputStream b = new ByteArrayInputStream(htmlStr.getBytes());

            XMLWorkerHelper.getInstance().parseXHtml(pdfWriter, document, html, css,  Charset.forName("UTF-8"),new ChineseFontProvider());
        } catch (DocumentException | IOException e) {
            e.printStackTrace();
        } finally {
            document.close();
        }
    }

    /**
     * ok
     */
    public String testGetHtmlStr() {
        InputStream resourceAsStream = this.getClass().getClassLoader().getResourceAsStream("templates/child2.ftl");
        int length = 0;
        byte[] data = new byte[1024];
        StringBuilder builder = new StringBuilder();
        try {
            while ((length = resourceAsStream.read(data)) != -1) {
                String buffer = new String(data, 0, length, StandardCharsets.UTF_8);
                builder.append(buffer);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return builder.toString();
    }

    /**
     * 生成静态页面的
     *
     * @param ftlFileName
     * @param model
     * @return
     */
    public String translateFtlToHtml(String ftlFileName, Object model) {
        StringWriter writer = null;
        try {
            Configuration configuration = new Configuration(Configuration.getVersion());
            configuration.setDirectoryForTemplateLoading(new File("F:/ideaWorkPlace/anonymousP/freemarker-learn/src/main/resources/templates"));
            configuration.setDefaultEncoding("utf-8");
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

    /**
     * 返回流，而不是生成文件
     */
    @Test
    public void testCreatePdfByTranslateHtmlToFtl2(){
        ByteArrayInputStream byteStreamOfPdf = null;
        FileOutputStream outputStream = null;
        try {
            Map<String,List<VaccineImmunizationInChildData>> rootDateModel = new HashMap<>();
            List<VaccineImmunizationInChildData> vaccineImmunizationInChildDataList = new ArrayList<>();


            VaccineImmunizationInChildData v1 = new VaccineImmunizationInChildData();
            v1.setVaccineName("乙肝疫苗");
            v1.setAbbreviation("HepB");
            String[] time = new String[15];
            time[0] = "1";
            time[5] = "3";
            time[9] = "2";
            time[14] = "16";
            v1.setVaccinationTime(time);
            vaccineImmunizationInChildDataList.add(v1);

            v1 = new VaccineImmunizationInChildData();
            v1.setVaccineName("卡介苗");
            v1.setAbbreviation("BCG");
            String[] time2 = new String[15];
            time2[0] = "1";
            time2[7] = "2";
            v1.setVaccinationTime(time2);
            vaccineImmunizationInChildDataList.add(v1);

            v1 = new VaccineImmunizationInChildData();
            v1.setVaccineName("脊灰灭活疫苗");
            v1.setAbbreviation("IPV");
            String[] time3 = new String[15];
            time3[2] = "1";
            time3[5] = "2";
            time3[14] = "16";
            v1.setVaccinationTime(time3);
            vaccineImmunizationInChildDataList.add(v1);

            rootDateModel.put("myList", vaccineImmunizationInChildDataList);

            // 获取抓换后的ftl字符串
            String ftlStr = translateFtlToHtml("child3.ftl", rootDateModel);
            // PDF字节流的字符串形式
            String result = Html2PdfUtils.createPdf(ftlStr);
            // 将PDF字符串转码后，生成输入字节输入流
            byteStreamOfPdf = new ByteArrayInputStream(result.getBytes(StandardCharsets.ISO_8859_1));

            // 输入目的地
            outputStream = new FileOutputStream(new File("D:/生成流.pdf"));
            // 读取字节输入流，将数据写入到文件输出流中，生成PDF文件
            byte[] b = new byte[2048];
            int len;
            while ((len = byteStreamOfPdf.read(b)) !=-1){
                outputStream.write(b, 0, len);
            }
        } catch (IOException | DocumentException e) {
            e.printStackTrace();
        } finally {
            try {
                assert outputStream != null;
                outputStream.close();
                byteStreamOfPdf.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }



    }


    /**
     * 失败
     */
    @Test
    public void testCreatePdfByTranslateHtmlToFtl3(){
        Map<String,List<VaccineImmunizationInChildData>> rootDateModel = new HashMap<>();
        List<VaccineImmunizationInChildData> vaccineImmunizationInChildDataList = new ArrayList<>();

        VaccineImmunizationInChildData v1 = new VaccineImmunizationInChildData();
        v1.setVaccineName("乙肝疫苗");
        v1.setAbbreviation("HepB");
        String[] time = new String[15];
        time[0] = "1";
        time[5] = "3";
        time[9] = "2";
        time[14] = "16";

        v1.setVaccinationTime(time);
        vaccineImmunizationInChildDataList.add(v1);

        v1 = new VaccineImmunizationInChildData();
        v1.setVaccineName("卡介苗");
        v1.setAbbreviation("BCG");
        String[] time2 = new String[15];
        time2[0] = "1";
        time2[7] = "2";
        v1.setVaccinationTime(time2);
        vaccineImmunizationInChildDataList.add(v1);

        v1 = new VaccineImmunizationInChildData();
        v1.setVaccineName("脊灰灭活疫苗");
        v1.setAbbreviation("IPV");
        String[] time3 = new String[15];
        time3[2] = "1";
        time3[5] = "2";
        time3[14] = "16";
        v1.setVaccinationTime(time3);
        vaccineImmunizationInChildDataList.add(v1);

        rootDateModel.put("myList", vaccineImmunizationInChildDataList);
        // 获取抓换后的ftl字符串
        String ftlStr = translateFtlToHtml("child3.ftl", rootDateModel);
        // 转成字节输入流
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(ftlStr.getBytes(StandardCharsets.UTF_8));
        try {
            Document document = new Document();
            PdfWriter pdfWriter = PdfWriter.getInstance(document, new FileOutputStream("D:/结合Api2.pdf"));
            document.open();
            // 无法设置中文字体
//            XMLWorkerHelper.getInstance().parseXHtml(pdfWriter, document, new StringReader(ftlStr));
            // 也不行
            XMLWorkerHelper.getInstance().parseXHtml(pdfWriter, document,byteArrayInputStream,null,Charset.forName("utf-8"),new ChineseFontProvider());

            document.close();
        } catch (DocumentException | IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * 直接生成pdf文件，而不是返回流
     */
    @Test
    public void testCreatePdfByTranslateHtmlToFtl1(){
        Map<String,List<VaccineImmunizationInChildData>> rootDateModel = new HashMap<>();
        List<VaccineImmunizationInChildData> vaccineImmunizationInChildDataList = new ArrayList<>();

        VaccineImmunizationInChildData v1 = new VaccineImmunizationInChildData();
        v1.setVaccineName("乙肝疫苗");
        v1.setAbbreviation("HepB");
        String[] time = new String[15];
        time[0] = "1";
        time[5] = "3";
        time[9] = "2";
        time[14] = "16";

        v1.setVaccinationTime(time);
        vaccineImmunizationInChildDataList.add(v1);

        v1 = new VaccineImmunizationInChildData();
        v1.setVaccineName("卡介苗");
        v1.setAbbreviation("BCG");
        String[] time2 = new String[15];
        time2[0] = "1";
        time2[7] = "2";
        v1.setVaccinationTime(time2);
        vaccineImmunizationInChildDataList.add(v1);

        v1 = new VaccineImmunizationInChildData();
        v1.setVaccineName("脊灰灭活疫苗");
        v1.setAbbreviation("IPV");
        String[] time3 = new String[15];
        time3[2] = "1";
        time3[5] = "2";
        time3[14] = "16";
        v1.setVaccinationTime(time3);
        vaccineImmunizationInChildDataList.add(v1);

        rootDateModel.put("myList", vaccineImmunizationInChildDataList);
        // 获取抓换后的ftl字符串
        String ftlStr = translateFtlToHtml("child3.ftl", rootDateModel);

        try {
            // 直接生成PDF文件
            Html2PdfUtils.createPdf(ftlStr,"D:/直接生成pdf文件11.pdf");
        } catch (IOException | DocumentException e) {
            e.printStackTrace();
        }
    }

    /**
     * 合并多个输入流
     * @param firstIs
     * @param secondIs
     */
    public void hebing(InputStream firstIs,InputStream secondIs) {
        SequenceInputStream sis = null;
        FileOutputStream fos = null;
        try {
            Vector<InputStream> v = new Vector<InputStream>();

            v.add(firstIs);
            v.add(secondIs);
            Enumeration<InputStream> en = v.elements();
            sis = new SequenceInputStream(en);
            fos = new FileOutputStream("D:/HEBING.pdf");
            byte[] buf = new byte[1024];
            int len = 0;
            //因为SequenceInputStream是InputStream的子类，所以可以调用read方法。
            while((len=sis.read(buf)) != -1){
                fos.write(buf,0,len);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
                sis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
    @Test
    public void testCreatePdfByTranslateHtmlToFtl(){
        Goods goods = new Goods("测试数据", 100);
        String ftlStr = translateFtlToHtml("child2.ftl", goods);
        try {
            Html2PdfUtils.createPdf(ftlStr,"D:/ testCreatePdfByTranslateHtmlToFtl22.pdf");
        } catch (IOException | DocumentException e) {
            e.printStackTrace();
        }
    }

    /**
     * 不能分离html+css，无法自动加载css
     * 只在table加边框，html能显示，但pdf不显示边框
     * 只在td上加边框，都能显示，但html和pdf的最外层边框与其它边框相比更细
     * 都加:
     *  table 使用style： 外边框正常，html与pdf边框相同
     *  table 使用border属性：html边框相同,但PDF的外边框与其它边框相比更细
     * @throws IOException
     * @throws DocumentException
     */
    @Test
    public void testUtils() throws IOException, DocumentException {
        String s = testGetHtmlStr();

        System.out.println(s);
        Html2PdfUtils.createPdf(s,"D:/正常666.pdf");
    }


    /**
     * 官方文档
     * @throws FileNotFoundException
     * @throws DocumentException
     */
    @Test
    public void testOfficialDocument() throws FileNotFoundException, DocumentException {
        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream("D:/服了ssaa.pdf"));
        document.open();
        PdfPTable table = new PdfPTable(3);
        PdfPCell cell = new PdfPCell(new Phrase("Cell with colspan 3"));
        cell.setColspan(3);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);
        cell = new PdfPCell(new Phrase("Cell with rowspan 2"));
        cell.setRowspan(2);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        table.addCell(cell);
        table.addCell("Cell 1.1");
        cell = new PdfPCell();
        cell.addElement(new Phrase("Cell 1.2"));
        table.addCell(cell);
        cell = new PdfPCell(new Phrase("Cell 2.1"));
//        cell.setPadding(5);
        cell.setUseAscender(true);
        cell.setUseDescender(true);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);
        cell = new PdfPCell();
        cell.setPadding(5);
        cell.setUseAscender(true);
        cell.setUseDescender(true);
        Paragraph p = new Paragraph("Cell 2.2");
        cell.setUseAscender(true);
        cell.setUseDescender(true);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);

//        p.setAlignment(Element.ALIGN_CENTER);
        cell.addElement(p);
        table.addCell(cell);
        document.add(table);
        document.close();
    }
}
