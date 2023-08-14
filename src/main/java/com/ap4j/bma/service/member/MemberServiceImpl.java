package com.ap4j.bma.service.member;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class MemberServiceImpl implements MemberService{


	// 아래는 예시 코드입니다.
	@Override
	@Transactional // 트랜잭션 처리하기
	public void addSomething(String something) {

	}
}
