package com.heshi.audit.service;

import com.heshi.audit.entity.UserDo;
import com.heshi.audit.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    /**
     * 保存user对象
     *
     * @param userDo
     * @return
     */
    public UserDo save(UserDo userDo) {
        return userRepository.save(userDo);
    }

    /**
     * 查询user对象
     *
     * @param id
     * @return
     */
    public UserDo get(Long id) {
        Optional<UserDo> option = userRepository.findById(id);
        if (option.isPresent()) {
            return option.get();
        } else {
            return null;
        }
    }

    /**
     * 删除user
     *
     * @param id
     */
    public void delete(Long id) {
        try {
            userRepository.deleteById(id);
        } catch (Exception e) {
        }
    }

    /**
     * 修改密码
     *
     * @param id
     * @param password
     * @return
     */
    public UserDo patchUserDo(Long id, String password) {
        UserDo userDo = get(id);
        if (null != userDo) {
            userDo.setPassword(password);
            userDo = save(userDo);
        }
        return userDo;
    }

    /**
     * 修改userDo对象
     * @param id
     * @param userDo
     * @return
     */
    public UserDo putUserDo(Long id, UserDo userDo) {
        return userRepository.save(userDo);
    }
}
