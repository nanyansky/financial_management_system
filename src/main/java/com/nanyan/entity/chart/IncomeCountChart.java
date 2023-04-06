package com.nanyan.entity.chart;

import lombok.Data;
import org.springframework.context.annotation.Bean;

import javax.persistence.Entity;
import java.sql.Timestamp;

/**
 * @author nanyan
 * @version 1.0
 * @description: TODO
 * @date 2023/4/6 21:07
 */

@Data
public class IncomeCountChart {
    private int count;
    private double money;
    private String date;
}
