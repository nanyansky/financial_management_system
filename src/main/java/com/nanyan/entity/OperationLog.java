package com.nanyan.entity;

import com.nanyan.utils.OperationType;
import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * @author nanyan
 * @version 1.0
 * @description: TODO
 * @date 2023/4/1 11:09
 */
@Entity
@Data
@Table(name = "operation_log")
public class OperationLog {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "username")
    private String userName;

    @Column(name = "operation_time")
    private Timestamp operationTime;

    @Enumerated(EnumType.STRING)
    @Column(name = "operation_type")
    private OperationType operationType;

    @Column(name = "operation_content")
    private String operationContent;

}
