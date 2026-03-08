package com.aiccoding.app.assembler;

import com.aiccoding.api.dto.CreateUserRequestDTO;
import com.aiccoding.api.dto.UserDTO;
import com.aiccoding.domain.entity.User;

/**
 * 用户对象转换器
 *
 * @author aiccoding
 * @date 2026-03-08
 * @description 负责Entity与DTO之间的相互转换
 */
public class UserAssembler {

    /**
     * 将User实体转换为UserDTO
     *
     * @param user 用户实体
     * @return 用户DTO
     */
    public static UserDTO toDTO(User user) {
        if (user == null) {
            return null;
        }

        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        dto.setPhone(user.getPhone());
        dto.setRealName(user.getRealName());
        dto.setStatus(user.getStatus());
        dto.setCreateTime(user.getCreateTime());
        dto.setUpdateTime(user.getUpdateTime());

        return dto;
    }

    /**
     * 将CreateUserRequestDTO转换为User实体
     *
     * @param requestDTO 创建用户请求DTO
     * @return 用户实体
     */
    public static User toEntity(CreateUserRequestDTO requestDTO) {
        if (requestDTO == null) {
            return null;
        }

        User user = new User();
        user.setUsername(requestDTO.getUsername());
        user.setPassword(requestDTO.getPassword());
        user.setEmail(requestDTO.getEmail());
        user.setPhone(requestDTO.getPhone());
        user.setRealName(requestDTO.getRealName());

        return user;
    }

}
