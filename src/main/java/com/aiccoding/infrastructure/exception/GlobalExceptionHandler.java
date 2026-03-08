/**
 * 全局异常处理类
 * <p>
 * 统一处理应用中抛出的各类异常，包括业务异常、系统异常、参数校验异常等<br>
 * 将异常信息转换为标准化的响应格式，保证API返回格式的一致性<br>
 * 遵循阿里巴巴Java开发规范，使用@Slf4j进行日志记录
 * </p>
 *
 * @author 小王
 * @version 1.0
 * @since 2026-03-08
 */
package com.aiccoding.infrastructure.exception;

import com.aiccoding.infrastructure.util.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 全局异常处理器
 * <p>
 * 使用@RestControllerAdvice注解，拦截所有控制器抛出的异常<br>
 * 根据不同的异常类型进行相应的处理和响应格式转换
 * </p>
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 处理业务异常
     * <p>
     * 业务异常通常表示预期内的业务规则违反，返回相应的错误码和消息<br>
     * HTTP状态码：200（业务处理成功，但业务逻辑失败）
     * </p>
     *
     * @param e 业务异常对象
     * @return 统一响应结果
     */
    @ExceptionHandler(BusinessException.class)
    @ResponseStatus(HttpStatus.OK)
    public Result<Void> handleBusinessException(BusinessException e) {
        log.warn("捕获到业务异常，错误码：{}，消息：{}，数据：{}", 
                e.getCode(), e.getMessage(), e.getData());
        
        Result<Void> result = Result.error(e.getCode(), e.getMessage());
        result.setData(e.getData());
        return result;
    }

    /**
     * 处理参数绑定异常（@RequestParam、@PathVariable等）
     * <p>
     * 处理表单参数绑定过程中的验证失败异常<br>
     * HTTP状态码：400（请求参数错误）
     * </p>
     *
     * @param e 参数绑定异常对象
     * @return 统一响应结果
     */
    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Result<Void> handleBindException(BindException e) {
        log.warn("捕获到参数绑定异常，错误数量：{}", e.getBindingResult().getErrorCount());
        
        List<String> errorMessages = extractFieldErrors(e.getFieldErrors());
        String message = buildErrorMessage("参数绑定失败", errorMessages);
        
        return Result.error(HttpStatus.BAD_REQUEST.value(), message);
    }

    /**
     * 处理JSON参数校验异常（@RequestBody）
     * <p>
     * 处理JSON请求体参数校验失败异常<br>
     * HTTP状态码：400（请求参数错误）
     * </p>
     *
     * @param e JSON参数校验异常对象
     * @return 统一响应结果
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Result<Void> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.warn("捕获到JSON参数校验异常，错误数量：{}", e.getBindingResult().getErrorCount());
        
        List<String> errorMessages = extractFieldErrors(e.getBindingResult().getFieldErrors());
        String message = buildErrorMessage("请求参数校验失败", errorMessages);
        
        return Result.error(HttpStatus.BAD_REQUEST.value(), message);
    }

    /**
     * 处理约束违反异常（@Validated注解的方法参数校验）
     * <p>
     * 处理方法级别的参数校验失败异常<br>
     * HTTP状态码：400（请求参数错误）
     * </p>
     *
     * @param e 约束违反异常对象
     * @return 统一响应结果
     */
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Result<Void> handleConstraintViolationException(ConstraintViolationException e) {
        log.warn("捕获到约束违反异常，违规数量：{}", e.getConstraintViolations().size());
        
        Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
        List<String> errorMessages = violations.stream()
                .map(violation -> {
                    String propertyPath = violation.getPropertyPath().toString();
                    String message = violation.getMessage();
                    return String.format("%s: %s", propertyPath, message);
                })
                .collect(Collectors.toList());
        
        String message = buildErrorMessage("参数校验失败", errorMessages);
        return Result.error(HttpStatus.BAD_REQUEST.value(), message);
    }

    /**
     * 处理非法参数异常
     * <p>
     * 处理IllegalArgumentException及其子类异常<br>
     * HTTP状态码：400（请求参数错误）
     * </p>
     *
     * @param e 非法参数异常对象
     * @return 统一响应结果
     */
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Result<Void> handleIllegalArgumentException(IllegalArgumentException e) {
        log.warn("捕获到非法参数异常，消息：{}", e.getMessage());
        return Result.error(HttpStatus.BAD_REQUEST.value(), 
                Objects.requireNonNullElse(e.getMessage(), "请求参数不合法"));
    }

    /**
     * 处理空指针异常
     * <p>
     * 处理NullPointerException，通常是由于代码bug导致的<br>
     * HTTP状态码：500（服务器内部错误）
     * </p>
     *
     * @param e 空指针异常对象
     * @return 统一响应结果
     */
    @ExceptionHandler(NullPointerException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Result<Void> handleNullPointerException(NullPointerException e) {
        log.error("捕获到空指针异常，这可能是代码bug", e);
        return Result.error(HttpStatus.INTERNAL_SERVER_ERROR.value(), 
                "服务器内部错误：空指针异常");
    }

    /**
     * 处理通用运行时异常
     * <p>
     * 处理未被特定处理器处理的RuntimeException<br>
     * HTTP状态码：500（服务器内部错误）
     * </p>
     *
     * @param e 运行时异常对象
     * @return 统一响应结果
     */
    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Result<Void> handleRuntimeException(RuntimeException e) {
        log.error("捕获到未处理的运行时异常", e);
        return Result.error(HttpStatus.INTERNAL_SERVER_ERROR.value(), 
                Objects.requireNonNullElse(e.getMessage(), "服务器内部错误"));
    }

    /**
     * 处理所有其他异常
     * <p>
     * 作为最后的异常处理器，处理所有未被前面处理器处理的异常<br>
     * HTTP状态码：500（服务器内部错误）
     * </p>
     *
     * @param e 异常对象
     * @return 统一响应结果
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Result<Void> handleException(Exception e) {
        log.error("捕获到未处理的异常", e);
        return Result.error(HttpStatus.INTERNAL_SERVER_ERROR.value(), 
                "服务器内部错误，请联系管理员");
    }

    /**
     * 提取字段错误信息
     *
     * @param fieldErrors 字段错误列表
     * @return 错误信息列表
     */
    private List<String> extractFieldErrors(List<FieldError> fieldErrors) {
        if (fieldErrors == null || fieldErrors.isEmpty()) {
            return new ArrayList<>();
        }
        
        return fieldErrors.stream()
                .filter(Objects::nonNull)
                .map(fieldError -> {
                    String field = fieldError.getField();
                    String message = fieldError.getDefaultMessage();
                    return String.format("字段[%s]：%s", field, 
                            Objects.requireNonNullElse(message, "参数错误"));
                })
                .collect(Collectors.toList());
    }

    /**
     * 构建错误消息
     *
     * @param prefix        错误前缀
     * @param errorMessages 具体错误信息列表
     * @return 完整的错误消息
     */
    private String buildErrorMessage(String prefix, List<String> errorMessages) {
        if (errorMessages == null || errorMessages.isEmpty()) {
            return prefix;
        }
        
        if (errorMessages.size() == 1) {
            return prefix + "，详情：" + errorMessages.get(0);
        }
        
        return prefix + "，详情：" + String.join("；", errorMessages);
    }
}