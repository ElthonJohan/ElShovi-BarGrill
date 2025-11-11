package com.ELShovi.repository;

import com.ELShovi.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface IUserRepository extends IGenericRepository<User, Integer> {
    Optional<User> findByEmail( String email);

}
