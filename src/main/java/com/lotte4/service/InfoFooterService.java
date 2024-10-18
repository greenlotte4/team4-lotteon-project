package com.lotte4.service;

import com.lotte4.repository.InfoFooterRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
public class InfoFooterService {

    private final InfoFooterRepository infoFooterRepository;
}
