package org.client.service;

import org.client.common.dto.AddressDto;
import org.client.common.dto.IndividualDto;
import org.client.common.dto.MoneyTransferDto;
import org.client.common.dto.WalletDto;
import org.client.common.dto.Wallets.RubWalletDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public interface WalletService {

    void addWalletForClient(WalletDto dto, String individualIcp, String icpFromParam) throws Exception;

    List<Object> getWalletByIcp(String icp) throws Exception;

    void editWallet(WalletDto dto, String icpFromParam) throws Exception;

    void deleteWallet(IndividualDto dto, String uuidFromParam) throws Exception;

    String moneyTransfer(MoneyTransferDto dto);
}
