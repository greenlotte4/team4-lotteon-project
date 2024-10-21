package com.lotte4.repository;

import com.lotte4.entity.Terms;
import com.lotte4.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TermsRepository extends JpaRepository<Terms, Integer> {

    Terms findByTermsId(int termsId);

}
