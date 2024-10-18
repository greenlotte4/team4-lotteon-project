package com.lotte4.repository;

import com.lotte4.entity.InfoFooter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InfoFooterRepository extends JpaRepository<InfoFooter,Integer> {


}
