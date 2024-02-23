package com.example.demo.aspect;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    @Before("execution(* com.example.demo.services.BookService.addBook(..))")
    public void logBookAddition(JoinPoint joinPoint) {
        logger.info("Book addition method called: " + joinPoint.getSignature().getName());
    }

    @Before("execution(* com.example.demo.services.BookService.updateBook(..))")
    public void logBookUpdate(JoinPoint joinPoint) {
        logger.info("Book update method called: " + joinPoint.getSignature().getName());
    }

    @Before("execution(* com.example.demo.services.PatronService.*(..))")
    public void logPatronTransaction(JoinPoint joinPoint) {
        logger.info("Patron transaction method called: " + joinPoint.getSignature().getName());
    }

    @AfterThrowing(
            pointcut = "execution(* com.example.demo.services.*.*(..))",
            throwing = "exception"
    )
    public void logException(JoinPoint joinPoint, Throwable exception) {
        logger.error("Exception in method " + joinPoint.getSignature().getName() + ": " + exception.getMessage());
    }
}
