package org.spring.learning.springbean.factory;

import com.spring.learning.iocoverview.domain.User;

/**
 * User抽象工厂
 */
public interface UserFactory {

    default User createUser(){
        return new User();
    }

    void initUserFactory();

    void destroyUserFactory();
}
