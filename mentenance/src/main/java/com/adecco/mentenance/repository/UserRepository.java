package com.adecco.mentenance.repository;


import com.adecco.mentenance.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByUserName(String userName);

     List<User> findAllByRoles_RoleName(String role);


}

