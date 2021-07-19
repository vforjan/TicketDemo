package hu.otp.simple.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import hu.otp.simple.domain.User;

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
