package com.lotte4.dto;

import com.lotte4.entity.Address;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddressDTO {

    private String zipCode;
    private String addr1;
    private String addr2;


    }
