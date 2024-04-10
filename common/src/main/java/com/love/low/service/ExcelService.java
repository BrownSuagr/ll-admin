package com.love.low.service;


import com.love.low.enmus.ExcelTemplate;
import com.love.low.model.dto.ExcelExportDTO;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * @name: ExcelService
 * @description: Excel操作业务接口层
 * @author: BrownSugar
 * @date: 2024-04-02 12:05:33
 * @version: 1.0.0
 **/
public interface ExcelService {

    /**
     * @description: 导出Excel数据
     * @param: [excelTemplate, sheetMap, response]
     * @author: BrownSugar
     * @date: 2024-04-02 03:04:14
     **/
    void exportExcel(ExcelTemplate excelTemplate, Map<String, Object> sheetMap, HttpServletResponse response);


    void exportExcel(ExcelTemplate excelTemplate, String path, List<ExcelExportDTO> list);
}
