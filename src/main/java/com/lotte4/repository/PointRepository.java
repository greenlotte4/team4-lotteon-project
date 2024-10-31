package com.lotte4.repository;

import com.lotte4.entity.MemberInfo;
import com.lotte4.entity.Point;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PointRepository extends JpaRepository<Point,Integer> {

    List<Point> findPointsByMemberInfo(MemberInfo memberInfo);

    List<Point> findAll();
}
