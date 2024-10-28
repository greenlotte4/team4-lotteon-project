package com.lotte4.service.board;

import com.lotte4.dto.BoardCateDTO;
import com.lotte4.dto.BoardCommentDTO;
import com.lotte4.dto.BoardRegisterDTO;
import com.lotte4.dto.BoardResponseDTO;
import com.lotte4.entity.Board;
import com.lotte4.entity.BoardCate;
import com.lotte4.entity.ProductCate;
import com.lotte4.entity.User;
import com.lotte4.repository.UserRepository;
import com.lotte4.repository.board.BoardCateRepository;
import com.lotte4.repository.board.BoardRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Log4j2
@RequiredArgsConstructor
@Service
public class BoardService {
    private final UserRepository userRepository;
    private final BoardRepository boardRepository;
    private final BoardCateRepository boardCateRepository;
    private final ModelMapper modelMapper;
    // 타입별로 찾는 메서드
    public Page<BoardResponseDTO> selectAllBoardByType(String type, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Board> boardEntities = boardRepository.findByType(type, pageable);
        return boardEntities.map(board -> modelMapper.map(board, BoardResponseDTO.class));
    }
    // 카테고리 아이디로 찾는 메서드
    public Page<BoardResponseDTO> selectAllBoardByCateId(int cateId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Board> boardEntities;

        Optional<BoardCate> category = boardCateRepository.findById(cateId);
        if (category.isPresent()) {
            if (category.get().getParent() == null) {
                boardEntities = boardRepository.findByCate_Parent_BoardCateId(cateId, pageable);
            } else {
                boardEntities = boardRepository.findByCate_BoardCateId(cateId, pageable);
            }
        } else {
            // 예외 처리: 카테고리가 존재하지 않을 경우
            throw new IllegalArgumentException("Invalid category ID: " + cateId);
        }

        return boardEntities.map(board -> modelMapper.map(board, BoardResponseDTO.class));
    }


    public Board insertBoard(BoardRegisterDTO dto) {
        log.info("insert board qna:" +dto);
        return userRepository.findByUid(dto.getUid())
                .map(user -> {
                    Board board = modelMapper.map(dto, Board.class);
                    board.setUser(user);

                    BoardCate category = boardCateRepository.findById(dto.getCate())
                            .orElseThrow(() -> new NoSuchElementException("Category not found"));
                    board.setCate(category);
                    log.info("BoardService insert board : "+board);
                    return boardRepository.save(board);
                })
                .orElseThrow(() -> new NoSuchElementException("User not found"));
    }

    @Transactional
    public Board updateBoard(BoardRegisterDTO dto) {
        Board existingboard = boardRepository.findById(dto.getBoardId())
                .orElseThrow(() -> new EntityNotFoundException("해당 ID의 게시판을 찾을 수 없습니다: " + dto.getBoardId()));
        if(dto.getCate() != existingboard.getCate().getBoardCateId()){
            existingboard.setCate(boardCateRepository.findByBoardCateId(dto.getCate()));
        }
        if(dto.getTitle() != existingboard.getTitle()){
            existingboard.setTitle(dto.getTitle());
        }
        if(dto.getContent() != existingboard.getContent()){
            existingboard.setContent(dto.getContent());
        }
        return boardRepository.save(existingboard);
    }

    public BoardResponseDTO selectBoardById(int id) {
        return boardRepository.findById(id)
                .map(board -> modelMapper.map(board, BoardResponseDTO.class))
                .orElse(null);
    }

    public void insertBoardQnaComment(BoardCommentDTO commentDTO) {
        Optional<Board> existingBoard =boardRepository.findById(commentDTO.getBoardId());
        if(existingBoard.isPresent()){
            Board board = existingBoard.get();
            board.setComment(commentDTO.getComment());
            board.setState(1); // 답변완료로 상태 수정
            boardRepository.save(board);
        }
    }

    public boolean deleteBoardByBoardId(int boardId) {
        try {
            boardRepository.deleteById(boardId);
            return true; // 성공적으로 삭제된 경우
        } catch (EmptyResultDataAccessException e) {
            // 존재하지 않는 boardId로 삭제 시도 시 예외 발생
            System.out.println("해당 ID의 게시글이 존재하지 않습니다: " + boardId);
            return false; // 삭제 실패
        } catch (Exception e) {
            // 다른 예외 처리
            System.out.println("삭제 중 오류가 발생했습니다: " + e.getMessage());
            return false; // 삭제 실패
        }
    }

}
