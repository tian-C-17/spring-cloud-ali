package com.cloud.demo.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.log.RecordLog;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRule;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRuleManager;
import com.alibaba.csp.sentinel.slots.block.degrade.circuitbreaker.CircuitBreaker;
import com.alibaba.csp.sentinel.slots.block.degrade.circuitbreaker.CircuitBreakerStrategy;
import com.alibaba.csp.sentinel.slots.block.degrade.circuitbreaker.EventObserverRegistry;
import com.alibaba.csp.sentinel.util.TimeUtil;
import com.cloud.demo.entry.CommonResult;
import com.cloud.demo.entry.Dept;
import com.cloud.demo.service.DeptFeignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
public class DeptFeignController {

    @Autowired
    private DeptFeignService deptFeignService;

    @Value("${server.port}")
    private String serverPort;

    /**
     * 使用 @SentinelResource 注解的 fallback 属性时，需要注意以下事项：
     * 返回值类型必须与原函数返回值类型一致；
     * 方法参数列表需要和原函数一致，或者可以额外多一个 Throwable 类型的参数用于接收对应的异常；
     * fallback 函数默认需要和原方法在同一个类中，若希望使用其他类的函数，
     * 则可以指定 fallbackClass 为对应的类的 Class 对象，注意对应的函数必需为 static 函数，否则无法解析。
     * @param id
     * @return
     */
    @GetMapping(value = "/dept/get/{id}")
    @SentinelResource(value = "fallback",fallback = "handlerFallback")
    public CommonResult<Dept> get(@PathVariable("id") int id) {
        // 2
        initDegradeRule();
        monitor();
        System.out.println("--------->>>>主业务逻辑");
        CommonResult<Dept> result = deptFeignService.get(id);
        if (id == 6) {
            System.err.println("--------->>>>主业务逻辑，抛出非法参数异常");
            throw new IllegalArgumentException("IllegalArgumentException，非法参数异常....");
            //如果查到的记录也是 null 也控制正异常
        } else if (result.getData() == null) {
            System.err.println("--------->>>>主业务逻辑，抛出空指针异常");
            throw new NullPointerException("NullPointerException，该ID没有对应记录,空指针异常");
        }
        return result;
    }

    @GetMapping(value = "consumer/feign/dept/list")
    public CommonResult<List<Dept>> list() {
        return deptFeignService.list();
    }

    //处理异常的回退方法（服务降级）
    public CommonResult handlerFallback(@PathVariable int id, Throwable e) {
        System.err.println("--------->>>>服务降级逻辑");
        Dept dept = new Dept(id, "null", "null");
        return new CommonResult(444, "C语言中文网提醒您，服务被降级！异常信息为：" + e.getMessage(), dept);
    }

    /**
     * 自定义事件监听器，监听熔断器状态转换
     */
    public void monitor(){

        EventObserverRegistry.getInstance().addStateChangeObserver("logging",
                (prevState, newState, rule, snapshotValue) -> {
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    if (newState == CircuitBreaker.State.OPEN) {
                        // 变换至 OPEN state 时会携带触发时的值
                        System.err.println(String.format("%s -> OPEN at %s, 发送请求次数=%.2f", prevState.name(),
                                format.format(new Date(TimeUtil.currentTimeMillis())), snapshotValue));
                    } else {
                        System.err.println(String.format("%s -> %s at %s", prevState.name(), newState.name(),
                                format.format(new Date(TimeUtil.currentTimeMillis()))));
                    }
                });
    }


    //---------2:通过代码定义熔断规则

    /**
     * 初始化熔断策略
     */
    private static void initDegradeRule() {
        List<DegradeRule> rules = new ArrayList<>();
        DegradeRule rule = new DegradeRule("fallback");
        //熔断策略为异常比例
        rule.setGrade(CircuitBreakerStrategy.ERROR_RATIO.getType());
        //异常比例阈值
        rule.setCount(0.7);
        //最小请求数
        rule.setMinRequestAmount(100);
        //统计市场，单位毫秒
        rule.setStatIntervalMs(30000);
        //熔断市场，单位秒
        rule.setTimeWindow(10);
        rules.add(rule);
        DegradeRuleManager.loadRules(rules);
    }

}
