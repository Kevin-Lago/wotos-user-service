package com.wotos.wotosuserservice.dao;

import com.wotos.wotosuserservice.model.LocalUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Optional;

public interface UserRepo extends JpaRepository<LocalUser, Integer> {

    Optional<LocalUser> findLocalUserByUsername(String username);

    LocalUser save(LocalUser localUser);

}
