package com.aiccoding.api.facade;

import com.aiccoding.api.dto.CreateUserRequestDTO;
import com.aiccoding.api.dto.UserDTO;
import com.aiccoding.app.assembler.UserAssembler;
import com.aiccoding.app.service.UserApplicationService;
import com.aiccoding.domain.entity.User;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 用户门面接口
 *
 * @author aiccoding
 * @date 2026-03-08
 * @description 用户模块对外提供的REST API接口定义
 */
@RestController
@RequestMapping("/users")
public class UserFacade {

    private final UserApplicationService userApplicationService;

    public UserFacade(UserApplicationService userApplicationService) {
        this.userApplicationService = userApplicationService;
    }

    /**
     * 根据ID获取用户
     *
     * @param id 用户ID
     * @return 用户信息
     */
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
        User user = userApplicationService.getUserById(id);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(UserAssembler.toDTO(user));
    }

    /**
     * 根据用户名获取用户
     *
     * @param username 用户名
     * @return 用户信息
     */
    @GetMapping("/username/{username}")
    public ResponseEntity<UserDTO> getUserByUsername(@PathVariable String username) {
        User user = userApplicationService.getUserByUsername(username);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(UserAssembler.toDTO(user));
    }

    /**
     * 获取所有用户列表
     *
     * @return 用户列表
     */
    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> userDTOList = userApplicationService.getAllUsers()
                .stream()
                .map(UserAssembler::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(userDTOList);
    }

    /**
     * 创建新用户
     *
     * @param request 创建用户请求
     * @return 创建结果
     */
    @PostMapping
    public ResponseEntity<UserDTO> createUser(@Valid @RequestBody CreateUserRequestDTO request) {
        User user = UserAssembler.toEntity(request);
        boolean success = userApplicationService.createUser(user);
        if (success) {
            return ResponseEntity.ok(UserAssembler.toDTO(user));
        }
        return ResponseEntity.badRequest().build();
    }

    /**
     * 更新用户信息
     *
     * @param id   用户ID
     * @param user 用户信息
     * @return 更新结果
     */
    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Long id, @RequestBody UserDTO user) {
        user.setId(id);
        boolean success = userApplicationService.updateUser(UserAssembler.toEntity(user));
        if (success) {
            return ResponseEntity.ok(user);
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * 删除用户
     *
     * @param id 用户ID
     * @return 删除结果
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        boolean success = userApplicationService.deleteUser(id);
        if (success) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

}
