/**
 * 业务异常类
 * <p>
 * 用于表示业务逻辑中出现的异常情况，继承自RuntimeException以支持非受检异常<br>
 * 遵循阿里巴巴Java开发规范，使用@Slf4j进行日志记录<br>
 * 支持错误码和错误消息，便于统一异常处理和错误响应
 * </p>
 *
 * @author 小王
 * @version 1.0
 * @since 2026-03-08
 */
package com.aiccoding.infrastructure.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import java.util.Objects;

/**
 * 业务异常类
 * <p>
 * 用于标识业务逻辑处理过程中出现的各种异常情况<br>
 * 可以携带错误码、错误消息和相关数据，便于问题定位和处理
 * </p>
 */
@Slf4j
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class BusinessException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    /**
     * 默认错误码：业务异常
     */
    public static final int DEFAULT_ERROR_CODE = 10000;

    /**
     * 错误码
     */
    private final Integer code;

    /**
     * 异常相关数据（可选）
     */
    private Object data;

    /**
     * 默认构造方法
     * <p>使用默认错误码和消息创建业务异常</p>
     */
    public BusinessException() {
        super("业务处理异常");
        this.code = DEFAULT_ERROR_CODE;
        log.debug("创建默认业务异常，错误码：{}", DEFAULT_ERROR_CODE);
    }

    /**
     * 构造方法
     *
     * @param message 错误消息
     */
    public BusinessException(String message) {
        super(Objects.requireNonNull(message, "错误消息不能为null"));
        this.code = DEFAULT_ERROR_CODE;
        log.debug("创建业务异常，错误码：{}，消息：{}", DEFAULT_ERROR_CODE, message);
    }

    /**
     * 构造方法
     *
     * @param code    错误码
     * @param message 错误消息
     */
    public BusinessException(Integer code, String message) {
        super(Objects.requireNonNull(message, "错误消息不能为null"));
        this.code = Objects.requireNonNull(code, "错误码不能为null");
        log.debug("创建业务异常，错误码：{}，消息：{}", code, message);
    }

    /**
     * 构造方法
     *
     * @param message 错误消息
     * @param cause   原始异常
     */
    public BusinessException(String message, Throwable cause) {
        super(Objects.requireNonNull(message, "错误消息不能为null"), cause);
        this.code = DEFAULT_ERROR_CODE;
        log.debug("创建业务异常，错误码：{}，消息：{}，原因：{}", 
                 DEFAULT_ERROR_CODE, message, cause.getClass().getSimpleName());
    }

    /**
     * 构造方法
     *
     * @param code    错误码
     * @param message 错误消息
     * @param cause   原始异常
     */
    public BusinessException(Integer code, String message, Throwable cause) {
        super(Objects.requireNonNull(message, "错误消息不能为null"), cause);
        this.code = Objects.requireNonNull(code, "错误码不能为null");
        log.debug("创建业务异常，错误码：{}，消息：{}，原因：{}", 
                 code, message, cause.getClass().getSimpleName());
    }

    /**
     * 构造方法
     *
     * @param message 错误消息
     * @param data    异常相关数据
     */
    public BusinessException(String message, Object data) {
        super(Objects.requireNonNull(message, "错误消息不能为null"));
        this.code = DEFAULT_ERROR_CODE;
        this.data = data;
        log.debug("创建业务异常，错误码：{}，消息：{}，数据：{}", 
                 DEFAULT_ERROR_CODE, message, data);
    }

    /**
     * 构造方法
     *
     * @param code    错误码
     * @param message 错误消息
     * @param data    异常相关数据
     */
    public BusinessException(Integer code, String message, Object data) {
        super(Objects.requireNonNull(message, "错误消息不能为null"));
        this.code = Objects.requireNonNull(code, "错误码不能为null");
        this.data = data;
        log.debug("创建业务异常，错误码：{}，消息：{}，数据：{}", code, message, data);
    }

    /**
     * 构造方法
     *
     * @param code    错误码
     * @param message 错误消息
     * @param data    异常相关数据
     * @param cause   原始异常
     */
    public BusinessException(Integer code, String message, Object data, Throwable cause) {
        super(Objects.requireNonNull(message, "错误消息不能为null"), cause);
        this.code = Objects.requireNonNull(code, "错误码不能为null");
        this.data = data;
        log.debug("创建业务异常，错误码：{}，消息：{}，数据：{}，原因：{}", 
                 code, message, data, cause.getClass().getSimpleName());
    }

    /**
     * 创建业务异常实例（链式调用）
     *
     * @param message 错误消息
     * @return BusinessException实例
     */
    public static BusinessException of(String message) {
        return new BusinessException(message);
    }

    /**
     * 创建业务异常实例（链式调用）
     *
     * @param code    错误码
     * @param message 错误消息
     * @return BusinessException实例
     */
    public static BusinessException of(Integer code, String message) {
        return new BusinessException(code, message);
    }

    /**
     * 创建业务异常实例（链式调用）
     *
     * @param message 错误消息
     * @param data    异常相关数据
     * @return BusinessException实例
     */
    public static BusinessException of(String message, Object data) {
        return new BusinessException(message, data);
    }

    /**
     * 创建业务异常实例（链式调用）
     *
     * @param code    错误码
     * @param message 错误消息
     * @param data    异常相关数据
     * @return BusinessException实例
     */
    public static BusinessException of(Integer code, String message, Object data) {
        return new BusinessException(code, message, data);
    }

    /**
     * 创建带参数的业务异常
     *
     * @param message 包含占位符的错误消息
     * @param args    参数值
     * @return BusinessException实例
     */
    public static BusinessException format(String message, Object... args) {
        String formattedMessage = String.format(Objects.requireNonNull(message, "错误消息不能为null"), args);
        return new BusinessException(formattedMessage);
    }

    /**
     * 创建带参数和错误码的业务异常
     *
     * @param code    错误码
     * @param message 包含占位符的错误消息
     * @param args    参数值
     * @return BusinessException实例
     */
    public static BusinessException format(Integer code, String message, Object... args) {
        String formattedMessage = String.format(Objects.requireNonNull(message, "错误消息不能为null"), args);
        return new BusinessException(code, formattedMessage);
    }

    /**
     * 获取异常摘要信息
     *
     * @return 异常摘要字符串
     */
    public String getSummary() {
        return String.format("BusinessException[code=%d, message=%s]", code, getMessage());
    }

    /**
     * 重写toString方法，提供更详细的异常信息
     *
     * @return 异常详细信息字符串
     */
    @Override
    public String toString() {
        return String.format("BusinessException{code=%d, message='%s', data=%s}", 
                           code, getMessage(), data);
    }
}