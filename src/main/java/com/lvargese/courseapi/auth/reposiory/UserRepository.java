package com.lvargese.courseapi.auth.reposiory;

import com.lvargese.courseapi.auth.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
}
