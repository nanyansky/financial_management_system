package com.nanyan.entity.chart;

import lombok.Data;

/**
 * @author nanyan
 * @version 1.0
 * @description: TODO
 * @date 2023/4/7 12:08
 */

@Data
public class IncomeTypeChart {
    private String typeName;
    private int typeCount;
    private double typeMoney;
}
