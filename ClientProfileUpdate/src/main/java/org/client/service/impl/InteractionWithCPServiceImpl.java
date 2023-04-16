package org.client.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.client.common.dto.WalletDto;
import org.client.dto.EmailUpdateDto;
import org.client.dto.IndividualUpdateDto;

import org.client.dto.PhoneNumberUpdateDto;
import org.client.service.InteractionWithCPService;
import org.client.util.UpdateIndividualException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@AllArgsConstructor
@Slf4j
public class InteractionWithCPServiceImpl implements InteractionWithCPService {

    private static final String CP_SERVICE = "http://localhost:8080/individual";
    private static final String CP_SERVICE_Phone = "http://localhost:8080/contacts";
    private static final String CP_SERVICE_Email = "http://localhost:8080/contacts/email";
    private static final String CP_SERVICE_RubWallet = "http://localhost:8080/wallet";


    private final RestTemplate restTemplate;

    public void findIndividual(String icp, IndividualUpdateDto userUpdate) throws UpdateIndividualException {

        ResponseEntity<?> responseEntity = restTemplate.getForEntity(CP_SERVICE + "/get?icp=" + icp, IndividualUpdateDto.class);
        var userFromDB = responseEntity.getBody();
        if (userFromDB == null) {
            throw new UpdateIndividualException("There is no user with this ICP");
        }

        if (userFromDB.equals(userUpdate)) {
            throw new UpdateIndividualException("User already has valid fields");
        }
    }

    @Override
    public void findPhoneNumberByIcp(String uuid, PhoneNumberUpdateDto phoneNumberUpdateDto) throws UpdateIndividualException {

        ResponseEntity<?>responseEntity = restTemplate.getForEntity(CP_SERVICE_Phone+ "/get/"+uuid, PhoneNumberUpdateDto.class);
        var PhoneNumberFromDB=responseEntity.getBody();
        log.info(PhoneNumberFromDB.toString()+PhoneNumberFromDB.getClass());
        if(PhoneNumberFromDB==null) {
            throw new UpdateIndividualException("User has no active phone Number");
        }
        if(PhoneNumberFromDB.equals(phoneNumberUpdateDto)){
            throw new UpdateIndividualException("phone is already have this value");
        }
    }

    @Override
    public void findEmailByUuid(String uuid, EmailUpdateDto emailUpdateDto) throws UpdateIndividualException {
        ResponseEntity<?>responseEntity = restTemplate.getForEntity(CP_SERVICE_Email+ "/get/"+uuid, EmailUpdateDto.class);
        var PhoneNumberFromDB=responseEntity.getBody();
        if(PhoneNumberFromDB==null) {
            throw new UpdateIndividualException("User has no active phone Email");
        }
        if(PhoneNumberFromDB.equals(emailUpdateDto)){
            throw new UpdateIndividualException("email is already have this value");
        }

    }




}
