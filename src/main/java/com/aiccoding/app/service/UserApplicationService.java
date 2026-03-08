package com.aiccoding.app.service;

import com.aiccoding.domain.entity.User;
import com.aiccoding.domain.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 用户应用服务
 *
 * @author aiccoding
 * @date 2026-03-08
 * @description 用户业务编排层，负责业务流程编排和事务管理
 */
public class UserApplicationService {

    private final UserRepository userRepository;

    public UserApplicationService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * 根据ID获取用户
     *
     * @param id 用户ID
     * @return 用户实体
     */
    public User getUserById(Long id) {
        return userRepository.findById(id);
    }

    /**
     * 根据用户名获取用户
     *
     * @param username 用户名
     * @return 用户实体
     */
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    /**
     * 获取所有用户列表
     *
     * @return 用户列表
     */
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    /**
     * 创建新用户
     *
     * @param user 用户信息
     * @return 创建成功返回true
     */
    public boolean createUser(User user) {
        // 业务校验
        User existingUser = userRepository.findByUsername(user.getUsername());
        if (existingUser != null) {
            throw new IllegalArgumentException("用户名已存在: " + user.getUsername());
        }

        // 设置创建时间和默认状态
        user.setCreateTime(LocalDateTime.now());
        user.setUpdateTime(LocalDateTime.now());
        user.setStatus(1);

        return userRepository.save(user) > 0;
    }

    /**
     * 更新用户信息
     *
     * @param user 用户信息
     * @return 更新成功返回true
     */
    public boolean updateUser(User user) {
        User existingUser = userRepository.findById(user.getId());
        if (existingUser == null) {
            throw new IllegalArgumentException("用户不存在: " + user.getId());
        }

        user.setUpdateTime(LocalDateTime.now());
        return userRepository.update(user) > 0;
    }

    /**
     * 删除用户
     *
     * @param id 用户ID
     * @return 删除成功返回true
     */
    public boolean deleteUser(Long id) {
        User existingUser = userRepository.findById(id);
        if (existingUser == null) {
            throw new IllegalArgumentException("用户不存在: " + id);
        }

        return userRepository.deleteById(id) > 0;
    }

}
