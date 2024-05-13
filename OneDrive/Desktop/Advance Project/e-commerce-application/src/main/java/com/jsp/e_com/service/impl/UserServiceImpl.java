package com.jsp.e_com.service.impl;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Random;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.jsp.e_com.cache.CacheStore;
import com.jsp.e_com.entity.AccessToken;
import com.jsp.e_com.entity.Customer;
import com.jsp.e_com.entity.RefreshToken;
import com.jsp.e_com.entity.Seller;
import com.jsp.e_com.entity.User;
import com.jsp.e_com.enums.UserRole;
import com.jsp.e_com.exception.AccontAccessRequestDeniedException;
import com.jsp.e_com.exception.IllegelAccessRequestExcption;
import com.jsp.e_com.exception.InvalidCradentialException;
import com.jsp.e_com.exception.InvalidEmailIdException;
import com.jsp.e_com.exception.InvalidOTPException;
import com.jsp.e_com.exception.OtpExpiredException;
import com.jsp.e_com.exception.RegistretionSessionExpierdException;
import com.jsp.e_com.exception.TokenNullException;
import com.jsp.e_com.exception.UserAreadyPresentException;
import com.jsp.e_com.jwt.JwtService;
import com.jsp.e_com.mail_service.MailService;
import com.jsp.e_com.repository.AccessTokenReopsitory;
import com.jsp.e_com.repository.RefreshTokenRepositiry;
import com.jsp.e_com.repository.UserRepository;
import com.jsp.e_com.request.dto.AuthRequest;
import com.jsp.e_com.request.dto.OtpRequest;
import com.jsp.e_com.request.dto.UserRequest;
import com.jsp.e_com.responce.dto.AuthResponce;
import com.jsp.e_com.responce.dto.UserResponce;
import com.jsp.e_com.service.UserService;
import com.jsp.e_com.util.MessageModel;
import com.jsp.e_com.util.ResponseStructure;
import com.jsp.e_com.util.SimpleResponseStructer;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;

@Service
public class UserServiceImpl implements UserService {

	private UserRepository userRepository;
	private ResponseStructure<UserResponce> responseStructure;
	private CacheStore<String> otpCache;
	private CacheStore<User> userCache;
	private SimpleResponseStructer simpleResponseStructer;
	private MailService mailService;
	private AuthenticationManager authenticationManager;
	private JwtService jwtService;
	private AccessTokenReopsitory accessTokenRepo;
	private RefreshTokenRepositiry refreshTokenRepo;

	private PasswordEncoder pe;


	@Value("${myapp.jwt.refresh.expiration}")
	private long refreshExpiration;
	@Value("${myapp.jwt.access.expiration}")
	private long accessExpiration;



	public UserServiceImpl(UserRepository userRepository, ResponseStructure<UserResponce> responseStructure,
			CacheStore<String> otpCache, CacheStore<User> userCache, SimpleResponseStructer simpleResponseStructer,
			MailService mailService, AuthenticationManager authenticationManager, JwtService jwtService,
			AccessTokenReopsitory accessTokenRepo, RefreshTokenRepositiry refreshTokenRepo, PasswordEncoder pe) {
		super();
		this.userRepository = userRepository;
		this.responseStructure = responseStructure;
		this.otpCache = otpCache;
		this.userCache = userCache;
		this.simpleResponseStructer = simpleResponseStructer;
		this.mailService = mailService;
		this.authenticationManager = authenticationManager;
		this.jwtService = jwtService;
		this.accessTokenRepo = accessTokenRepo;
		this.refreshTokenRepo = refreshTokenRepo;
		this.pe = pe;
	}

	@Override
	public ResponseEntity<SimpleResponseStructer> registerUser(UserRequest userRequest) {

		if(userRepository.existsByEmail(userRequest.getUserEmail()))
			throw new UserAreadyPresentException("User already exist");

		User user=mapToChildEntity(userRequest);
		String otp=generateOTP();


		otpCache.add(user.getEmail(), otp);
		userCache.add(user.getEmail(), user);

		//send mail with otp
		try {
			sendOtp(user,otp);
		} catch (MessagingException e) {
			throw new InvalidEmailIdException("email id not fuond");
		}



		return ResponseEntity.ok(simpleResponseStructer.setStatus(HttpStatus.ACCEPTED.value())
				.setMassage(otp+"Verify otp sent through mail to complete registr OTP expires in 1 minute"));

	}


