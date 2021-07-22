package hu.otp.simple.core;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.everyItem;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import hu.otp.simple.common.ErrorMessages;
import hu.otp.simple.common.dtos.UserValidationDto;
import hu.otp.simple.core.domain.User;
import hu.otp.simple.core.domain.UserDevice;
import hu.otp.simple.core.domain.UserToken;
import hu.otp.simple.core.repository.UserBankCardsRepository;
import hu.otp.simple.core.repository.UserDeviceRepository;
import hu.otp.simple.core.repository.UserRepository;
import hu.otp.simple.core.repository.UserTokenRepository;
import hu.otp.simple.core.service.UserService;
import hu.otp.simple.core.service.impl.UserServiceImpl;

@RunWith(SpringRunner.class)
public class UserServiceImplIntegrationTest {

	@TestConfiguration
	static class EmployeeServiceImplTestContextConfiguration {
		@Bean
		public UserService employeeService() {
			return new UserServiceImpl();
		}
	}

	@Autowired
	UserService userService;

	@MockBean
	private UserBankCardsRepository userBankCardsRepository;

	@MockBean
	private UserRepository userRepository;

	@MockBean
	private UserDeviceRepository userDeviceRepository;

	@MockBean
	private UserTokenRepository userTokenRepository;

	String wrongToken = "dGVzenQuYmVuZWRla0BvdHBtb2JpbC5jb20mMjAwZCZGQURERkVBNTYyRjNDOTE0RENDODE3NTY2ODJEQjBGQw==";
	String anotherToken = "dGVzenQuY2VjaWxpYUBvdHBtb2JpbC5jb20mMzAwMCZFNjg1NjA4NzJCREIyREYyRkZFN0FEQzA5MTc1NTM3OA==";
	String validTokenButAnotherUsers = "dGVzenQuYmVuZWRla0BvdHBtb2JpbC5jb20mMjAwMCZGQURERkVBNTYyRjNDOTE0RENDODE5NTY2ODJEQjBGQw==";
	String okToken = "dGVzenQuYWxhZGFyQG90cG1vYmlsLmNvbSYxMDAwJkY2N0MyQkNCRkNGQTMwRkNDQjM2RjcyRENBMjJBODE3";

	@Before
	public void setUp() {
		User johnDoe = new User();
		johnDoe.setName("Teszt Aladár");
		johnDoe.setEmail("teszt.aladar@otpmobil.com");
		johnDoe.setUserId(1000);

		User johnDoe2 = new User();
		johnDoe2.setName("Teszt Aladár2");
		johnDoe2.setEmail("teszt.aladar@otpmobil.com");
		johnDoe2.setUserId(2000);

		UserToken token1 = new UserToken();
		token1.setToken("dGVzenQuYWxhZGFyQG90cG1vYmlsLmNvbSYxMDAwJkY2N0MyQkNCRkNGQTMwRkNDQjM2RjcyRENBMjJBODE3");
		token1.setUserId(johnDoe.getUserId());
		UserToken token2 = new UserToken();
		token2.setToken("asdasasd2131234dasd21312312");
		token2.setUserId(johnDoe.getUserId());

		UserDevice device = new UserDevice();
		device.setUserId(johnDoe.getUserId());
		device.setDeviceHash("F67C2BCBFCFA30FCCB36F72DCA22A817");

		List<UserToken> tokens = Arrays.asList(token1, token2);

		Mockito.when(userRepository.findByUserId(johnDoe.getUserId())).thenReturn(johnDoe);
		Mockito.when(userRepository.findByUserId(3000)).thenReturn(null);
		Mockito.when(userRepository.findByUserId(2000)).thenReturn(johnDoe2);

		Mockito.when(userTokenRepository.findByUserId(johnDoe.getUserId())).thenReturn(tokens);
		Mockito.when(userDeviceRepository.findByUserIdAndDeviceHash(1000, device.getDeviceHash())).thenReturn(device);

	}

	@Test
	public void whenValidateTokenWithMissingToken_thenReturnFalseValidation() {
		UserValidationDto result = userService.validateUserByUserToken("");

		assertThat(result.isSuccess()).isEqualTo(false);
		assertThat(result.getOptionalError()).isNotNull();
		assertThat(result.getOptionalError()).isEqualTo(ErrorMessages.NOT_FOUND_TOKEN);

		verifyUserFindByUserIdIsCalledZero(1000);

	}

	@Test
	public void whenValidateTokenWithInvalidToken_thenReturnFalseValidation() {
		UserValidationDto result = userService.validateUserByUserToken(wrongToken);

		assertThat(result.isSuccess()).isEqualTo(false);
		assertThat(result.getOptionalError()).isNotNull();
		assertThat(result.getOptionalError()).isEqualTo(ErrorMessages.USER_ID_NOT_FOUND);

		verifyUserFindByUserIdIsCalledZero(1000);
	}

	@Test
	public void whenValidateTokenWithInvalidUserId_thenReturnFalseValidation() {
		UserValidationDto result = userService.validateUserByUserToken(anotherToken);

		assertThat(result.isSuccess()).isEqualTo(false);
		assertThat(result.getOptionalError()).isNotNull();
		assertThat(result.getOptionalError()).isEqualTo(ErrorMessages.USER_NOT_FOUND);

		verifyUserFindByUserIdIsCalledOnce(3000);
	}

	@Test
	public void whenValidateTokenWithAnotherUserId_thenReturnFalseValidation() {
		UserValidationDto result = userService.validateUserByUserToken(validTokenButAnotherUsers);

		assertThat(result.isSuccess()).isEqualTo(false);
		assertThat(result.getOptionalError()).isNotNull();
		assertThat(result.getOptionalError()).isEqualTo(ErrorMessages.TOKEN_EXPIRED);

		verifyUserFindByUserIdIsCalledOnce(2000);
		verifyTokenFindByUserIdIsCalledOnce(2000);
	}

	@Test
	public void whenValidateOkToken_thenReturnTrueValidation() {
		UserValidationDto result = userService.validateUserByUserToken(okToken);

		assertThat(result.isSuccess()).isEqualTo(true);
		assertThat(result.getOptionalError()).isNull();
		assertThat(result.getOptionalError()).isEqualTo(null);

		verifyUserFindByUserIdIsCalledOnce(1000);
		verifyTokenFindByUserIdIsCalledOnce(1000);
		verifyFindByUserIdAndDeviceHashIsCalledOnce(1000, "F67C2BCBFCFA30FCCB36F72DCA22A817");
	}

	private void verifyUserFindByUserIdIsCalledZero(Integer userId) {
		Mockito.verify(userRepository, VerificationModeFactory.times(0)).findByUserId(userId);
		Mockito.reset(userRepository);
	}

	private void verifyUserFindByUserIdIsCalledOnce(Integer userId) {
		Mockito.verify(userRepository, VerificationModeFactory.times(1)).findByUserId(userId);
		Mockito.reset(userRepository);
	}

	private void verifyTokenFindByUserIdIsCalledOnce(Integer userId) {
		Mockito.verify(userTokenRepository, VerificationModeFactory.times(1)).findByUserId(userId);
		Mockito.reset(userTokenRepository);
	}

	private void verifyFindByUserIdAndDeviceHashIsCalledOnce(Integer userId, String hash) {
		Mockito.verify(userDeviceRepository, VerificationModeFactory.times(1)).findByUserIdAndDeviceHash(userId, hash);
		Mockito.reset(userDeviceRepository);
	}

}
