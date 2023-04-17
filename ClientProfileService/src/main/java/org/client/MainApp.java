package org.client;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.client.Exception.NotEnoughMoneyException;
import org.client.common.dto.MoneyTransferDto;
import org.client.common.dto.TransferResultDto;
import org.client.service.WalletService;
import org.client.service.impl.WalletServiceImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
/**
 * ClientProfileService!
 *
 */
@SpringBootApplication
@EnableWebMvc
@Slf4j
@EnableKafka
public class MainApp {

    private final WalletService walletService;
    public MainApp(WalletService walletService) {
        this.walletService = walletService;
    }

    @KafkaListener(topics = "${kafka.reuest.topic}", groupId = "app.1")
    @SendTo
    public TransferResultDto handle(MoneyTransferDto moneyTransferDto) {

       String total =  walletService.moneyTransfer(moneyTransferDto);

        TransferResultDto transferResultDto= new TransferResultDto();
        transferResultDto.setTransactionName("MoneyTransfer");

        if(total.equals("the transaction was successful")) {
            transferResultDto.setTransactionResult("the transaction was successful");
            transferResultDto.setDetails("all is ok!");
            System.out.println("transaction was successful" +"###");
        } else if (total.equals(" не достаточно средств для перевода")) {
            transferResultDto.setTransactionResult(" the transaction failed");
            transferResultDto.setDetails("не достаточно средств для перевода");
        } else if (total.equals("  не найден отправитель средств c таким icp")) {
            transferResultDto.setTransactionResult(" the transaction failed");
            transferResultDto.setDetails("  не найден отправитель средств c таким icp");
        } else if (total.equals("найдено несколько клиентов с таким номером тлф")) {
            transferResultDto.setTransactionResult(" the transaction failed");
            transferResultDto.setDetails("найдено несколько клиентов с таким номером тлф");
        } else if (total.equals("не найден клиент с таким номером тлф")) {
            transferResultDto.setTransactionResult(" the transaction failed");
            transferResultDto.setDetails("не найден клиент с таким номером тлф");
        } else if (total.equals("возникли проблемы с подключением к БД")) {
            transferResultDto.setTransactionResult(" the transaction failed");
            transferResultDto.setDetails("возникли проблемы с подключением к БД");
        } else if (total.equals("неправильный формат номера телефона")) {
            transferResultDto.setTransactionResult(" the transaction failed");
            transferResultDto.setDetails("неправильный формат номера телефона");
        } else if (total.equals("сумма для перевода не может быть ноль или отрицательной")) {
            transferResultDto.setTransactionResult(" the transaction failed");
            transferResultDto.setDetails("сумма для перевода не может быть ноль или отрицательной");
        } else if (total.equals("Валюта должна быть: EURO, RUB, USD")) {
            transferResultDto.setTransactionResult(" the transaction failed");
            transferResultDto.setDetails("Валюта указана неправильно! Валюта должна быть: EURO, RUB, USD");
        }
        return transferResultDto;
    }

    public static void main(String[] args) {
        log.debug("starting ClientProfileService");
        SpringApplication.run(MainApp.class, args);
    }
}
