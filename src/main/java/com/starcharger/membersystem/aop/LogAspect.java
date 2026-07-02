package com.starcharger.membersystem.aop;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Arrays;

/**
 * API 請求與回應 Log
 */
@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class LogAspect {

    private final ObjectMapper objectMapper;

    /**
     * 印出進出 controller 上下行電文
     */
    @Around("execution(public * com.starcharger.membersystem.controller..*(..))")
    public Object apiLog(ProceedingJoinPoint joinPoint) throws Throwable {
        HttpServletRequest request = getHttpServletRequest();

        Object[] args = joinPoint.getArgs();
        log.info("========== API START ==========");
        log.info("URI        : {}", request != null ? request.getRequestURI() : "");
        log.info("IP         : {}", request != null ? request.getRemoteAddr() : "");
        log.info("Request    : {}", toJson(maskPassword(args)));

        try {
            Object result = joinPoint.proceed();

            log.info("Response   : {}", toJson(result));
            log.info("========== API END ==========");

            return result;

        } catch (Exception e) {
            log.info("========== API ERROR ==========");
            throw e;
        }
    }

    /**
     * 取得 HttpServletRequest
     */
    private HttpServletRequest getHttpServletRequest() {
        ServletRequestAttributes attributes =
                (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

        if (attributes == null) {
            return null;
        }

        return attributes.getRequest();
    }

    /**
     * 物件轉 JSON 字串
     */
    private String toJson(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (Exception e) {
            return String.valueOf(object);
        }
    }

    /**
     * 避免密碼明文出現在 log
     */
    private Object maskPassword(Object[] args) {
        return Arrays.stream(args)
                .map(arg -> {
                    if (arg == null) {
                        return null;
                    }

                    String json = toJson(arg);

                    return json.replaceAll(
                            "(?i)\"password\"\\s*:\\s*\"[^\"]*\"",
                            "\"password\":\"******\""
                    );
                })
                .toList();
    }
}

