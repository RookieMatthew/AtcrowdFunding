package com.zsr.listener.activiti;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;

/**
 * Demo class
 *
 * @author shourenzhang
 * @date 2019/8/9 14:15
 */
public class NoListener implements ExecutionListener {
    @Override
    public void notify(DelegateExecution execution) throws Exception {
        System.out.println("审批不通过.........");
    }
}
