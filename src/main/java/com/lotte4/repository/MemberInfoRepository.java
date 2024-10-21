package com.lotte4.repository;

import com.lotte4.entity.MemberInfo;
import com.lotte4.entity.SellerInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberInfoRepository extends JpaRepository<MemberInfo, Integer> {






}
