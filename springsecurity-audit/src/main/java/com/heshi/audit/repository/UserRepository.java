package com.heshi.audit.repository;

import com.heshi.audit.entity.UserDo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserDo, Long> {

    UserDo findByName(String name);

    UserDo findByNameAndValid(String name, Boolean valid);
}
