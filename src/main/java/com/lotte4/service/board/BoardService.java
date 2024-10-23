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
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
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
    //ProductCate productCate = modelMapper.map(productCateDTO, ProductCate.class);
    public List<BoardResponseDTO> selectAllBoardByType(String type) {

        List<Board> boardEntities = boardRepository.findByType(type);
        List<BoardResponseDTO> boardResponseDTOS = new ArrayList<>();
        for (Board board : boardEntities) {
            BoardResponseDTO boardResponseDTO = modelMapper.map(board, BoardResponseDTO.class);
            boardResponseDTOS.add(boardResponseDTO);
        }
        return boardResponseDTOS;
    }

    public Board insertBoardQna(BoardRegisterDTO dto) {
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

    public BoardResponseDTO selectBoardById(int id){
        Optional<Board> optBoard = boardRepository.findById(id);
        if(optBoard.isPresent()){
            Board board = optBoard.get();
            BoardResponseDTO boardDTO = modelMapper.map(board, BoardResponseDTO.class);
            return boardDTO;
        }
        return null;
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
}