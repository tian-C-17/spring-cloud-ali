package com.cloud.demo.controller;

import com.alibaba.csp.sentinel.Entry;
import com.alibaba.csp.sentinel.SphO;
import com.alibaba.csp.sentinel.SphU;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
public class SentinelFlowLimitController {

    @Value("${server.port}")
    private String serverPort;

    @GetMapping("/testA")
    public String testA(){
        return testAbySphU();
    }

    @GetMapping("/testB")
    public String testB(){
        return testBBySohO();
    }

    /**
     * Sentinel 提供了一个名为 SphU 的类，它包含的 try-catch 风格的 API ，可以帮助我们手动定义资源。
     * @return
     */
    public String testAbySphU(){
        Entry entry = null;
        try {
            // 业务逻辑开始
            entry = SphU.entry("testAbySphU");
            // 业务逻辑结束
            return "-------testA"+serverPort;
        }catch (BlockException e){
            // 流控逻辑处理 -- 开始
            return "testA--服务被限流";
            ////流控逻辑处理 - 结束
        }finally {
            if(entry != null){
                entry.exit();
            }
        }
    }

    /**
     * Sentinel 还提供了一个名为 SphO 的类，它包含了 if-else 风格的 API，
     * 能帮助我们手动定义资源。通过这种方式定义的资源，发生了限流之后会返回 false，
     * 此时我们可以根据返回值，进行限流之后的逻辑处理。
     * @return
     */
    public String testBBySohO(){
        if(SphO.entry("testBBySohO()")){
            // 务必保证finally会被执行
            try{
                return "_______B"+serverPort;
            }finally {
                SphO.exit();
            }
        }else {
            // 资源访问阻止，被限流或被降级
            //流控逻辑处理 - 开始
            return "，testB 服务被限流";
            //流控逻辑处理 - 结束
        }
    }

    @GetMapping("/testC")
    @SentinelResource(value = "testCbyAnnotation")
    public String testC(){
        return "-----C"+serverPort;
    }

    @GetMapping("/testD")
    //通过注解定义资源      2
    @SentinelResource(value = "testD-resource",blockHandler = "blockHandlerTestD")
    public String testD(){
        initFlowRules();  // 3 调用初始化流控规则的方法
        return "-------------DDD";
    }

    /**
     * 使用 @SentinelResource 注解的 blockHandler 属性时，需要注意以下事项：
     *
     * blockHandler 函数访问范围需要是 public；
     * 返回类型需要与原方法相匹配；
     * 参数类型需要和原方法相匹配并且最后加一个额外的参数，类型为 BlockException；
     * blockHandler 函数默认需要和原方法在同一个类中，若希望使用其他类的函数，则可以指定 blockHandler 为对应的类的 Class 对象，注意对应的函数必需为 static 函数，否则无法解析。
     * 请务必添加 blockHandler 属性来指定自定义的限流处理方法，若不指定，则会跳转到错误页（用户体验不好）
     */

    public String blockHandlerTestD(BlockException blockException){
        return "您已被限流，请稍后重试！";
    }

    /**
     * 3:通过代码定义流量控制规则
     */
    private static void initFlowRules() {
        List<FlowRule> rules = new ArrayList<>();
        //定义一个限流规则对象
        FlowRule rule = new FlowRule();
        //资源名称
        rule.setResource("testD-resource");
        //限流阈值的类型
        rule.setGrade(RuleConstant.FLOW_GRADE_QPS);
        // 设置 QPS 的阈值为 2
        rule.setCount(2);
        rules.add(rule);
        //定义限流规则
        FlowRuleManager.loadRules(rules);
    }

}
