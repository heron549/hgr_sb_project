package com.hgr.project.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.hgr.project.entity.SnsUser;
import com.hgr.project.exception.DataNotFoundException;
import com.hgr.project.repository.UserRepository;

import lombok.RequiredArgsConstructor;


// 차후 완성 현재 snsuser 엔티티 , 레포지토리 까지만 작성됨
@RequiredArgsConstructor
@Service
public class UserService {
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	
	public SnsUser create(String username, String password, String email) {
		SnsUser user = new SnsUser();
		user.setUsername(username);
		user.setPassword( this.passwordEncoder.encode(password) );
		user.setEmail(email);
		// FIXME #REFACT: 회원 가입 시간 추가
		user.setRegDate(LocalDateTime.now());
		System.out.println("디비저장");
		try {
			this.userRepository.save(user);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}	
		System.out.println("반환");
		return user;
	}
	public SnsUser findById(Long userId) {
		Optional<SnsUser> oUser = this.userRepository.findById(userId);
		if(oUser.isPresent()) {
			return oUser.get();
		}
		throw new DataNotFoundException("user not found");
		// OR
        //return userRepository.findById(userId)
        //        .orElseThrow(() -> new DataNotFoundException("user not found"));
    }

}
