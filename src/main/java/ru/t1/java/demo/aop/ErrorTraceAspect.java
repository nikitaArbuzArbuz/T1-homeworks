package ru.t1.java.demo.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import ru.t1.java.demo.kafka.producers.KafkaErrorTraceProducer;

import java.util.Arrays;

@Aspect
@Slf4j
@Component
public class ErrorTraceAspect {

    private final KafkaErrorTraceProducer kafkaErrorTraceProducer;

    public ErrorTraceAspect(KafkaErrorTraceProducer kafkaErrorTraceProducer) {
        this.kafkaErrorTraceProducer = kafkaErrorTraceProducer;
    }

    @AfterThrowing(pointcut = "execution(* ru.t1.java.demo..*(..))", throwing = "exception")
    public void logAndSendErrorTrace(JoinPoint joinPoint, Exception exception) {

        String methodName = joinPoint.getSignature().getName();
        Object[] params = joinPoint.getArgs();
        String stackTrace = Arrays.toString(exception.getStackTrace());
        log.error("Sending error trace to Kafka: method = {}, params = {}, stackTrace = {}",
                    methodName, Arrays.toString(params), stackTrace);

        kafkaErrorTraceProducer.sendErrorTrace(methodName, params, stackTrace);
    }
}
