package com.ap4j.bma.model.repository;

import com.ap4j.bma.model.entity.meamulReg.MaemulRegEntity;
import com.ap4j.bma.model.entity.recent.RecentEntity;
import com.ap4j.bma.model.entity.meamulReg.MaemulRegEntity;
import com.ap4j.bma.model.entity.member.MemberEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RecentRepository extends JpaRepository<RecentEntity, String> {

	/** 최근 본 매물 유저 중복체크 확인 */
	boolean existsByMaemulEntityAndMemberEntity_Nickname(MaemulRegEntity maemulEntity, String nickname);

	@Query("SELECT r.maemulEntity FROM RecentEntity r WHERE r.memberEntity.nickname = :nickname")
	Page<MaemulRegEntity> findMaemulEntitiesByMemberNickname(String nickname, Pageable pageable);

	Long countByMemberEntity_Nickname(String nickname);

	@Query("SELECT r.maemulEntity FROM RecentEntity r WHERE r.memberEntity.nickname = :nickname " +
			"AND (r.maemulEntity.address LIKE %:keyword% OR r.maemulEntity.tradeType LIKE %:keyword%)")
	Page<MaemulRegEntity> findMaemulEntitiesByMemberNicknameAndKeyword(String nickname, String keyword, Pageable pageable);

	Long countByMemberEntity_NicknameAndMaemulEntity_AddressContainingOrMaemulEntity_TradeTypeContaining(
			String nickname,String addressKeyword, String tradeTypeKeyword);

}
