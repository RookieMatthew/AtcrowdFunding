package com.zsr.potal.listener;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;

/**
 * Demo class
 *
 * @author shourenzhang
 * @date 2019/8/10 17:17
 */
public class RefuseListener implements ExecutionListener {
    @Override
    public void notify(DelegateExecution execution) throws Exception {
        System.out.println("审核拒绝监听器");
    }
}
