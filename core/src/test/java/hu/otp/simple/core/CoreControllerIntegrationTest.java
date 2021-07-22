package hu.otp.simple.core;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.internal.verification.VerificationModeFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import hu.otp.simple.common.dtos.UserValidationDto;
import hu.otp.simple.core.controller.CoreController;
import hu.otp.simple.core.repository.UserBankCardsRepository;
import hu.otp.simple.core.repository.UserDeviceRepository;
import hu.otp.simple.core.repository.UserRepository;
import hu.otp.simple.core.repository.UserTokenRepository;
import hu.otp.simple.core.service.UserService;

@RunWith(SpringRunner.class)
@WebMvcTest(value = CoreController.class, excludeAutoConfiguration = SecurityAutoConfiguration.class)
// @TestPropertySource(locations = "classpath:application-integrationtest.properties")
// @SpringBootTest
public class CoreControllerIntegrationTest {

	@Autowired
	private MockMvc mvc;

	@MockBean
	private UserBankCardsRepository userBankCardsRepository;

	@MockBean
	private UserRepository userRepository;

	@MockBean
	private UserDeviceRepository userDeviceRepository;

	@MockBean
	private UserTokenRepository userTokenRepository;

	@MockBean
	private UserService userService;

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void givenToken_whenValidateToken_thenReturnUserValidationDto() throws Exception {
		String token = "asdasdasdasd";
		UserValidationDto dto = new UserValidationDto();
		dto.setSuccess(true);
		dto.setToken(token);

		given(userService.validateUserByUserToken(token)).willReturn(dto);

		mvc.perform(get("/core/validate-token").contentType(MediaType.APPLICATION_JSON).param("token", token)).andExpect(status().isOk());

		verify(userService, VerificationModeFactory.times(1)).validateUserByUserToken(token);
		reset(userService);
	}

	@Test
	public void notGivenToken_whenValidateToken_thenReturnBadRequest() throws Exception {
		mvc.perform(get("/core/validate-token").contentType(MediaType.APPLICATION_JSON)).andExpect(status().is4xxClientError());
	}

}
