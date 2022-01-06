package com.cg.service.user;

import com.cg.model.User;
import com.cg.model.dto.UserDTO;
import com.cg.service.IGeneralService;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Optional;

public interface IUserService extends IGeneralService<User>, UserDetailsService {

    Optional<User> findByUsername(String username);

    User getByUsername(String username);

//    UserDTO findUserDTOByUsername(String username);

    Optional<UserDTO> findUserDTOByUsername(String username);

}
