package org.client.repository.Wallet;

import org.client.common.entity.WalletMedium;
import org.client.common.entity.Wallets.EuroWallet;
import org.client.common.entity.Wallets.UsdWallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UsdWalletRepository  extends JpaRepository<UsdWallet, String> {

    //find usdWallet by client icp
    @Query(value = "from UsdWallet as usd join fetch usd.walletMedium as wallmed join fetch wallmed.individual as indiv where indiv.icp = :icp")
    UsdWallet findUsdWallByClientIcp(String icp);

}
