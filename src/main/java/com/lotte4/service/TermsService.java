package com.lotte4.service;

import com.lotte4.dto.TermsDTO;
import com.lotte4.dto.UserDTO;
import com.lotte4.entity.MemberInfo;
import com.lotte4.entity.SellerInfo;
import com.lotte4.entity.Terms;
import com.lotte4.entity.User;
import com.lotte4.repository.TermsRepository;
import com.lotte4.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Log4j2
@RequiredArgsConstructor
@Service
public class TermsService {

    private final TermsRepository termsRepository;
    private final ModelMapper modelMapper;

    public TermsDTO selectTerms() {

        // entity를 dto로 변환
        return modelMapper.map(termsRepository.findByTermsId(1), TermsDTO.class);
    }



}
