package com.mycompany.webapp.controller;

import java.net.URLEncoder;
import java.util.Date;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Controller
@RequestMapping("/ch05")
public class Ch05Controller {

	private static final Logger logger = LoggerFactory.getLogger(Ch05Controller.class);

	@RequestMapping("/content")
	public String content() {
		return "ch05/content";
	}

	// 헤더값 읽기
	@GetMapping("/getheadervalue")
	public String getHeaderValue(HttpServletRequest request) {
		logger.info("실행");

		logger.info("method: " + request.getMethod());
		logger.info("requestURI: " + request.getRequestURI());
		logger.info("client IP: " + request.getRemoteAddr());
		logger.info("contextRoot: " + request.getContextPath());
		String userAgent = request.getHeader("User-Agent");
		logger.info("User-Agent: " + userAgent);

		if (userAgent.contains("Windows NT")) {
			logger.info("client OS Windows");
		} else if (userAgent.contains("Mac")) {
			logger.info("client OS: macOS");
		}

		if (userAgent.contains("Edg")) {
			logger.info("client OS: Edge");
		} else if (userAgent.contains("Trident")) {
			logger.info("client OS: IE11");
		} else if (userAgent.contains("Chrome")) {
			logger.info("client OS: Chrome");
		} else if (userAgent.contains("Safari")) {
			logger.info("client OS: Safari");
		}
		return "redirect:/ch05/content";
	}

	// 쿠키 생성
	@GetMapping("/createcookie")
	public String createCookie(HttpServletResponse response) {
		logger.info("실행");

		Cookie cookie = new Cookie("useremail", "huhjb1020@naver.com");
		// cookie.setDomain("localhost"); // localhost로 전송
		// cookie.setPath("/"); // localhost/* 이면 모두 전송
		// setMaxAge 초 단위
		cookie.setMaxAge(30 * 60); // 30분
		cookie.setHttpOnly(true); // JavaScript에서 못 읽게함
		cookie.setSecure(true); // https://만 전송
		response.addCookie(cookie);

		return "redirect:/ch05/content";
	}

	// 쿠키 읽기
	@GetMapping("/getcookie")
	public String getCookie(@CookieValue("useremail") String uemail, @CookieValue String useremail) {
		logger.info("실행");

		logger.info("uemail: " + uemail);
		logger.info("useremail: " + useremail);

		return "redirect:/ch05/content";
	}

	@GetMapping("/getcookie2")
	public String getCookie2(HttpServletRequest request) {
		logger.info("실행");

		Cookie[] cookies = request.getCookies();
		for (Cookie cookie : cookies) {
			String cookieName = cookie.getName();
			String cookieValue = cookie.getValue();
			if (cookieName.equals("useremail")) {
				logger.info("useremail: " + cookieValue);
				break;
			}
		}

		return "redirect:/ch05/content";
	}

	// JSON 쿠키 생성
	@GetMapping("/createjsoncookie")
	public String createJsonCookie(HttpServletResponse response) throws Exception {
		logger.info("실행");

		// String json = "{\"userId\": \"fall\", \"useremail\": \"fall@company.com\",
		// \"username\": \"홍길동\"}";
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("userid", "fall");
		jsonObject.put("useremail", "fall@company.com");
		jsonObject.put("username", "홍길동");
		String json = jsonObject.toString();
		logger.info("json: " + json);
		json = URLEncoder.encode(json, "UTF-8"); // 한글은 인코딩 필수
		logger.info("json: " + json);

		Cookie cookie = new Cookie("user", json);
		response.addCookie(cookie);

		return "redirect:/ch05/content";
	}

	// JSON 쿠키 읽기
	@GetMapping("/getjsoncookie")
	public String getJsonCookie(@CookieValue String user) {
		logger.info("실행");
		logger.info("user: " + user);
		JSONObject jsonObject = new JSONObject(user);
		logger.info("userid: " + jsonObject.getString("userid"));
		logger.info("useremail: " + jsonObject.getString("useremail"));
		logger.info("username: " + jsonObject.getString("username"));

		return "redirect:/ch05/content";
	}

	@GetMapping("/createjwtcookie")
	public String createJwtCookie(HttpServletResponse response) throws Exception {
		logger.info("실행");

		String userid = "fall";
		String useremail = "fall@mycompany.com";
		String username = "홍길동";

		JwtBuilder builder = Jwts.builder();
		// HEADER:ALGORITHM & TOKEN TYPE
		builder.setHeaderParam("alg", "HS256");
		builder.setHeaderParam("typ", "JWT");
		// 30분
		builder.setExpiration(new Date(new Date().getTime() + 1000 * 60 * 30));

		// PAYLOAD:DATA = claim
		builder.claim("userid", userid);
		builder.claim("useremail", useremail);
		builder.claim("username", username);

		// VERIFY SIGNATURE
		String secretKey = "abc12345";
		builder.signWith(SignatureAlgorithm.HS256, secretKey.getBytes("UTF-8"));
		String jwt = builder.compact();
		logger.info("jwt: " + jwt);

		Cookie cookie = new Cookie("jwt", jwt);
		response.addCookie(cookie);

		return "redirect:/ch05/content";
	}

	@GetMapping("/getjwtcookie")
	public String getJwtCookie(@CookieValue String jwt) throws Exception {
		logger.info("실행");

		logger.info("jwt: " + jwt);

		JwtParser parser = Jwts.parser();
		String secretKey = "abc12345";
		parser.setSigningKey(secretKey.getBytes("UTF-8"));
		Jws<Claims> jws = parser.parseClaimsJws(jwt);
		Claims claims = jws.getBody();
		String userid = claims.get("userid", String.class);
		String useremail = claims.get("useremail", String.class);
		String username = claims.get("username", String.class);
		logger.info("userid: " + userid);
		logger.info("useremail: " + useremail);
		logger.info("username: " + username);

		return "redirect:/ch05/content";
	}
}