	private void sendOtp(User user, String otp) throws MessagingException{

		MessageModel model=MessageModel.builder()
				.to(user.getEmail())
				.subject("verify your otp")
				.text(
						"<p>Hi, <br>"
								+"Thanks for your intrest in E-com,"
								+ "Pleace verify your mail Id using the otp given below</p>."
								+ "<br>"
								+ "<h1>"+otp+"</h1>"
								+ "<br>"
								+ "<p>pleace ignore if not you"
								+ "<br>"
								+ "with best regards"
								+ "<h3>E-comm</h3>"
								+ "<img height=100px width=200px src=https://seeklogo.com/images/F/flipkart-logo-3F33927DAA-seeklogo.com.png>"
						)
				.build();

		mailService.sendMailMessage(model);
	}


	@Override
	public ResponseEntity<ResponseStructure<UserResponce>> verifyOTP(OtpRequest otpRequest) {

		if(otpCache.get(otpRequest.getEmail())==null) throw new OtpExpiredException("OTP expired");
		if(!otpCache.get(otpRequest.getEmail()).equals(otpRequest.getOtp())) throw new InvalidOTPException("otp not matched");

		User user=userCache.get(otpRequest.getEmail());
		if(user==null) throw new RegistretionSessionExpierdException("Registretion time is over");
		user.setEmailVerified(true);
		System.out.println(user.getPassword());
		user.setPassword(pe.encode(user.getPassword()));
		System.out.println(user.getPassword());
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(responseStructure.setData(mapToUserResponce(userRepository.save(user)))
						.setMessage("otp verifyed sucsssefully")
						.setStatuscode(HttpStatus.OK.value()));
	}




	private String generateOTP() {
		return String.valueOf(new Random().nextInt(100000,999999));

	}

	private LocalDateTime mapToLocalDateAndTime(long millisecond) {
		Instant instant=Instant.ofEpochMilli(millisecond);
		LocalDateTime localDateTime=LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
		return  localDateTime;
	}


	private <T extends User> T mapToChildEntity(UserRequest userRequest) {

		UserRole role=userRequest.getRole();

		User user=null;

		switch (role) {
		case SELLER ->user=new Seller();
		case CUSTOMER ->user=new Customer();
		default ->throw new IllegelAccessRequestExcption("In valid user role");
		}

		user.setDisplayName(userRequest.getDisplayName());
		user.setUsername(userRequest.getUserEmail().split("@gmail.com")[0]);
		user.setEmail(userRequest.getUserEmail());
		user.setPassword(userRequest.getPassword());
		user.setEmailVerified(false);
		user.setUserRole(userRequest.getRole());
		return (T) user;
	}

	static UserResponce mapToUserResponce(User user) {
		UserResponce responce=UserResponce.builder()
				.displayName(user.getDisplayName())
				.userId(user.getUserId())
				.userName(user.getUsername())
				.email(user.getEmail())
				.isEmailVerified(user.isEmailVerified())
				.role(user.getUserRole())
				.build();
		return responce;

	}


	@Override
	public ResponseEntity<ResponseStructure<AuthResponce>> login(AuthRequest authRequste) {

		String username=authRequste.getUsername().split("@gmail.com")[0];

		System.err.println(username);
		System.err.println(authRequste.getPassword());

		Authentication authentication = authenticationManager.authenticate
				(new UsernamePasswordAuthenticationToken(username, authRequste.getPassword()));

		if(!authentication.isAuthenticated())throw new InvalidCradentialException("user not authenticated");

		SecurityContextHolder.getContext().setAuthentication(authentication);

		HttpHeaders headers=new HttpHeaders();
		

		return userRepository.findByUsername(username).map(user -> {

			generateRefresfToken(user,headers);
			generateAccessToken(user,headers);


			return ResponseEntity.ok().headers(headers).body(new ResponseStructure<AuthResponce>()
					.setData(mapToAuthResponse(user))
					.setMessage("Authentiction Successfull")
					.setStatuscode(HttpStatus.OK.value()));
		}).get();

	}


	private AuthResponce mapToAuthResponse(User user) {
		return AuthResponce.builder()
				.username(user.getUsername())
				.id(user.getUserId())
				.role(user.getUserRole())
				.accessExpriration(accessExpiration)
				.refershExpiration(refreshExpiration)
				.build();
	}

