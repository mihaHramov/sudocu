package com.example.miha.sudocu.data.DP.intf;

import com.example.miha.sudocu.data.model.User;

public interface IRepositoryUser {
    User getUser();
    void setUser(User user);
}
