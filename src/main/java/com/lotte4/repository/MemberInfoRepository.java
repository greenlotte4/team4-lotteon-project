package com.lotte4.repository;

import com.lotte4.entity.MemberInfo;
import com.lotte4.entity.SellerInfo;
import com.lotte4.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberInfoRepository extends JpaRepository<MemberInfo, Integer> {

    // 중복확인
    int countByEmail(String email);
    int countByHp(String hp);

    // memberInfoId로 정보 조회
    Optional<MemberInfo> findByMemberInfoId(int memberInfoId);

}
