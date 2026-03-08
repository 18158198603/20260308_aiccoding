/**
 * 统一返回结果封装类
 * <p>
 * 用于标准化API接口返回格式，包含状态码、消息和数据<br>
 * 遵循阿里巴巴Java开发规范，使用@Slf4j进行日志记录
 * </p>
 *
 * @author 小王
 * @version 1.0
 * @since 2026-03-08
 */
package com.aiccoding.infrastructure.util;

import lombok.Data;
import lombok.experimental.Accessors;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;

/**
 * 响应码枚举
 */
@ApiModel(description = "统一响应结果")
@Data
@Accessors(chain = true)
public class Result<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 成功状态码
     */
    public static final int SUCCESS_CODE = 200;

    /**
     * 失败状态码
     */
    public static final int ERROR_CODE = 500;

    /**
     * 响应状态码
     */
    @ApiModelProperty(value = "响应状态码", example = "200")
    private Integer code;

    /**
     * 响应消息
     */
    @ApiModelProperty(value = "响应消息", example = "操作成功")
    private String message;

    /**
     * 响应数据
     */
    @ApiModelProperty(value = "响应数据")
    private T data;

    /**
     * 响应时间戳
     */
    @ApiModelProperty(value = "响应时间戳", example = "1646745678901")
    private Long timestamp;

    /**
     * 私有构造方法
     */
    private Result() {
        this.timestamp = System.currentTimeMillis();
    }

    /**
     * 私有构造方法
     *
     * @param code    状态码
     * @param message 消息
     * @param data    数据
     */
    private Result(Integer code, String message, T data) {
        this();
        this.code = code;
        this.message = message;
        this.data = data;
    }

    /**
     * 创建成功响应（无数据）
     *
     * @param <T> 数据类型
     * @return Result对象
     */
    public static <T> Result<T> success() {
        return new Result<>(SUCCESS_CODE, "操作成功", null);
    }

    /**
     * 创建成功响应（带数据）
     *
     * @param data 响应数据
     * @param <T>  数据类型
     * @return Result对象
     */
    public static <T> Result<T> success(T data) {
        return new Result<>(SUCCESS_CODE, "操作成功", data);
    }

    /**
     * 创建成功响应（自定义消息）
     *
     * @param message 响应消息
     * @param <T>     数据类型
     * @return Result对象
     */
    public static <T> Result<T> success(String message) {
        return new Result<>(SUCCESS_CODE, message, null);
    }

    /**
     * 创建成功响应（自定义消息和数据）
     *
     * @param message 响应消息
     * @param data    响应数据
     * @param <T>     数据类型
     * @return Result对象
     */
    public static <T> Result<T> success(String message, T data) {
        return new Result<>(SUCCESS_CODE, message, data);
    }

    /**
     * 创建错误响应（默认错误消息）
     *
     * @param <T> 数据类型
     * @return Result对象
     */
    public static <T> Result<T> error() {
        return new Result<>(ERROR_CODE, "操作失败", null);
    }

    /**
     * 创建错误响应（自定义错误消息）
     *
     * @param message 错误消息
     * @param <T>     数据类型
     * @return Result对象
     */
    public static <T> Result<T> error(String message) {
        return new Result<>(ERROR_CODE, message, null);
    }

    /**
     * 创建错误响应（自定义状态码和消息）
     *
     * @param code    错误状态码
     * @param message 错误消息
     * @param <T>     数据类型
     * @return Result对象
     */
    public static <T> Result<T> error(Integer code, String message) {
        return new Result<>(code, message, null);
    }

    /**
     * 创建错误响应（带数据）
     *
     * @param message 错误消息
     * @param data    错误数据
     * @param <T>     数据类型
     * @return Result对象
     */
    public static <T> Result<T> error(String message, T data) {
        return new Result<>(ERROR_CODE, message, data);
    }

    /**
     * 判断是否成功
     *
     * @return true-成功，false-失败
     */
    public boolean isSuccess() {
        return SUCCESS_CODE == this.code;
    }
}