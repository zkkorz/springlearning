package com.spring.learning.dependency.injection;

import com.spring.learning.iocoverview.domain.User;

public class UserHolder {

    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "UserHolder{" +
                "user=" + user +
                '}';
    }

    public UserHolder(User user) {
        this.user = user;
    }

    public UserHolder() {
    }
}
