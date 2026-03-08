package com.aiccoding.infrastructure.repository;

import com.aiccoding.domain.entity.User;
import com.aiccoding.domain.repository.UserRepository;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 用户仓储实现类
 *
 * @author aiccoding
 * @date 2026-03-08
 * @description 用户仓储的具体实现（基础设施层）
 */
@Repository
public class UserRepositoryImpl implements UserRepository {

    private static final String NAMESPACE = "com.aiccoding.infrastructure.repository.UserMapper.";

    private final SqlSessionTemplate sqlSessionTemplate;

    public UserRepositoryImpl(SqlSessionTemplate sqlSessionTemplate) {
        this.sqlSessionTemplate = sqlSessionTemplate;
    }

    @Override
    public User findById(Long id) {
        return sqlSessionTemplate.selectOne(NAMESPACE + "findById", id);
    }

    @Override
    public User findByUsername(String username) {
        return sqlSessionTemplate.selectOne(NAMESPACE + "findByUsername", username);
    }

    @Override
    public List<User> findAll() {
        return sqlSessionTemplate.selectList(NAMESPACE + "findAll");
    }

    @Override
    public int save(User user) {
        return sqlSessionTemplate.insert(NAMESPACE + "save", user);
    }

    @Override
    public int update(User user) {
        return sqlSessionTemplate.update(NAMESPACE + "update", user);
    }

    @Override
    public int deleteById(Long id) {
        return sqlSessionTemplate.delete(NAMESPACE + "deleteById", id);
    }

}
