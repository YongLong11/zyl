package com.zyl.service;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.read.metadata.ReadSheet;
import com.zyl.listener.ExcelListener;
import com.zyl.utils.ExcelUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class EasyExcelService {

    public <T> void writeExcel(HttpServletResponse response, String fileName,Integer sheetNo, List<T> data) throws IOException {
        // 防止中文乱码
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Access-Control-Expose-Headers","Content-Disposition");
        response.setCharacterEncoding("utf-8");
        fileName = URLEncoder.encode(fileName, "UTF-8").replaceAll("\\+", "%20");
        response.setHeader("Content-disposition", String.format("attachment;filename=%s", fileName + ".xlsx"));

        ServletOutputStream outputStream = response.getOutputStream();
        try {
            EasyExcel.write(outputStream).sheet(sheetNo).doWrite(data);
            log.info("写入完成");
        } catch (Exception e){
            log.error("{} 写入错误", fileName, e);
        }
    }

    public <T> List<T> readExcel(String filename, Class<T> clazz, int sheetNo) {
        if (!filename.endsWith("xlsx")) {
            return new ArrayList<>();
        }
        ExcelReader excelReader = null;
        List<T> list = new ArrayList<>();
        try {
            EasyExcel.read(filename, clazz, new ExcelListener<T>(list)).sheet(sheetNo).build();
            ReadSheet readSheet = EasyExcel.readSheet(sheetNo).build();
            excelReader.read(readSheet);
        } finally {
            if (excelReader != null) {
                excelReader.finish();
            }
        }
        return list;
    }


    public void xssDown(HttpServletResponse response){
        ExcelUtils.ExcelData[] excelDataNums = new ExcelUtils.ExcelData[2];
        for (int j = 0; j < 2; j++) {
            int finalJ = j;
            ExcelUtils.ExcelData excelData = new ExcelUtils.ExcelData() {
                @Override
                public String getSheetName() {
                    return "导出" + finalJ;
                }
                @Override
                public String[] getHeader() {
                    return new String[]{"编号", "姓名", "年龄"};
                }

                @Override
                public List<String[]> getData() {
                    List<String[]> datas = new ArrayList<>();
                    for (int i = 0; i < 2; i++) {
                        String[] data = new String[]{
                                "1",
                                "哈哈",
                                "15"
                        };
                        datas.add(data);
                    }
                    return datas;
                }
            };
            excelDataNums[j] = excelData;
        }

        try {
        ExcelUtils.exportExcel(response, "导出", excelDataNums);
        }catch (Exception e){
            log.error("error", e);
        }
    }

}
