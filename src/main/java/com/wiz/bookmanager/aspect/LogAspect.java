package com.wiz.bookmanager.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * ログを自動でメソッドの前後に差し込むためのクラス
 */
@Aspect
@Component
public class LogAspect {

    /**
     * ロガー
     */
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * Beforeアノテーションにより、指定したメソッドの前に処理を追加する
     * Pointcut式 execution(戻り値 パッケージ.クラス.メソッド(引数))
     * @param joinPoint
     */
    @Before("execution(* com.wiz.bookmanager.controller..*.*Controller.*(..))")
    public void doBeforeController(JoinPoint joinPoint){
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String url = request.getRequestURL().toString();
        String ip = request.getRemoteAddr();
        String classMethod = joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();
        RequestLog requestLog = new RequestLog(url, ip, classMethod, args);
        logger.debug("▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲ request: {}", requestLog);
    }

    /**
     * Afterアノテーションにより、指定したメソッドの後に処理を追加する
     */
    @After("execution(* com.wiz.bookmanager.controller..*.*Controller.*(..))")
    public void doAfterController(){
        logger.debug("■■■■■■■■■■■■■■■　doAfter controller");
    }

    @AfterReturning(pointcut = "execution(* com.wiz.bookmanager.controller..*.*Controller.*(..))", returning= "result")
    public void doAfterReturn(Object result){
        logger.debug("■■■■■■■■■■■■■■■　Result : {}", result);
    }

    @Before("execution(* com.wiz.bookmanager.service.*ServiceImpl.*(..))")
    public void doBeforeService (JoinPoint joinPoint) {
        String classMethod = joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();
        MethodLog methodLog = new MethodLog(classMethod, args);
        logger.debug("■■■■■■■■■■■■■■■　call service   : {}", methodLog);
    }

    /**
     * ログ出力フォーマットクラス
     */
    private class RequestLog{
        private String url;
        private String ip;
        private String classMethod;
        private Object[] args;

        /**
         * コンストラクター
         * @param url url
         * @param ip ipアドレス
         * @param classMethod クラス+メソッド名
         * @param args 引数
         */
        public RequestLog(String url, String ip, String classMethod, Object[] args) {
            this.url = url;
            this.ip = ip;
            this.classMethod = classMethod;
            this.args = args;
        }

        @Override
        public String toString() {
            return "{" +
                    " url='" + url + '\'' +
                    ", ip='" + ip + '\'' +
                    ", classMethod='" + classMethod + '\'' +
                    ", args=" + Arrays.toString(args) +
                    '}';
        }
    }

    /**
     * ログ出力フォーマットクラス
     */
    private class MethodLog{
        private String classMethod;
        private Object[] args;

        /**
         * コンストラクター
         * @param classMethod クラス+メソッド名
         * @param args 引数
         */
        public MethodLog(String classMethod, Object[] args) {
            this.classMethod = classMethod;
            this.args = args;
        }

        @Override
        public String toString() {
            return "{" +
                    " classMethod='" + classMethod + '\'' +
                    ", args=" + Arrays.toString(args) +
                    '}';
        }
    }

}
