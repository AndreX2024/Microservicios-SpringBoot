package dev.carloscastano.get.services;

import dev.carloscastano.get.entities.User;

import java.util.List;

public interface IUserService {
    List<User> getAll();
}
