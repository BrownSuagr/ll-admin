package com.love.low;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.Data;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.apache.poi.xwpf.usermodel.*;

import java.io.*;
import java.math.BigInteger;
import java.util.*;

@SpringBootTest
public class WordTest {

    private static final Logger LOG = LoggerFactory.getLogger(WordTest.class);

    /**
     * 判断文本中时候包含$
     *
     * @param text 文本
     * @return 包含返回true, 不包含返回false
     */
    public static boolean checkText(String text) {
        boolean check = false;
        if (text.indexOf("$") != -1) {
            check = true;
        }
        return check;
    }

    /**
     * 匹配传入信息集合与模板
     *
     * @param value   模板需要替换的区域
     * @param textMap 传入信息集合
     * @return 模板需要替换区域信息集合对应值
     */
    public static String changeValue(String value, Map<String, String> textMap) {
        Set<Map.Entry<String, String>> textSets = textMap.entrySet();
        LOG.info("value:{} textSets:{}", value, textSets);
        for (Map.Entry<String, String> textSet : textSets) {
            //匹配模板与替换值 格式${key}
            String key = "${" + textSet.getKey() + "}";
            LOG.info("key:{}", key);
            if (value.indexOf(key) != -1) {
                value = textSet.getValue();
            }
        }
        //模板未匹配到区域替换为空
        if (checkText(value)) {
            value = "";
        }
        return value;
    }


    /**
     * 替换文档中段落文本
     *
     * @param document docx解析对象
     * @param textMap  需要替换的信息集合
     */
    public static void changeParagraphText(XWPFDocument document, Map<String, String> textMap) {
        //获取段落集合
        List<XWPFParagraph> paragraphs = document.getParagraphs();
        for (XWPFParagraph paragraph : paragraphs) {
            //判断此段落时候需要进行替换
            String text = paragraph.getText();
            LOG.warn("text:{}", text);
            if (checkText(text)) {
                List<XWPFRun> runs = paragraph.getRuns();
                LOG.warn("runs:{}", runs);
                for (XWPFRun run : runs) {
                    //替换模板原来位置
                    run.setText(changeValue(run.toString(), textMap), 0);
                }
            }
        }
    }

    @Data
    public static class NetInspectRepDTO{

        private String company;
        private String address;
        private String recorder;
        private String carrier;
        private String way;
        private String subject;
        private List<NetInspectRepContentDTO> content;
    }


    @Data
    public static class NetInspectRepContentDTO{
        /**
         * 序号
         **/
        private Integer no;

        /**
         * 被查询主体
         **/
        private String subject;

        /**
         * 查询事项
         **/
        private String event;
        private List<NetInspectRepContentDetailDTO> content;

    }

    @Data
    public static class NetInspectRepContentDetailDTO{

        /**
         * 查询网站
         **/
        private String website;

        /**
         * 网址
         **/
        private String url;

        /**
         * 查询时间
         **/
        private String date = DateUtil.format(DateUtil.date(), DatePattern.NORM_DATE_PATTERN);
    }


