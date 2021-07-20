package hu.otp.simple.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import hu.otp.simple.core.domain.User;

/**
 * Repository for User entities.
 * 
 * @author vforjan
 *
 */
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

	User findByUserId(Integer userId);

}
