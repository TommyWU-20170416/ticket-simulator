package com.wsi.walletservices.repository;

import com.wsi.walletservices.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
}