	private void generateRefresfToken(User user, HttpHeaders headers) {
		String token = jwtService.generateRefreshToken(user.getUsername(),user.getUserRole().name());
		headers.add(HttpHeaders.SET_COOKIE,configureCookie("rt",token,refreshExpiration));
		RefreshToken refreshToken=new RefreshToken();
		refreshToken.setToken(token);
		refreshToken.setBlocked(false);
		
		refreshToken.setExpiration(mapToLocalDateAndTime(refreshExpiration));
		refreshToken.setUser(user);
		refreshTokenRepo.save(refreshToken);
	}
	private void generateAccessToken(User user, HttpHeaders headers) {
		String token = jwtService.generateAccessToken(user.getUsername(),user.getUserRole().name());
		headers.add(HttpHeaders.SET_COOKIE,configureCookie("at",token,accessExpiration));

		AccessToken accessToken=new AccessToken();
		accessToken.setToken(token);
		accessToken.setBlocked(false);
		
		
		accessToken.setExpiration(mapToLocalDateAndTime(accessExpiration));
		accessToken.setUser(user);
		accessTokenRepo.save(accessToken);
	}

	private String configureCookie(String name,String value,long maxAge) {

		return ResponseCookie.from(name, value)
				.domain("localhost")
				.path("/")
				.httpOnly(true)
				.secure(false)
				.maxAge(Duration.ofMillis(maxAge))
				.sameSite("Lax")
				.build().toString();
	}

	@Override
	public ResponseEntity<SimpleResponseStructer> userLogout(String refreshToken, String accessToken) {
		if(refreshToken==null&&accessToken==null)throw new UsernameNotFoundException("user is not login");
		HttpHeaders header=new HttpHeaders();
		header.add(HttpHeaders.SET_COOKIE, validateCookie("at"));
		header.add(HttpHeaders.SET_COOKIE, validateCookie("rt"));
		blockAccessToken(accessToken);
		blockRefreshToken(refreshToken);

		return ResponseEntity.ok().headers(header).body(simpleResponseStructer.setStatus(HttpStatus.OK.value()).setMassage("user Logout successfull"));
	}

	private void blockRefreshToken(String refreshToken) {
		refreshTokenRepo.findByToken(refreshToken).ifPresent((rt)->{
			rt.setBlocked(true);
			refreshTokenRepo.save(rt);
		}
				);
	}

	private void blockAccessToken(String accessToken) {
		accessTokenRepo.findByToken(accessToken).ifPresent((at)->{
			at.setBlocked(true);
			accessTokenRepo.save(at);

		});
	}
	
	public String validateCookie(String name) {
		return ResponseCookie.from(name,"")
				.domain("localhost")
				.path("/")
				.httpOnly(true)
				.secure(false)
				.maxAge(0)
				.sameSite("Lax")
				.build().toString();
	}

//	
	
	private String deConfigureCookie(String name, String value) {

		return ResponseCookie.from(name, value)
				.domain("localhost")
				.path("/")
				.httpOnly(true)
				.secure(false)
				.maxAge(0)
				.sameSite("Lax")
				.build().toString();
	}

	@Override
	public ResponseEntity<ResponseStructure<AuthResponce>> refreshLogin(String accessToken, String refreshToken) {
      
		if(refreshToken==null) {
			throw new IllegalArgumentException("User is not logged in");
		}
		if(accessToken!=null) {
			accessTokenRepo.findByToken(refreshToken).ifPresent(token -> {
				token.setBlocked(true);
				accessTokenRepo.save(token);
			});
		}
		Date date=jwtService.getIssueDate(refreshToken);
		String username=jwtService.getUsername(refreshToken);
		HttpHeaders headers=new HttpHeaders();
		
		return userRepository.findByUsername(username).map(user -> {
			if(date.before(new Date()))
				generateAccessToken(user, headers);
			else
				headers.add(HttpHeaders.SET_COOKIE, configureCookie("rt", refreshToken, refreshExpiration));
			generateAccessToken(user, headers);
			return ResponseEntity.ok().headers(headers).body(new ResponseStructure<AuthResponce>()
				.setStatuscode(HttpStatus.OK.value()).setMessage("Token is Refresh seccusfully")
				.setData(mapToAuthResponse(user)));
		}).get();
		
	}





}
