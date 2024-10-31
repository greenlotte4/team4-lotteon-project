package com.lotte4.repository;

import com.lotte4.entity.MemberInfo;
import com.lotte4.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
/*
     날짜 : 2024/10/28
     이름 : 강은경
     내용 : UserRepository 생성

     수정이력
      - 2024/10/28 강은경 - 관리자 회원목록 기능 검색&페이징 메서드 추가
      - 2024/10/30 황수빈 - 포인트 조회 시 memberinfo로 uid를 찾아야함으로 메서드 추가
      - 2024/10/31 강은경 - 이름과 이메일로 아이디 조회하는 메서드 추가
*/
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    User findByMemberInfo(MemberInfo memberInfo);


    // uid로 정보 조회
    Optional<User> findByUid(String uid);

    // 아이디 중복확인
    int countByUid(String uid);

    // role이 member인 회원 목록 select(관리자 회원목록을 위함)
    Page<User> findByRole(String role, Pageable pageable);

    // role이 member이고, keyword 검색하는 메서드 (모든 필드에서 검색)
    Page<User> findByRoleAndUidContainingOrMemberInfoNameContainingOrMemberInfoEmailContainingOrMemberInfoHpContaining(
            String role, String uid, String name, String email, String hp, Pageable pageable);


    // 이름과 이메일로 아이디 조회
    Optional<User> findByMemberInfo_nameAndMemberInfo_email(String memberInfo_name, String memberInfo_email);

}
