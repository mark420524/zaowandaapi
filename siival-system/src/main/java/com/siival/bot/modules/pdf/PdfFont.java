package com.siival.bot.modules.pdf;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.BaseFont;

import java.io.IOException;

import static com.itextpdf.text.pdf.BaseFont.createFont;

/**
 * @author mark acrossxwall@gmail.com
 * @version 1.0.0
 * @ClassName PdfFont
 * 
 * @Date 2022-03-04 21:55
 */

public class PdfFont {

    public static    BaseFont bfChinese;

    static {
        try {
            bfChinese = createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
