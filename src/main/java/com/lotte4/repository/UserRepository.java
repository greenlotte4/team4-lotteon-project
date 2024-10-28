package com.lotte4.repository;

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
*/
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    // uid로 정보 조회
    Optional<User> findByUid(String uid);

    // 아이디 중복확인
    int countByUid(String uid);

    // role이 member인 회원 목록 select(관리자 회원목록을 위함)
    Page<User> findByRole(String role, Pageable pageable);

    // role이 'member'인 사용자 수를 반환하는 메서드
    int countByRole(String role);

    // role이 member이고, uid 검색하는 메서드
    Page<User> findByRoleAndUidContaining(String role, String uid, Pageable pageable); // 아이디 검색

    // role이 member이고, name 검색하는 메서드
    Page<User> findByRoleAndMemberInfoNameContaining(String role, String name, Pageable pageable); // 이름 검색

    // role이 member이고, email 검색하는 메서드
    Page<User> findByRoleAndMemberInfoEmailContaining(String role, String email, Pageable pageable); // 이메일 검색

    // role이 member이고, hp 검색하는 메서드
    Page<User> findByRoleAndMemberInfoHpContaining(String role, String hp, Pageable pageable); // 휴대폰 검색
}