    public static void main(String[] args) {

        String templatePath = "./documents/template.docx";
        InputStream is = null;
        // 创建一个空的Word文档
        XWPFDocument document = null;
        try {
            is = new FileInputStream(templatePath);
            document = new XWPFDocument(is);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        NetInspectRepDTO dto = new NetInspectRepDTO();
        dto.setCompany("春雪食品集团股份有限公司及子公司");
        dto.setAddress("山东省莱阳市富山路382号");
        dto.setRecorder("江祎航");
        dto.setCarrier("个人计算机");
        dto.setWay("互联网");
        dto.setSubject("春雪食品集团股份有限公司、春雪食品集团股份有限公司第一加工厂、春雪食品集团股份有限公司第二加工厂、春雪食品集团股份有限公司调理食品厂、烟台太元食品有限公司、莱阳春雪养殖有限公司、莱阳春雪养殖有限公司固体废弃物处理中心、烟台春雪商贸有限公司、莱阳市春雪生物科技有限公司、青岛春雪贸易有限公司");


        List<NetInspectRepContentDTO> contentList = Lists.newArrayList();
        NetInspectRepContentDTO c1 = new NetInspectRepContentDTO();
        c1.setNo(0);
        c1.setSubject("青岛春雪贸易有限公司");
        c1.setEvent("舆论信息查询");

        NetInspectRepContentDetailDTO d1 = new NetInspectRepContentDetailDTO();
        d1.setWebsite("百度");
        d1.setUrl("https://www.baidu.com/");

        NetInspectRepContentDetailDTO d2 = new NetInspectRepContentDetailDTO();
        d2.setWebsite("搜狗");
        d2.setUrl("https://www.sogou.com/");

        c1.setContent(List.of(d1, d2));
        contentList.add(c1);


        NetInspectRepContentDTO c2 = new NetInspectRepContentDTO();
        c2.setNo(2);
        c2.setSubject("青岛春雪贸易有限公司");
        c2.setEvent("信用信息及行政处罚信息查询");

        NetInspectRepContentDetailDTO d21 = new NetInspectRepContentDetailDTO();
        d21.setWebsite("信用中国");
        d21.setUrl("https://www.creditchina.gov.cn/home/index.html");
        c2.setContent(List.of(d21));

        contentList.add(c2);

        NetInspectRepContentDTO c3 = new NetInspectRepContentDTO();
        c3.setNo(3);
        c3.setSubject("青岛春雪贸易有限公司");
        c3.setEvent("工商基本信息、行政处罚信息、经营异常信息查询、严重违法失信查询");

        NetInspectRepContentDetailDTO c31 = new NetInspectRepContentDetailDTO();
        c31.setWebsite("国家企业信用信息公示系统");
        c31.setUrl("http://www.gsxt.gov.cn/corp-query-homepage.html");

        NetInspectRepContentDetailDTO c32 = new NetInspectRepContentDetailDTO();
        c32.setWebsite("天眼查");
        c32.setUrl("https://www.tianyancha.com/");

        NetInspectRepContentDetailDTO c33 = new NetInspectRepContentDetailDTO();
        c33.setWebsite("企查查");
        c33.setUrl("https://www.qcc.com/");


        c3.setContent(List.of(c31, c32, c33));
        contentList.add(c3);

        dto.setContent(contentList);

        LOG.warn("dto:{}", JSONUtil.toJsonStr(dto));



        Map<String, String> textMap = Maps.newHashMap();
        // textMap.put("company", "春雪食品集团股份有限公司及子公司");
        // textMap.put("address", "山东省莱阳市富山路382号");
        // textMap.put("recorder", "江祎航");
        // textMap.put("carrier", "个人计算机");
        // textMap.put("way", "互联网");
        // textMap.put("subject", "春雪食品集团股份有限公司、春雪食品集团股份有限公司第一加工厂、春雪食品集团股份有限公司第二加工厂、春雪食品集团股份有限公司调理食品厂、烟台太元食品有限公司、莱阳春雪养殖有限公司、莱阳春雪养殖有限公司固体废弃物处理中心、烟台春雪商贸有限公司、莱阳市春雪生物科技有限公司、青岛春雪贸易有限公司");

        textMap.put("company", dto.getCompany());
        textMap.put("address", dto.getAddress());
        textMap.put("recorder", dto.getRecorder());
        textMap.put("carrier", dto.getCarrier());
        textMap.put("way", dto.getWay());
        textMap.put("subject", dto.getSubject());

        changeParagraphText(document, textMap);


        // 创建一个标题段落
        //XWPFParagraph titleParagraph = document.createParagraph();

        // 设置标题的样式
        // titleParagraph.setAlignment(ParagraphAlignment.CENTER);
        // titleParagraph.setVerticalAlignment(TextAlignment.CENTER);

        // 创建一个标题Run并设置其文本和样式
        // XWPFRun titleRun = titleParagraph.createRun();
        // titleRun.setText("Java POI Word示例");
        // titleRun.setFontSize(18);
        // titleRun.setBold(true);

        // 创建一个段落
        //XWPFParagraph paragraph = document.createParagraph();

        // 创建一个Run并设置其文本和样式
        // XWPFRun run = paragraph.createRun();
        // run.setText("这是一个示例段落。");
        // run.setFontSize(12);


        // 创建一个表格
        XWPFTable table = document.createTable(1, 6);

        // 设置表格样式
        CTTblPr tblPr = table.getCTTbl().getTblPr();
        tblPr.addNewTblBorders().addNewBottom().setVal(STBorder.NONE);
        tblPr.addNewTblBorders().addNewTop().setVal(STBorder.NONE);
        tblPr.addNewTblBorders().addNewLeft().setVal(STBorder.NONE);
        tblPr.addNewTblBorders().addNewRight().setVal(STBorder.SINGLE);

        // 设置表格列宽
        table.setWidth("100%");
        // 设置表格对齐方式
        table.setTableAlignment(TableRowAlign.CENTER);
        // 设置表格边距
        table.setCellMargins(20, 20, 20, 20);


        // 添加表头内容
        XWPFTableRow hr = table.getRow(0);
        addCell(0, hr,"序号");
        addCell(1, hr,"被查询主体");
        addCell(2, hr,"查询事项");
        addCell(3, hr,"查询网站");
        addCell(4, hr,"网址");
        addCell(5, hr,"查询时间");

        dto.getContent().forEach(c ->{

            List<NetInspectRepContentDetailDTO> detail = c.getContent();

            detail.forEach(d ->{
                XWPFTableRow temp = table.createRow();

                addCell(0, temp, c.getNo().toString());
                addCell(1, temp, c.getSubject());
                addCell(2, temp, c.getEvent());

                addCell(3, temp, d.getWebsite());
                addCell(4, temp, d.getUrl());
                addCell(5, temp, d.getDate());

            });



//            for (int i = 1; i < detail.size(); i++) {
//
//            }
//
//
//            XWPFTableRow row1 = table.getRow(1);
//            XWPFTableRow row2 = table.getRow(2);
//
//            row1.getCell(0).getCTTc().addNewTcPr().addNewVMerge().setVal(STMerge.RESTART);
//            row2.getCell(0).getCTTc().addNewTcPr().addNewVMerge().setVal(STMerge.CONTINUE);








        });


        // 添加数据行
//        XWPFTableRow r1 = table.createRow();
//        addCell(0, r1,"1");
//        addCell(1, r1,"青岛春雪贸易有限公司");
//        addCell(2, r1,"舆论信息查询");
//        addCell(3, r1,"百度");
//        addCell(4, r1,"https://www.baidu.com/");
//        addCell(5, r1,DateUtil.format(DateUtil.date(), DatePattern.NORM_DATE_PATTERN));
//
//
//        XWPFTableRow r2 = table.createRow();
//        addCell(0, r2,"2");
//        addCell(1, r2, "青岛春雪贸易有限公司");
//        addCell(2, r2, "舆论信息查询");
//        addCell(3, r2, "搜狗");
//        addCell(4, r2, "https://www.sogou.com/");
//        addCell(5, r2, DateUtil.format(DateUtil.date(), DatePattern.NORM_DATE_PATTERN));


//        row.getCell(1).setText("青岛春雪贸易有限公司");
//        row.getCell(2).setText("舆论信息查询");
//        row.getCell(3).setText("搜狗");
//        row.getCell(4).setText("https://www.sogou.com/");
//        row.getCell(5).setText(DateUtil.format(DateUtil.date(), DatePattern.NORM_DATE_PATTERN));

        // 合并第一行的三个单元格
        XWPFTableRow row1 = table.getRow(1);
        XWPFTableRow row2 = table.getRow(2);

        row1.getCell(0).getCTTc().addNewTcPr().addNewVMerge().setVal(STMerge.RESTART);
        row2.getCell(0).getCTTc().addNewTcPr().addNewVMerge().setVal(STMerge.CONTINUE);

        row1.getCell(1).getCTTc().addNewTcPr().addNewVMerge().setVal(STMerge.RESTART);
        row2.getCell(1).getCTTc().addNewTcPr().addNewVMerge().setVal(STMerge.CONTINUE);

        row1.getCell(2).getCTTc().addNewTcPr().addNewVMerge().setVal(STMerge.RESTART);
        row2.getCell(2).getCTTc().addNewTcPr().addNewVMerge().setVal(STMerge.CONTINUE);



        // 设置表格边框
        //table.setInsideHBorder(XWPFTable.XWPFBorderType.SINGLE, 4, 4, "FF0000");


        // 设置表格的样式
//        CTTblPr tablePr = table.getCTTbl().addNewTblPr();
//        CTTblWidth tblWidth = tablePr.addNewTblW();
//        tblWidth.setW(BigInteger.valueOf(5000));
//        tblWidth.setType(STTblWidth.DXA);

        // 获取表格的行和单元格
//        List<XWPFTableRow> rows = table.getRows();
//        for (int i = 1; i < rows.size(); i++) {
//            XWPFTableRow row = rows.get(i);
//            List<XWPFTableCell> cells = row.getTableCells();
//            for (int j = 0; j < cells.size(); j++) {
//                XWPFTableCell cell = cells.get(j);
//
//                // 设置单元格的文本和样式
//                XWPFParagraph cellParagraph = cell.getParagraphs().get(0);
//                XWPFRun cellRun = cellParagraph.createRun();
//                cellRun.setText("单元格 " + (i+1) + "-" + (j+1));
//                cellRun.setFontSize(10);
//            }
//        }



        // 创建一个3行3列的表格
//        XWPFTable table = document.createTable(3, 3);

        // 设置表格宽度
//        table.setWidth("100%");
//        // 设置表格对齐方式
//        table.setTableAlignment(TableRowAlign.CENTER);
//        // 设置表格边框
//        table.setInsideHBorder(XWPFTable.XWPFBorderType.SINGLE, 4, 4, "FF0000");
//
//
//        // 获取表格的某一单元格
//        XWPFTableCell cell = table.getRow(0).getCell(0);
//        // 设置单元格内容
//        cell.setText("姓名");
//
//        // 获取表格的第二行第二列单元格
//        XWPFTableCell cell2 = table.getRow(1).getCell(1);
//        // 设置单元格内容
//        cell2.setText("年龄");
//
//        // 获取表格的最后一行最后一列单元格
//        XWPFTableCell cell3 = table.getRow(2).getCell(2);
//        // 设置单元格内容
//        cell3.setText("性别");


        // 保存Word文档
        FileOutputStream out = null;
        try {
            out = new FileOutputStream("./documents/result.docx");
            document.write(out);
            out.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }



    }



    private static void addCell(int num, XWPFTableRow row, String text) {
        XWPFTableCell cell = row.getCell(num);
        // 垂直居中对齐
        cell.setVerticalAlignment(XWPFTableCell.XWPFVertAlign.CENTER);
        // 水平居中对齐
        cell.getParagraphArray(0).setAlignment(ParagraphAlignment.CENTER);
        cell.setText(text);
    }


}
