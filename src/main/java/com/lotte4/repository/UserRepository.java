package com.lotte4.repository;

import com.lotte4.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    // uid로 정보 조회
    Optional<User> findByUid(String uid);

    // 아이디 중복확인
    int countByUid(String uid);

    // role이 member인 회원 목록 select(관리자 회원목록을 위함)
    Optional<List<User>> findByRole(String role);


}
