package com.love.low.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.metadata.fill.FillConfig;
import com.love.low.enmus.ExcelTemplate;
import com.love.low.exception.LoveLowException;
import com.love.low.model.dto.ExcelExportDTO;
import com.love.low.service.ExcelService;
import lombok.RequiredArgsConstructor;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * @name: ExcelServiceImpl
 * @description: Excel操作业务层实现
 * @author: BrownSugar
 * @date: 2024-04-02 12:06:16
 * @version: 1.0.0
 * @see ExcelService
 **/
@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class ExcelServiceImpl implements ExcelService {

    private static final Logger LOG = LoggerFactory.getLogger(ExcelServiceImpl.class);

    /**
     * @description: 导出Excel数据
     * @param: [excelTemplate, sheetMap, response]
     * @author: BrownSugar
     * @date: 2024-04-02 03:05:11
     **/
    @Override
    public void exportExcel(ExcelTemplate excelTemplate, Map<String, Object> sheetMap, HttpServletResponse response) {
        if (CollUtil.isEmpty(sheetMap)) {
            return;
        }

        try (InputStream in = this.getClass().getClassLoader().getResourceAsStream(excelTemplate.getExportPath()); OutputStream out = response.getOutputStream()) {
            // 获取数据
            String encodeName = URLEncoder.encode(excelTemplate.getName(), StandardCharsets.UTF_8);
            response.setContentType("application/vnd.ms-excel;charset=utf-8");
            response.setHeader(HttpHeaders.CONTENT_DISPOSITION,"attachment;filename=" + encodeName);

            // 强制自动换行
            FillConfig fillConfig = FillConfig
                    .builder()
                    .forceNewRow(true)
                    .build();

            //原模板只有一个sheet，通过poi复制出需要的sheet个数的模板
            XSSFWorkbook workbook = new XSSFWorkbook(in);

            //设置模板的第一个sheet的名称
            workbook.setSheetName(0, "名称-1");

            //设置模板的第一个sheet的名称
            workbook.setSheetName(0, "sheet1");
            sheetMap.forEach((sheetName, sheetData) -> {
                workbook.cloneSheet(0, sheetName);
            });
            //写到流里
            workbook.write(out);
//            byte[] bArray = out.toByteArray();
//            InputStream is = new ByteArrayInputStream(bArray);

            ExcelWriter writer = EasyExcel
                    .write(out)
                    .excelType(ExcelTypeEnum.XLSX)
                    .withTemplate(in)
                    .build();

            AtomicInteger index = new AtomicInteger();
            sheetMap.forEach((sheetName, sheetData) -> {
                //创建sheet
                WriteSheet sheet = EasyExcel.writerSheet(sheetName).build();
                //填充
                writer.fill(sheetData, fillConfig, sheet);
                LOG.warn("sheetName:{} sheetData:{}", sheetName, sheetData);
                index.getAndIncrement();
            });

            writer.finish();
        } catch (Exception e) {
            LOG.error("多个工作表导出到下载中心失败！{}", e.getMessage());
            throw new LoveLowException(HttpStatus.BAD_REQUEST, "Failed to export multiple worksheets");
        }
    }


    @Override
    public void exportExcel(ExcelTemplate excelTemplate, List<ExcelExportDTO> list, HttpServletResponse response) {
        if (CollUtil.isEmpty(list)) {
            return;
        }
        //excel模板
        try (InputStream in = this.getClass().getClassLoader().getResourceAsStream(excelTemplate.getExportPath()); ByteArrayOutputStream bos = new ByteArrayOutputStream(); OutputStream out = response.getOutputStream()) {

            //原模板只有一个sheet，通过poi复制出需要的sheet个数的模板
            XSSFWorkbook workbook = new XSSFWorkbook(in);
            //设置模板的第一个sheet的名称
            String firstName = list.get(0).getName();
            workbook.setSheetName(0, firstName);
            if(list.size() > 1){
                List<String> sheetNameList = list.stream().filter(l -> !firstName.equals(l.getName())).map(ExcelExportDTO::getName).collect(Collectors.toList());
                sheetNameList.forEach(sheetName -> workbook.cloneSheet(0, sheetName));
            }
            //写到流里
            workbook.write(bos);
            byte[] bArray = bos.toByteArray();
            InputStream is = new ByteArrayInputStream(bArray);

            //输出文件路径

            String encodeName = URLEncoder.encode(excelTemplate.getName(), StandardCharsets.UTF_8);
            response.setContentType("application/vnd.ms-excel;charset=utf-8");
            response.setHeader(HttpHeaders.CONTENT_DISPOSITION,"attachment;filename=" + encodeName);

            //输出文件路径
//            String filePath = "D:\\home\\" + System.currentTimeMillis() + ".xlsx";
            String filePath = "D:\\home\\" + System.currentTimeMillis() + ExcelTypeEnum.XLSX.getValue();

            ExcelWriter writer = EasyExcel
                    .write(out)
                    .excelType(ExcelTypeEnum.XLSX)
                    .withTemplate(is)
                    .build();

            for (int i = 0; i < list.size(); i++) {
                ExcelExportDTO dto = list.get(i);
                WriteSheet writeSheet = EasyExcel.writerSheet(dto.getName()).build();
                writer.fill(dto.getList(), writeSheet);
            }

            // 关闭流
            writer.finish();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public void exportExcelsss(String path, String fileName, String companyName, Map<String, Object> sheetMap, HttpServletResponse response) {
        try (InputStream in = this.getClass().getClassLoader().getResourceAsStream(path); OutputStream out = response.getOutputStream()) {
            response.setContentType("application/vnd.ms-excel;charset=utf-8");
            // 设置请求头属性
            String name = URLEncoder.encode(fileName, StandardCharsets.UTF_8);
            response.setHeader("Content-Disposition", "attachment;filename=" + name);

            EasyExcel.write(out)
                    .withTemplate(in)
                    .excelType(ExcelTypeEnum.XLSX)
                    .sheet(companyName)
                    .doFill(sheetMap);

        } catch (Exception e) {
            LOG.error("模版文件下载失败！{}", e.getMessage());
//            throw new LoveLowException(ResultCode.SYSTEM_INNER_ERROR.getMessage());
        }
    }

}
