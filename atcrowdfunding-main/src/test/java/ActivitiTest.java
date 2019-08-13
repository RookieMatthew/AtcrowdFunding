import com.zsr.listener.activiti.NoListener;
import com.zsr.listener.activiti.YesListener;
import org.activiti.engine.*;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricProcessInstanceQuery;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Demo class
 *
 * @author shourenzhang
 * @date 2019/8/6 10:22
 */
public class ActivitiTest {
    private ApplicationContext ac = new ClassPathXmlApplicationContext("spring/spring-*.xml");
    private ProcessEngine processEngine = (ProcessEngine) ac.getBean("processEngine");

    //创建流程引擎，生成23张表
    @Test
    public void test01(){
        System.out.println(processEngine);
    }

    //部署流程定义，act_ge_bytearray，act_re_deployment，act_re_procdef
    @Test
    public void test02(){
        RepositoryService repositoryService = processEngine.getRepositoryService();
//        Deployment deploy = repositoryService.createDeployment().addClasspathResource("MyFirstProcess.bpmn").deploy();
//        Deployment deploy = repositoryService.createDeployment().addClasspathResource("MySecondProcess.bpmn").deploy();
        Deployment deploy = repositoryService.createDeployment().addClasspathResource("MyThirdProcess.bpmn").deploy();
        System.out.println(deploy);
    }

    //查询部署流程定义
    @Test
    public void test03(){
        RepositoryService repositoryService = processEngine.getRepositoryService();
        ProcessDefinitionQuery processDefinitionQuery = repositoryService.createProcessDefinitionQuery();
        List<ProcessDefinition> list = processDefinitionQuery.list();
        for (ProcessDefinition processDefinition : list) {
            System.out.println(processDefinition.getId());
            System.out.println(processDefinition.getKey());
            System.out.println(processDefinition.getName());
            System.out.println(processDefinition.getVersion());
        }
    }

    //创建流程实例，act_hi_actinst，act_hi_procinst，act_hi_taskinst，act_ru_execution，act_ru_task
    @Test
    public void test04(){
        ProcessDefinition processDefinition = processEngine.getRepositoryService()
                            .createProcessDefinitionQuery()
                            .latestVersion()
                            .singleResult();
        RuntimeService runtimeService = processEngine.getRuntimeService();
        ProcessInstance processInstance = runtimeService.startProcessInstanceById(processDefinition.getId());
        System.out.println(processInstance);
    }

    //启动流程实例任务
    @Test
    public void test05(){
        TaskService taskService = processEngine.getTaskService();
        TaskQuery taskQuery = taskService.createTaskQuery();
        //得到某个委托人的任务
        List<Task> list1 = taskQuery.taskAssignee("组长").list();
        List<Task> list2 = taskQuery.taskAssignee("经理").list();
        for (Task task : list1) {
            System.out.println("组长的任务："+task);
//            taskService.complete(task.getId());
        }
        for (Task task : list2) {
            System.out.println("经理的任务："+task);
            taskService.complete(task.getId());
        }
    }

    //历史流程实例查询
    @Test
    public void test06(){
        HistoryService historyService = processEngine.getHistoryService();
        HistoricProcessInstanceQuery historicProcessInstanceQuery = historyService.createHistoricProcessInstanceQuery();
        HistoricProcessInstance historicProcessInstance = historicProcessInstanceQuery.processInstanceId("101").finished().singleResult();
        System.out.println(historicProcessInstance);
    }

    //任务领取
    @Test
    public void test07(){
        TaskService taskService = processEngine.getTaskService();
        List<Task> list = taskService.createTaskQuery().taskCandidateGroup("TeamLeader").list();
        long count = taskService.createTaskQuery().taskAssignee("组长01").count();
        System.out.println("组长01分配任务前的任务数量："+count);
        for (Task task : list) {
            System.out.println(task.getId());
            System.out.println(task.getName());
            //组长01领取TeamLeader组的任务
            taskService.claim(task.getId(),"组长01");
        }
        long count2 = taskService.createTaskQuery().taskAssignee("组长01").count();
        System.out.println("组长01分配任务后的任务数量："+count2);
    }

