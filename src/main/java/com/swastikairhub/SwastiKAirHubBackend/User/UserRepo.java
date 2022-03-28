package com.swastikairhub.SwastiKAirHubBackend.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User,String> {

    Optional<User> findCustomerByUsername(String username);
    Optional<User> findCustomerByEmail(String email);
    Optional<User> findCustomerByPhoneNumber(String phoneNumber);
    Optional<User> findByUsername(String username);
}
