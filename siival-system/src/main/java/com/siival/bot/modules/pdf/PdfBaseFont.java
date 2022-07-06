package com.siival.bot.modules.pdf;


import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.GrayColor;

import java.io.IOException;


/**
 * @author mark acrossxwall@gmail.com
 * @version 1.0.0
 * @ClassName PdfFont
 * 
 * @Date 2022-03-04 21:55
 */

public class PdfBaseFont {

    public static BaseFont bfChinese;

    static {
        try {
            bfChinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static final Font FONT = new Font(PdfFont.bfChinese, 30, Font.BOLD, new GrayColor(0.92f));

}
