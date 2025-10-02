package com.library.members.datalayer.Users;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Integer> {

    User findUserByUserIdentifier_UserId(String userId);

    void deleteUserByUserIdentifier_UserId(String userId);

}
