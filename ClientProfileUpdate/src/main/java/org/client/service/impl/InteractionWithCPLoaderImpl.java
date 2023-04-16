package org.client.service.impl;


import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.client.dto.EmailUpdateDto;
import org.client.dto.IndividualUpdateDto;
//import org.client.dto.PhoneNumberUpdateDto;
import org.client.dto.PhoneNumberUpdateDto;
import org.client.service.InteractionWithCPLoader;
import org.client.util.ClientProfileLoaderException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@AllArgsConstructor
@Slf4j
public class InteractionWithCPLoaderImpl implements InteractionWithCPLoader {
    private static final String CP_LOADER = "http://localhost:9092/api/clients";
    private static final String CP_LOADER_Phone = "http://localhost:9092/api/phone";
    private static final String CP_LOADER_Email = "http://localhost:9092/api/email";
    private static final String CP_LOADER_RubWallet = "http://localhost:9092/api/rubWallet";
    private final RestTemplate restTemplate;

    public void updateIndividual(IndividualUpdateDto userUpdate) throws ClientProfileLoaderException {

        ResponseEntity<?> responseEntity = restTemplate.postForEntity(CP_LOADER, new HttpEntity<>(userUpdate), IndividualUpdateDto.class);

        if (responseEntity.getStatusCode() != HttpStatus.OK) {
            throw new ClientProfileLoaderException("Service Client Profile Loader is unavailable");
        }
    }

    @Override
    public void updatePhoneNumber(PhoneNumberUpdateDto phoneNumberUpdateDto) throws ClientProfileLoaderException {
        log.info("uuid " +phoneNumberUpdateDto.getUuid());

        ResponseEntity<?> responseEntity = restTemplate.postForEntity(CP_LOADER_Phone, new HttpEntity<>(phoneNumberUpdateDto), PhoneNumberUpdateDto.class);

        if (responseEntity.getStatusCode() != HttpStatus.OK) {
            throw new ClientProfileLoaderException("Service Client Profile Loader is unavailable");
        }

    }

    @Override
    public void updateEmail(EmailUpdateDto emailUpdateDto) throws ClientProfileLoaderException {
        ResponseEntity<EmailUpdateDto> responseEntity = restTemplate.postForEntity(CP_LOADER_Email, new HttpEntity<>(emailUpdateDto), EmailUpdateDto.class);

        if (responseEntity.getStatusCode() != HttpStatus.OK) {
            throw new ClientProfileLoaderException("Service Client Profile Loader is unavailable");
        }
    }




}
