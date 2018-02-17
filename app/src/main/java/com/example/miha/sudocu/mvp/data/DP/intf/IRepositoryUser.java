package com.example.miha.sudocu.mvp.data.DP.intf;

import com.example.miha.sudocu.mvp.data.model.User;

public interface IRepositoryUser {
    User getUser();
    void setUser(User user);
}
