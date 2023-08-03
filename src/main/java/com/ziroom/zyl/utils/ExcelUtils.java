package com.ziroom.zyl.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.util.Assert;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.List;
import java.util.Objects;

@Slf4j
public class ExcelUtils {

    public static void exportExcel(HttpServletResponse response, String fileName, ExcelData... datas) throws IllegalArgumentException, IOException {
        log.info("excel export start. fileName:[{}]", fileName);
        //实例化HSSFWorkbook
        XSSFWorkbook workbook = new XSSFWorkbook();
        for (ExcelData data : datas) {
            Assert.isTrue(data.checkDataCompleteness(), "excel data header.length must equal data.length");
            //创建一个Excel表单，参数为sheet的名字
            String sheetName = data.getSheetName();
            XSSFSheet sheet = Strings.isNotEmpty(sheetName) ? workbook.createSheet(sheetName) : workbook.createSheet();
            //设置表头
            setTitle(workbook, sheet, data.getHeader());
            //设置单元格并赋值
            setData(sheet, data.getData());
        }
        //设置浏览器下载
        setBrowser(response, workbook, fileName + ".xlsx");
        log.info("excel export success. fileName:[{}]", fileName);
    }

    public interface ExcelData {

        default String getSheetName() {
            return null;
        }

        String[] getHeader();

        List<String[]> getData();


        default boolean checkDataCompleteness() {
            String[] header = getHeader();
            List<String[]> data = getData();
            if (Objects.nonNull(header) && Objects.nonNull(data)) {
                long completeCount = data.stream().filter(d -> Objects.nonNull(d) && d.length == header.length).count();
                return completeCount == data.size();
            }
            return true;
        }
    }

    private static void setTitle(XSSFWorkbook workbook, XSSFSheet sheet, String[] str) {
        Row row = sheet.createRow(0);
        //设置列宽，setColumnWidth的第二个参数要乘以256，这个参数的单位是1/256个字符宽度
        for (int i = 0; i <= str.length; i++) {
            sheet.setColumnWidth(i, 15 * 256);
        }
        //设置为居中加粗,格式化时间格式
        XSSFCellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        style.setFont(font);
        style.setDataFormat(HSSFDataFormat.getBuiltinFormat("m/d/yy h:mm"));
        //创建表头名称
        Cell cell;
        for (int j = 0; j < str.length; j++) {
            cell = row.createCell(j);
            cell.setCellValue(str[j]);
            cell.setCellStyle(style);
        }
    }

    private static void setData(XSSFSheet sheet, List<String[]> data) {
        int rowNum = 1;
        for (int i = 0; i < data.size(); i++) {
            Row row = sheet.createRow(rowNum);
            for (int j = 0; j < data.get(i).length; j++) {
                row.createCell(j).setCellValue(data.get(i)[j]);
            }
            rowNum++;
        }
    }

    private static void setBrowser(HttpServletResponse response, XSSFWorkbook workbook, String fileName) throws IOException {
        response.setHeader("Access-Control-Expose-Headers","Content-Disposition");
        response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName,"UTF-8"));
        OutputStream os = new BufferedOutputStream(response.getOutputStream());
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        response.setCharacterEncoding("utf-8");
        workbook.write(os);
        os.flush();
        os.close();
    }
}
