package com.siival.bot.modules.pdf;


import com.alibaba.fastjson.JSON;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import com.itextpdf.text.pdf.draw.DottedLineSeparator;
import com.itextpdf.text.pdf.draw.LineSeparator;
import com.siival.bot.modules.api.config.PdfExportConfig;
import com.siival.bot.modules.api.resp.QuestionsVo;
import com.siival.bot.modules.api.resp.SelectListInfo;
import com.siival.bot.utils.StringUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PdfExport {

	// main测试
	public static void main(String[] args) throws Exception {
		String title = "早晚答";
		String author = "Mark";
		String subject = "早晚答题";
		String keyword = "小程序，答题";
		String creator = "markjava@qq.com";
		String waterMark= "早晚答";
		String outputFileName = "E:\\tiku\\PDFDemo.pdf";
		PdfExportConfig config = new PdfExportConfig();
//		config.setPassword("123456");
		config.setAuthor(author);
		config.setSubject(subject);
		config.setKeyword(keyword);
		config.setWaterMark(waterMark);
		config.setCreator(creator);
		config.setBarcode("zaowandabarcode.jpg");
		config.setSlogan("美好生活，你我一起");
		List<QuestionsVo> list = new ArrayList<>();
		QuestionsVo vo = new QuestionsVo();
		String options = "[{\"title\": \"A . 3个月\"}, {\"title\": \"B . 5个月\"}, {\"title\": \"C . 8个月\"}, {\"right\": 1, \"title\": \"D . 12个月\"}, {\"title\": \"E . 15个月\"}]";
		vo.setQuestion("[单选] 有一健康女婴，身长68 cm，体重7．5 kg，前囟1．0 cm，头围46cm，已出牙4颗，可独坐，并能用拇食指拿取小球。其最可能的月龄是");
		List<SelectListInfo> infos = JSON.parseArray(options,SelectListInfo.class);
		vo.setSelectList(infos);
		vo.setRightAnswer("F");
		list.add(vo);
		list.add(vo);
		writeFileToPdf( title, config,list, outputFileName);
	}

	public static void writeFileToPdf(String title , PdfExportConfig config, List<QuestionsVo> list, String outputFileName) {
		// 1.新建document对象
		Document document = new Document(PageSize.A4);// 建立一个Document对象
		try {
			// 2.建立一个书写器(Writer)与document对象关联
			File file = new File(outputFileName);
			file.createNewFile();
			PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(file));
			writer.setPageEvent(new Watermark(config.getWaterMark()));// 水印
			writer.setPageEvent(new MyHeaderFooter(config.getWaterMark()+"-"+title ));// 页眉/页脚
//			writer.setPdfVersion(PdfWriter.PDF_VERSION_1_2);
			byte [] password = null;
			if (StringUtils.isNotBlank(config.getPassword())) {
				password = config.getPassword().getBytes(StandardCharsets.UTF_8);
			}
			writer.setEncryption(password,PdfConstant.OWNER_PASSWORD.getBytes(StandardCharsets.UTF_8),
					PdfWriter.ALLOW_MODIFY_ANNOTATIONS,
					PdfWriter.ENCRYPTION_AES_256
					);


			// 3.打开文档
			document.open();
			document.addTitle(title );// 标题
			document.addAuthor(config.getAuthor());// 作者
			document.addSubject(config.getAuthor());// 主题
			document.addKeywords(config.getKeyword());// 关键字
			document.addCreator(config.getCreator());// 创建者

			// 4.向文档中添加内容
			generatePDF(list, document, config.getSlogan(), config.getBarcode());



		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 5.关闭文档
			document.close();
		}
	}

	// 定义全局的字体静态变量
	private static Font titlefont;
	private static Font headfont;
	private static Font keyfont;
	private static Font textfont;
	// 最大宽度
	private static int maxWidth = 520;
	// 静态代码块
	static {
		try {
			// 不同字体（这里定义为同一种字体：包含不同字号、不同style）

			titlefont = new Font(PdfFont.bfChinese, 16, Font.BOLD);
			headfont = new Font(PdfFont.bfChinese, 14, Font.BOLD);
			keyfont = new Font(PdfFont.bfChinese, 10, Font.BOLD);
			textfont = new Font(PdfFont.bfChinese, 10, Font.NORMAL);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 生成PDF文件
	public static void generatePDF(List<QuestionsVo> list, Document document,String slogan,String barcode) throws Exception {

		// 段落
		Paragraph paragraph = new Paragraph(slogan, titlefont);
		paragraph.setAlignment(1); //设置文字居中 0靠左   1，居中     2，靠右
		paragraph.setIndentationLeft(12); //设置左缩进
		paragraph.setIndentationRight(12); //设置右缩进
		paragraph.setFirstLineIndent(24); //设置首行缩进
		paragraph.setLeading(20f); //行间距
		paragraph.setSpacingBefore(5f); //设置段落上空白
		paragraph.setSpacingAfter(10f); //设置段落下空白

		// 直线
		Paragraph p1 = new Paragraph();
		p1.add(new Chunk(new LineSeparator()));

		// 点线
		Paragraph p2 = new Paragraph( );
		p2.add(new Chunk(new DottedLineSeparator()));
		Anchor answerTo = new Anchor("answer");
		answerTo.setReference("#answer");

//		// 超链接
//		Anchor anchor = new Anchor("baidu");
//		anchor.setReference("www.baidu.com");

		// 定位
//		Anchor gotoP = new Anchor("goto");
//		gotoP.setReference("#top");

		// 添加图片
		Image barcodeImage = Image.getInstance(barcode);

		barcodeImage.setAlignment(Image.ALIGN_CENTER);
		barcodeImage.scalePercent(40); //依照比例缩放

		// 表格

		document.add(paragraph);
		document.add(p2);
		document.add(barcodeImage);
		document.add(p1);
		for (int i=0;i<list.size();i++  ) {
			QuestionsVo vo = list.get(i);
			Paragraph questionParagraph = createParagraph((i+1)+"."+vo.getQuestion());
			document.add(questionParagraph);
			for (SelectListInfo info : vo.getSelectList()) {
				Paragraph optionParagraph = createParagraph(info.getTitle());
				document.add(optionParagraph);
			}
		}
		//生成题库内容
		document.add(p1);
		//下面时答案
		List<String> answer =  buildAnswerByQuestion(list);

		PdfPTable table = generateAnswer(answer  );
		document.add(table);

	}

	private static Paragraph createParagraph(String question) {
		Paragraph paragraph = new Paragraph(question, textfont);
		paragraph.setAlignment(0); //设置文字居中 0靠左   1，居中     2，靠右
		paragraph.setIndentationLeft(12); //设置左缩进
		paragraph.setIndentationRight(12); //设置右缩进
		paragraph.setFirstLineIndent(24); //设置首行缩进
		paragraph.setLeading(15f); //行间距
		paragraph.setSpacingBefore(2f); //设置段落上空白
		paragraph.setSpacingAfter(5f); //设置段落下空白
		return paragraph;
	}

	private static List<String> buildAnswerByQuestion(List<QuestionsVo> list) {
		return list.stream().map(QuestionsVo::getRightAnswer ).collect(Collectors.toList());
	}

	public static PdfPTable generateAnswer(List<String> answer ) {
		if (answer==null || answer.size()==0) {
			return null;
		}
		PdfPTable table = null;
		int a = 0;
		if (answer.size()>6) {
			table = createTable(new float[] { 40, 40, 40, 40, 40, 40 });
			a = 6 - answer.size()%6;
		}else{
			float[] f = new float[answer.size()];
			for (int i=0;i<f.length;i++) {
				f[i] = 40f;
			}
			table = createTable(f);
		}

		table.addCell(createCell("答案", headfont, Element.ALIGN_LEFT, 6, false));
		for (int i=0;i<answer.size();i++) {
			table.addCell(createCell((i+1)+". "+answer.get(i), textfont));
		}
		for (int i=0;i<a;i++) {
			table.addCell(createCell("",textfont));
		}
		return table;
	}


/**------------------------创建表格单元格的方法start----------------------------*/
	/**
	 * 创建单元格(指定字体)
	 * @param value
	 * @param font
	 * @return
	 */
	public static PdfPCell createCell(String value, Font font) {
		PdfPCell cell = new PdfPCell();
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setPhrase(new Phrase(value, font));
		return cell;
	}
	/**
	 * 创建单元格（指定字体、水平..）
	 * @param value
	 * @param font
	 * @param align
	 * @return
	 */
	public static PdfPCell createCell(String value, Font font, int align) {
		PdfPCell cell = new PdfPCell();
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell.setHorizontalAlignment(align);
		cell.setPhrase(new Phrase(value, font));
		return cell;
	}
	/**
	 * 创建单元格（指定字体、水平居..、单元格跨x列合并）
	 * @param value
	 * @param font
	 * @param align
	 * @param colspan
	 * @return
	 */
	public static PdfPCell createCell(String value, Font font, int align, int colspan) {
		PdfPCell cell = new PdfPCell();
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell.setHorizontalAlignment(align);
		cell.setColspan(colspan);
		cell.setPhrase(new Phrase(value, font));
		return cell;
	}
	/**
	 * 创建单元格（指定字体、水平居..、单元格跨x列合并、设置单元格内边距）
	 * @param value
	 * @param font
	 * @param align
	 * @param colspan
	 * @param boderFlag
	 * @return
	 */
	public static PdfPCell createCell(String value, Font font, int align, int colspan, boolean boderFlag) {
		PdfPCell cell = new PdfPCell();
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell.setHorizontalAlignment(align);
		cell.setColspan(colspan);
		cell.setPhrase(new Phrase(value, font));
		cell.setPadding(3.0f);
		if (!boderFlag) {
			cell.setBorder(0);
			cell.setPaddingTop(15.0f);
			cell.setPaddingBottom(8.0f);
		} else if (boderFlag) {
			cell.setBorder(0);
			cell.setPaddingTop(0.0f);
			cell.setPaddingBottom(15.0f);
		}
		return cell;
	}
	/**
	 * 创建单元格（指定字体、水平..、边框宽度：0表示无边框、内边距）
	 * @param value
	 * @param font
	 * @param align
	 * @param borderWidth
	 * @param paddingSize
	 * @param flag
	 * @return
	 */
	public static PdfPCell createCell(String value, Font font, int align, float[] borderWidth, float[] paddingSize, boolean flag) {
		PdfPCell cell = new PdfPCell();
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell.setHorizontalAlignment(align);
		cell.setPhrase(new Phrase(value, font));
		cell.setBorderWidthLeft(borderWidth[0]);
		cell.setBorderWidthRight(borderWidth[1]);
		cell.setBorderWidthTop(borderWidth[2]);
		cell.setBorderWidthBottom(borderWidth[3]);
		cell.setPaddingTop(paddingSize[0]);
		cell.setPaddingBottom(paddingSize[1]);
		if (flag) {
			cell.setColspan(2);
		}
		return cell;
	}
/**------------------------创建表格单元格的方法end----------------------------*/


/**--------------------------创建表格的方法start------------------- ---------*/
	/**
	 * 创建默认列宽，指定列数、水平(居中、右、左)的表格
	 * @param colNumber
	 * @param align
	 * @return
	 */
	public static PdfPTable createTable(int colNumber, int align) {
		PdfPTable table = new PdfPTable(colNumber);
		try {
			table.setTotalWidth(maxWidth);
			table.setLockedWidth(true);
			table.setHorizontalAlignment(align);
			table.getDefaultCell().setBorder(1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return table;
	}
	/**
	 * 创建指定列宽、列数的表格
	 * @param widths
	 * @return
	 */
	public static PdfPTable createTable(float[] widths) {
		PdfPTable table = new PdfPTable(widths);
		try {

			table.setTotalWidth(maxWidth);
			table.setLockedWidth(true);
			table.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.getDefaultCell().setBorder(1);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return table;
	}
	/**
	 * 创建空白的表格
	 * @return
	 */
	public static PdfPTable createBlankTable() {
		PdfPTable table = new PdfPTable(1);
		table.getDefaultCell().setBorder(0);
		table.addCell(createCell("", keyfont));
		table.setSpacingAfter(20.0f);
		table.setSpacingBefore(20.0f);
		return table;
	}
/**--------------------------创建表格的方法end------------------- ---------*/


}
