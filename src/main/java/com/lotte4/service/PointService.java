package com.lotte4.service;

import com.lotte4.dto.PointDTO;
import com.lotte4.entity.User;
import com.lotte4.repository.PointRepository;
import com.lotte4.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
public class PointService {

    private final UserRepository userRepository;
    private final PointRepository pointRepository;
    private final ModelMapper modelMapper;

    public List<PointDTO> selectAllPoints() {
        List<PointDTO> points = pointRepository.findAll().stream().map(point -> {

            PointDTO pointDTO = modelMapper.map(point, PointDTO.class);

            // UID 설정
            String uid = userRepository.findByMemberInfo(point.getMemberInfo()).getUid();
            pointDTO.setUid(uid);

            return pointDTO;
        }).collect(Collectors.toList());

        return points;
    }
    public List<PointDTO> selectPointsByUid(String uid){

        User user = userRepository.findByUid(uid)
                .orElseThrow(() -> new NoSuchElementException("User not found with uid: " + uid));

        return  pointRepository.findPointsByMemberInfo(user.getMemberInfo()).stream().map((element) ->
                        modelMapper.map(element, PointDTO.class))
                .collect(Collectors.toList());
    }


}
