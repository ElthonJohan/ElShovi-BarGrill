package com.ELShovi.service;

import com.ELShovi.dto.ProfileDTO;
import com.ELShovi.model.User;

public interface IUserService extends IGenericService<User,Integer> {
    public User updateProfile(Integer idUser, ProfileDTO dto) ;
}
