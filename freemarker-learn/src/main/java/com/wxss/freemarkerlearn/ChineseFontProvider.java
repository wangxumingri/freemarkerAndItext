package com.wxss.freemarkerlearn;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontProvider;
import com.itextpdf.text.pdf.BaseFont;

/**
 * Author:Created by wx on 2019/6/17
 * Desc:
 */
public class ChineseFontProvider implements FontProvider {
    @Override
    public Font getFont(String arg0, String arg1, boolean arg2, float arg3, int arg4, BaseColor arg5) {
        BaseFont bfChinese = null;
        try {
            bfChinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
            //也可以使用Windows系统字体(TrueType)
            //bfChinese = BaseFont.createFont("C:/WINDOWS/Fonts/SIMYOU.TTF", BaseFont.IDENTITY_H,BaseFont.NOT_EMBEDDED);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Font(bfChinese, 14, Font.NORMAL);
    }

    @Override
    public boolean isRegistered(String arg0) {
        return false;
    }
}
