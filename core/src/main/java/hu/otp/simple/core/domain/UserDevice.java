package hu.otp.simple.core.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * User device entity.
 * 
 * @author vforjan
 *
 */

@Entity
@Table(name = "UserDevice")
public class UserDevice {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	@Column(name = "userId")
	private Integer userId;

	@Column(name = "deviceHash")
	private String deviceHash;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getDeviceHash() {
		return deviceHash;
	}

	public void setDeviceHash(String deviceHash) {
		this.deviceHash = deviceHash;
	}

}
