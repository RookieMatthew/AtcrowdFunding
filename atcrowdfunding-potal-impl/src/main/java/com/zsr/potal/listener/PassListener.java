package com.zsr.potal.listener;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;

/**
 * Demo class
 *
 * @author shourenzhang
 * @date 2019/8/10 17:16
 */
public class PassListener implements ExecutionListener {
    @Override
    public void notify(DelegateExecution execution) throws Exception {
        System.out.println("审核通过监听器");
    }
}
