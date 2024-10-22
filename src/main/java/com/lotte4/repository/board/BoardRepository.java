package com.lotte4.repository.board;

import com.lotte4.entity.Board;
import com.lotte4.entity.BoardCate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

//import java.util.List;

@Repository
public interface BoardRepository extends JpaRepository<Board, Integer> {
    List<Board> findByType(String type);
}
