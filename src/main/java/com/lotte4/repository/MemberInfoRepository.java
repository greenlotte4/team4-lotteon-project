package com.lotte4.repository;

import com.lotte4.entity.MemberInfo;
import com.lotte4.entity.SellerInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberInfoRepository extends JpaRepository<MemberInfo, Integer> {

    // 중복확인
    int countByEmail(String email);
    int countByHp(String hp);

    // 나의설정 정보수정
    @Modifying
    @Query("UPDATE MemberInfo m SET m.email = :email, m.hp = :hp, " +
            "m.address.zipCode = :zipCode, m.address.addr1 = :addr1, m.address.addr2 = :addr2 " +
            "WHERE m.memberInfoId = :memberInfoId")
    int updateMemberInfo(
            @Param("email") String email,
            @Param("hp") String hp,
            @Param("zipCode") String zipCode,
            @Param("addr1") String addr1,
            @Param("addr2") String addr2,
            @Param("memberInfoId") int memberInfoId
    );



}
