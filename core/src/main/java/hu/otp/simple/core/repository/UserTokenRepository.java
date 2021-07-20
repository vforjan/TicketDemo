package hu.otp.simple.core.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import hu.otp.simple.core.domain.UserToken;

/**
 * Repository for UserToken entities.
 * 
 * @author vforjan
 *
 */
public interface UserTokenRepository extends JpaRepository<UserToken, Integer> {

	UserToken findByToken(String token);

	List<UserToken> findByUserId(int userId);

}
