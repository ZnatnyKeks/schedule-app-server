package com.ru.dating.repositories;

import com.ru.dating.entities.token.Token;
import com.ru.dating.entities.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<Token, String> {
    Optional<Token> getByRefreshToken(String refreshToken);

    Optional<Token> getByUser(User user);
}
