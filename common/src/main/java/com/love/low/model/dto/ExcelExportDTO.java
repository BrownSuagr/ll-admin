package com.love.low.model.dto;

import lombok.Data;

import java.util.LinkedList;

@Data
public class ExcelExportDTO<T> {
    private Integer index;
    private String name;
    private LinkedList<T> list;
}