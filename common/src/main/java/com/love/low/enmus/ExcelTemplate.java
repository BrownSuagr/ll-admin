package com.love.low.enmus;

import lombok.Getter;

public enum ExcelTemplate {

    /**
     * 网络核查报告Excel模板
     **/
    ExtendedWarranty("网络核查报告",
            "",
            "",
            "",
            "export/net_inspect_rep_result.xlsx",
            "NetInspectRep");


    /**
     * 模板名
     */
    @Getter
    private String name;

    /**
     * 下载的excel模板文件的名称
     */
    @Getter
    private String templateName;

    /**
     * excel导入模板文件的路径
     */
    @Getter
    private String templatePath;

    /**
     * 导入的校验结果  模板路径
     */
    @Getter
    private String validateResultPath;

    /**
     * 数据导出 模板路径
     */
    @Getter
    private String exportPath;

    /**
     * 保存检验结果的redisKey
     */
    @Getter
    private String redisKey;


    ExcelTemplate(String name, String templateName, String templatePath, String validateResultPath, String exportPath, String redisKey) {
        this.name = name;
        this.templateName = templateName;
        this.templatePath = templatePath;
        this.validateResultPath = validateResultPath;
        this.exportPath = exportPath;
        this.redisKey = redisKey;
    }

}