    //排他网关，天数小于3天
    @Test
    public void test08(){
        //1.部署流程定义
        RepositoryService repositoryService = processEngine.getRepositoryService();
        repositoryService.createDeployment().addClasspathResource("Process04.bpmn").deploy();
        //2.创建流程实例
        ProcessDefinition processDefinition = processEngine.getRepositoryService().createProcessDefinitionQuery().latestVersion().singleResult();
        RuntimeService runtimeService = processEngine.getRuntimeService();
        Map<String,Object> variable = new HashMap<>();
        variable.put("days",2);
        runtimeService.startProcessInstanceById(processDefinition.getId(),variable);
        //3.查询任务，完成当前任务，进入下个任务（开始-->组长审批-->排他网关(天数小于3)-->结束）
        TaskService taskService = processEngine.getTaskService();
        List<Task> list = taskService.createTaskQuery().list();
        for (Task task : list) {
            System.out.println(task.getId());
            System.out.println(task.getName());
            taskService.complete(task.getId());
        }
    }

    //排他网关，天数大于3天
    @Test
    public void test081(){
        //2.创建流程实例
        ProcessDefinition processDefinition = processEngine.getRepositoryService().createProcessDefinitionQuery().latestVersion().singleResult();
        RuntimeService runtimeService = processEngine.getRuntimeService();
        Map<String,Object> variable = new HashMap<>();
        variable.put("days",5);
        runtimeService.startProcessInstanceById(processDefinition.getId(),variable);
        //3.查询任务，完成当前任务，进入下个任务（开始-->组长审批-->排他网关(天数大于3)-->经理审批-->结束）
        TaskService taskService = processEngine.getTaskService();
        List<Task> list = taskService.createTaskQuery().list();
        //组长审批完成
        for (Task task : list) {
            System.out.println(task.getId());
            System.out.println(task.getName());
            taskService.complete(task.getId());
        }
        TaskService taskService1 = processEngine.getTaskService();
        List<Task> list1 = taskService.createTaskQuery().list();
        //经理审批完成
        for (Task task : list1) {
            System.out.println(task.getId());
            System.out.println(task.getName());
            taskService1.complete(task.getId());
        }
    }

    //并行网关
    @Test
    public void test09(){
        //1.部署流程定义
        processEngine.getRepositoryService().createDeployment().addClasspathResource("Process05.bpmn").deploy();
        //2.创建流程实例
        ProcessDefinition processDefinition = processEngine.getRepositoryService().createProcessDefinitionQuery().latestVersion().singleResult();
        RuntimeService runtimeService = processEngine.getRuntimeService();
        runtimeService.startProcessInstanceById(processDefinition.getId());
        //3.查询任务
        TaskService taskService = processEngine.getTaskService();
        List<Task> list = taskService.createTaskQuery().list();
        for (Task task : list) {
            System.out.println(task.getId());
            System.out.println(task.getName());
        }
    }
    //并行网关---任务1
    @Test
    public void test091(){
        //3.查询项目经理（项目经理01）任务，并完成,继续等待其他并行任务完成
        TaskService taskService = processEngine.getTaskService();
        List<Task> list = taskService.createTaskQuery().taskAssignee("项目经理01").list();
        for (Task task : list) {
            System.out.println(task.getId());
            System.out.println(task.getName());
            taskService.complete(task.getId());
        }
    }
    //并行网关---任务2
    @Test
    public void test092(){
        //3.查询财务经理（财务经理01）任务，并完成，全部任务完成-->并行网关-->结束
        TaskService taskService = processEngine.getTaskService();
        List<Task> list = taskService.createTaskQuery().taskAssignee("财务经理01").list();
        for (Task task : list) {
            System.out.println(task.getId());
            System.out.println(task.getName());
            taskService.complete(task.getId());
        }
    }

