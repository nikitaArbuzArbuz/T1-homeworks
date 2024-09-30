package ru.t1.java.demo.aop;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import ru.t1.java.demo.kafka.producers.KafkaDefaultProducer;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicLong;

@Async
@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class MetricAspect {
    private static final AtomicLong START_TIME = new AtomicLong();
    private final KafkaDefaultProducer kafkaDefaultProducer;

    @Value("${t1.aspect.time-to-execute}")
    private int maxTimeToExecute;
    @Value("${t1.kafka.topic.t1_demo_metric_trace}")
    private String metricTopic;

    @Pointcut("within(ru.t1.java.demo.aop.Metric)")
    public void loggingMethods() {

    }

    @Before("@annotation(Metric)")
    public void logExecTime(JoinPoint joinPoint) {
        log.info("Старт метода: {}", joinPoint.getSignature().toShortString());
        START_TIME.addAndGet(System.currentTimeMillis());
    }

    @After("@annotation(Metric)")
    public void calculateTime(JoinPoint joinPoint) {
        long afterTime = System.currentTimeMillis();
        long timeMethodExecute = afterTime - START_TIME.get();
        log.info("Время исполнения: {} ms", timeMethodExecute);
        START_TIME.set(0L);
        if (timeMethodExecute > maxTimeToExecute) {
            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            String methodName = signature.getName();
            Object[] args = joinPoint.getArgs();
            log.info("Sending throw trace to Kafka: method = {}, params = {}, executeTime = {} ms",
                    methodName, Arrays.toString(args), timeMethodExecute);

            kafkaDefaultProducer.throwTraceToTopic(timeMethodExecute, metricTopic, methodName, args);
        }
    }
}
