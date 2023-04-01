package com.nanyan.service.Impl;

import com.nanyan.dao.OperationLogDao;
import com.nanyan.entity.OperationLog;
import com.nanyan.service.OperationLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author nanyan
 * @version 1.0
 * @description: TODO
 * @date 2023/4/1 11:45
 */
@Service
public class OperationLogServiceImpl implements OperationLogService {

    @Resource
    OperationLogDao operationLogDao;

    @Override
    public void addOperationLog(OperationLog operationLog) {
        operationLogDao.addOperationLog(operationLog);
    }
}
