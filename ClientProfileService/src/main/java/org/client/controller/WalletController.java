package org.client.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.client.common.dto.IndividualDto;
import org.client.common.dto.WalletDto;
import org.client.common.dto.Wallets.RubWalletDto;
import org.client.service.WalletService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/wallet")
public class WalletController {

    private final WalletService walletService;

    public WalletController(WalletService walletService){this.walletService = walletService;}

    @PostMapping("/create/")
    @Operation(summary = "Создание нового кошелька и его привязка к пользователю по icp пользователя")
    public ResponseEntity<String> createWallet(@RequestBody WalletDto dto, @RequestParam("icp") String icp) throws Exception {
        walletService.addWalletForClient(dto, dto.getIndividualIcp(), icp);
        return ResponseEntity.ok("Wallet  was created successfully!");
    }

    @GetMapping("/getWalletByClientIcp/")
    @Operation(summary = "Информация о кошельке по icp клиента")
    public ResponseEntity<List<Object>> getWalletByClientIcp(@Parameter(description = "icp") String Icp,
                                                             @RequestParam("icp") String icp) throws Exception {
        return new ResponseEntity<>(walletService.getWalletByIcp(icp), HttpStatus.OK);
    }

    @PutMapping("/edit/")
    @Operation(summary = "редактирование кошелька по client icp")
    public ResponseEntity<String> editWalletByIcpClient(@Valid @RequestBody WalletDto dto, @RequestParam("icp") String icp) throws Exception {

      walletService.editWallet(dto , icp);
        return ResponseEntity.ok("wallet  was updated successfully!");
    }

    @DeleteMapping("/delete/")  //post запрос с clientIcp в  теле
    @Operation(summary = "удаление кошелька по icp client")
    public ResponseEntity<String> deleteWalletByIcp(@RequestBody IndividualDto dto, @RequestParam("icp") String icp) throws Exception {

        walletService.deleteWallet(dto, icp);
        return ResponseEntity.ok("wallet  was deleted !");
    }


}
