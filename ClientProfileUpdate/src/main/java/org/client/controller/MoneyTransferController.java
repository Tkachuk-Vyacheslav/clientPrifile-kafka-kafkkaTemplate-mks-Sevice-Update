package org.client.controller;

import lombok.extern.slf4j.Slf4j;
import org.client.common.dto.MoneyTransferDto;
import org.client.common.dto.TransferResultDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.concurrent.ExecutionException;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;
import org.springframework.kafka.requestreply.RequestReplyFuture;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequestMapping("transfer")
@RestController  // @RequestMapping("transfer")
public class MoneyTransferController {

    @Value("${kafka.reuest.topic}")
    private String requestTopic;   //@RequestParam("petId") int petId  ,  @PathVariable(value="icpFromParam") String icpFromParam

    @Autowired
    private ReplyingKafkaTemplate<String, MoneyTransferDto, TransferResultDto> replyingKafkaTemplate; // Для того, чтобы отправлять сообщения, нам потребуется объект KafkaTemplate<K, V>


    @PostMapping()
    public ResponseEntity<TransferResultDto> getObject( @RequestBody MoneyTransferDto moneyTransferDto, @RequestParam("icpFromParam") String icpFromParam)
            throws InterruptedException, ExecutionException {
        // evnt == MoneyTransfer ?
        if (!moneyTransferDto.getEvent().equals("MoneyTransfer")) {
            TransferResultDto res = new TransferResultDto();
            res.setTransactionResult("the transaction failed");
            res.setDetails("event must be equals MoneyTransfer");
            res.setTransactionName("MoneyTransfer");
            return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
            // icp from dto == icp from parameters?
        } else if (!moneyTransferDto.getIcp().equals(icpFromParam)) {
            TransferResultDto res = new TransferResultDto();
            res.setTransactionResult("the transaction failed");
            res.setDetails("icpFromParam must be equals moneyTransferDto icp");
            res.setTransactionName("MoneyTransfer");
            return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
        } else { // transaction is successful?
            ProducerRecord<String, MoneyTransferDto> record = new ProducerRecord<>(requestTopic, null, moneyTransferDto.getIcp(), moneyTransferDto);
            log.info("111 ProducerRecord");
            RequestReplyFuture<String, MoneyTransferDto, TransferResultDto> future = replyingKafkaTemplate.sendAndReceive(record);
            log.info("222 RequestReplyFuture");
            ConsumerRecord<String, TransferResultDto> response = future.get();
            log.info("333 ConsumerRecord finish response");
            if(response.value().getTransactionResult().equals("the transaction was successful")) {
                log.info("### successful");
                return new ResponseEntity<>(response.value(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(response.value(), HttpStatus.BAD_REQUEST);
            }
        }
    }


}