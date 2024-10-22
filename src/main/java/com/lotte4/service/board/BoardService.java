package com.lotte4.service.board;

import com.lotte4.dto.BoardCateDTO;
import com.lotte4.dto.BoardRegisterDTO;
import com.lotte4.dto.BoardResponseDTO;
import com.lotte4.entity.Board;
import com.lotte4.entity.BoardCate;
import com.lotte4.entity.ProductCate;
import com.lotte4.entity.User;
import com.lotte4.helper.BoardCategory;
import com.lotte4.repository.UserRepository;
import com.lotte4.repository.board.BoardCateRepository;
import com.lotte4.repository.board.BoardRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

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

    // TODO: boardCate 들어가는 거 수정해야함
    public Board insertBoardQna(BoardRegisterDTO dto) {
        log.info("insert board qna:" +dto);
        return userRepository.findByUid(dto.getUid())
                .map(user -> {
                    Board board = modelMapper.map(dto, Board.class);
                    board.setUser(user);

//                    BoardCate category = boardCateRepository.findById(dto.getType())
//                            .orElseThrow(() -> new NoSuchElementException("Category not found"));
//                    board.setBoardCate(category);

                    return boardRepository.save(board);
                })
                .orElseThrow(() -> new NoSuchElementException("User not found"));
    }

}
