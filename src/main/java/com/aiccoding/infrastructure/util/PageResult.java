/**
 * 分页结果封装类
 * <p>
 * 用于标准化分页查询的返回格式，包含总记录数、总页数、当前页数据和分页信息<br>
 * 遵循阿里巴巴Java开发规范，使用@Slf4j进行日志记录
 * </p>
 *
 * @author 小王
 * @version 1.0
 * @since 2026-03-08
 */
package com.aiccoding.infrastructure.util;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.List;

/**
 * 分页信息类
 */
@Data
@Accessors(chain = true)
class PageInfo {
    
    /**
     * 当前页码（从1开始）
     */
    @ApiModelProperty(value = "当前页码", example = "1")
    private Integer currentPage;
    
    /**
     * 每页显示条数
     */
    @ApiModelProperty(value = "每页显示条数", example = "10")
    private Integer pageSize;
    
    /**
     * 总记录数
     */
    @ApiModelProperty(value = "总记录数", example = "100")
    private Long totalCount;
    
    /**
     * 总页数
     */
    @ApiModelProperty(value = "总页数", example = "10")
    private Integer totalPages;
}

/**
 * 分页结果封装类
 *
 * @param <T> 数据类型
 */
@ApiModel(description = "分页查询结果")
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class PageResult<T> extends Result<List<T>> implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 分页信息
     */
    @ApiModelProperty(value = "分页信息")
    private PageInfo pageInfo;

    /**
     * 私有构造方法
     */
    private PageResult() {
        super();
    }

    /**
     * 创建分页成功响应
     *
     * @param data        当前页数据列表
     * @param currentPage 当前页码
     * @param pageSize    每页显示条数
     * @param totalCount  总记录数
     * @param <T>         数据类型
     * @return PageResult对象
     */
    public static <T> PageResult<T> success(List<T> data, Integer currentPage, Integer pageSize, Long totalCount) {
        PageResult<T> result = new PageResult<>();
        result.setCode(Result.SUCCESS_CODE);
        result.setMessage("查询成功");
        result.setData(data);
        
        // 计算总页数
        Integer totalPages = 0;
        if (totalCount != null && totalCount > 0) {
            totalPages = (int) Math.ceil((double) totalCount / pageSize);
        }
        
        // 构建分页信息
        PageInfo pageInfo = new PageInfo()
                .setCurrentPage(currentPage)
                .setPageSize(pageSize)
                .setTotalCount(totalCount)
                .setTotalPages(totalPages);
        
        result.setPageInfo(pageInfo);
        return result;
    }

    /**
     * 创建分页成功响应（自定义消息）
     *
     * @param message     响应消息
     * @param data        当前页数据列表
     * @param currentPage 当前页码
     * @param pageSize    每页显示条数
     * @param totalCount  总记录数
     * @param <T>         数据类型
     * @return PageResult对象
     */
    public static <T> PageResult<T> success(String message, List<T> data, Integer currentPage, Integer pageSize, Long totalCount) {
        PageResult<T> result = new PageResult<>();
        result.setCode(Result.SUCCESS_CODE);
        result.setMessage(message);
        result.setData(data);
        
        // 计算总页数
        Integer totalPages = 0;
        if (totalCount != null && totalCount > 0) {
            totalPages = (int) Math.ceil((double) totalCount / pageSize);
        }
        
        // 构建分页信息
        PageInfo pageInfo = new PageInfo()
                .setCurrentPage(currentPage)
                .setPageSize(pageSize)
                .setTotalCount(totalCount)
                .setTotalPages(totalPages);
        
        result.setPageInfo(pageInfo);
        return result;
    }

    /**
     * 获取当前页码
     *
     * @return 当前页码
     */
    public Integer getCurrentPage() {
        return pageInfo != null ? pageInfo.getCurrentPage() : 1;
    }

    /**
     * 获取每页显示条数
     *
     * @return 每页显示条数
     */
    public Integer getPageSize() {
        return pageInfo != null ? pageInfo.getPageSize() : 10;
    }

    /**
     * 获取总记录数
     *
     * @return 总记录数
     */
    public Long getTotalCount() {
        return pageInfo != null ? pageInfo.getTotalCount() : 0L;
    }

    /**
     * 获取总页数
     *
     * @return 总页数
     */
    public Integer getTotalPages() {
        return pageInfo != null ? pageInfo.getTotalPages() : 0;
    }
}