package com.aiccoding.domain.repository;

import com.aiccoding.domain.entity.User;
import java.util.List;

/**
 * 用户仓储接口
 *
 * @author aiccoding
 * @date 2026-03-08
 * @description 定义用户相关的仓储操作规范（领域层接口）
 */
public interface UserRepository {

    /**
     * 根据ID查询用户
     *
     * @param id 用户ID
     * @return 用户实体，不存在返回null
     */
    User findById(Long id);

    /**
     * 根据用户名查询用户
     *
     * @param username 用户名
     * @return 用户实体，不存在返回null
     */
    User findByUsername(String username);

    /**
     * 查询所有用户
     *
     * @return 用户列表
     */
    List<User> findAll();

    /**
     * 保存用户
     *
     * @param user 用户实体
     * @return 受影响的行数
     */
    int save(User user);

    /**
     * 更新用户
     *
     * @param user 用户实体
     * @return 受影响的行数
     */
    int update(User user);

    /**
     * 删除用户
     *
     * @param id 用户ID
     * @return 受影响的行数
     */
    int deleteById(Long id);

}