    //包含网关（条件都不满足，不走任何节点，报错）
    //条件为天数大于等于3、资金大于等于5000
    @Test
    public void test101(){
        //部署流程定义
        processEngine.getRepositoryService().createDeployment().addClasspathResource("Process06.bpmn").deploy();
        //创建流程实例
        HashMap<String, Object> param = new HashMap<>();
        param.put("days",2);
        param.put("cost",4000);
        ProcessDefinition processDefinition = processEngine.getRepositoryService().createProcessDefinitionQuery().latestVersion().singleResult();
        processEngine.getRuntimeService().startProcessInstanceById(processDefinition.getId(),param);
        //查询任务
        TaskService taskService = processEngine.getTaskService();
        List<Task> list = taskService.createTaskQuery().list();
        for (Task task : list) {
            System.out.println(task.getId());
            System.out.println(task.getName());
        }
    }
    //包含网关（一个条件满足，另一个条件不满足，只开启满足条件的任务节点，完成后流程结束）
    @Test
    public void test102(){
//        //创建流程实例
//        HashMap<String, Object> param = new HashMap<>();
//        param.put("days",2);
//        param.put("cost",6000);
//        ProcessDefinition processDefinition = processEngine.getRepositoryService().createProcessDefinitionQuery().latestVersion().singleResult();
//        processEngine.getRuntimeService().startProcessInstanceById(processDefinition.getId(),param);
        //查询任务，并完成
        TaskService taskService = processEngine.getTaskService();
        List<Task> list = taskService.createTaskQuery().list();
        for (Task task : list) {
            System.out.println(task.getId());
            System.out.println(task.getName());
            taskService.complete(task.getId());
        }
        //查询任务，无任务，流程结束
        TaskService taskService1 = processEngine.getTaskService();
        List<Task> list1 = taskService1.createTaskQuery().list();
        for (Task task : list1) {
            System.out.println(task.getId());
            System.out.println(task.getName());
        }
    }

    //包含网关（两个条件都满足，两个任务节点都开启，两个任务都完成后流程结束）
    @Test
    public void test103(){
        //创建流程实例
        HashMap<String, Object> param = new HashMap<>();
        param.put("days",4);
        param.put("cost",6000);
        ProcessDefinition processDefinition = processEngine.getRepositoryService().createProcessDefinitionQuery().latestVersion().singleResult();
        processEngine.getRuntimeService().startProcessInstanceById(processDefinition.getId(),param);
        //查询项目经理任务，并完成
        TaskService taskService = processEngine.getTaskService();
        List<Task> list = taskService.createTaskQuery().taskAssignee("项目经理").list();
        for (Task task : list) {
            System.out.println(task.getId());
            System.out.println(task.getName());
            taskService.complete(task.getId());
        }
        //查询财务经理任务，并完成
        TaskService taskService1 = processEngine.getTaskService();
        List<Task> list1 = taskService1.createTaskQuery().taskAssignee("财务经理").list();
        for (Task task : list1) {
            System.out.println(task.getId());
            System.out.println(task.getName());
            taskService.complete(task.getId());
        }

        //所有任务都完成，流程结束
        TaskService taskService2 = processEngine.getTaskService();
        List<Task> list2 = taskService2.createTaskQuery().list();
        for (Task task : list2) {
            System.out.println(task.getId());
            System.out.println(task.getName());
        }
    }

    //发送邮件
    @Test
    public void test11(){
        //部署流程定义
        processEngine.getRepositoryService().createDeployment().addClasspathResource("email.bpmn").deploy();
        //启动流程实例
        HashMap<String, Object> ss = new HashMap<>();
        ss.put("email","test@zsr.com");
        ss.put("authcode","1235");
        ProcessDefinition processDefinition = processEngine.getRepositoryService().createProcessDefinitionQuery().latestVersion().singleResult();
        processEngine.getRuntimeService().startProcessInstanceById(processDefinition.getId(),ss);
    }

    //任务流程监听器
    @Test
    public void test12(){
        //部署流程定义
        processEngine.getRepositoryService().createDeployment().addClasspathResource("activitiListener.bpmn").deploy();
        //启动流程实例
        ProcessDefinition processDefinition = processEngine.getRepositoryService().createProcessDefinitionQuery().latestVersion().singleResult();
        Map<String,Object> param = new HashMap<>();
        param.put("YesListener",new YesListener());
        param.put("NoListener",new NoListener());
        processEngine.getRuntimeService().startProcessInstanceById(processDefinition.getId(),param);
    }
    //查询当前任务,并完成任务（审核通过/不通过）调用相应的监听器，流程结束
    @Test
    public void runTask(){
        TaskService taskService = processEngine.getTaskService();
        List<Task> list = taskService.createTaskQuery().list();
        HashMap<String, Object> param = new HashMap<>();
        param.put("flag",false);
        for (Task task : list) {
            System.out.println(task.getId());
            System.out.println(task.getName());
            taskService.complete(task.getId(),param);
        }
    }
}
