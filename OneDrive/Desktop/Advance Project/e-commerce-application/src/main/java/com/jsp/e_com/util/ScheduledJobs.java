//package com.jsp.e_com.util;
//
//import java.util.Date;
//import java.util.List;
//
//import com.jsp.e_com.entity.AccessToken;
//import com.jsp.e_com.entity.RefreshToken;
//import com.jsp.e_com.jwt.JwtService;
//import com.jsp.e_com.repository.AccessSTokenReopsitory;
//import com.jsp.e_com.repository.RefreshTokenRepositiry;
//
//public class ScheduledJobs {
//	
//	private AccessTokenReopsitory accessTokenRepository;
//	private RefreshTokenRepositiry refreshTokenRepositiry;
//	private JwtService jwtService;
//	
//	public void deleteTokensIfBlocked() {
//		List<AccessToken> at=accessTokenRepository.findAll();
//		for(AccessToken act:at) {
//			Date expiration = jwtService.getExpiration(act.getToken());
//			if(new Date().after(expiration))
//				accessTokenRepository.delete(act);
//		}
//		
//		List<RefreshToken> rt=refreshTokenRepositiry.findAll();
//		for(RefreshToken rct:rt) {
//			Date expiration = jwtService.getExpiration(rct.getToken());
//			if(new Date().after(expiration))
//				refreshTokenRepositiry.delete(rct);
//		}
////		
//		List<RefreshToken> rts =refreshTokenRepositiry.findAllIsBlocked(true);
//		List<AccessToken> ats=accessTokenRepository.findByIsBlocked(true);
//		
//		accessTokenRepository.deleteAll(ats);
//		refreshTokenRepositiry.deleteAll(rts);
//
//	}
//	
//
//}
