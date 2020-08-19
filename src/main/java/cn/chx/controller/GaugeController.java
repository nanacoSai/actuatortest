package cn.chx.controller;

import io.micrometer.core.instrument.DistributionSummary;
import io.micrometer.prometheus.PrometheusMeterRegistry;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.actuate.metrics.GaugeService;
import org.springframework.util.NumberUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
public class GaugeController {

//    @Autowired
//    private GaugeService gaugeService;

    @Autowired
    private PrometheusMeterRegistry meterRegistry;

    DistributionSummary summary = null;

    AtomicInteger size = new AtomicInteger(0);
    @PostConstruct
    public void initMetrics() {
        summary = meterRegistry.summary("aaa", new String[]{"t1", "t2"});
        meterRegistry.gauge("size", size);
    }

    @RequestMapping("/test-gauge")
    public String testGauge() {
        long start = System.currentTimeMillis();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
        }

        size.set(size.get() + 1);
        summary.record(System.currentTimeMillis()-start);
        return "操作成功";
    }
}
