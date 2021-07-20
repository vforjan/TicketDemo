package hu.otp.simple.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import hu.otp.simple.core.domain.UserDevice;

/**
 * Repository for UserDevice entities.
 * 
 * @author vforjan
 *
 */

public interface UserDeviceRepository extends JpaRepository<UserDevice, Integer> {

	UserDevice findByUserIdAndDeviceHash(int userId, String deviceHash);

}
