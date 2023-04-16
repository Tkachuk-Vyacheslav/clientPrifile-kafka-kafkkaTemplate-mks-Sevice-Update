package org.client.common.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.client.common.entity.Wallets.EuroWallet;
import org.client.common.entity.Wallets.RubWallet;
import org.client.common.entity.Wallets.UsdWallet;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Data
@Entity
@Table
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WalletMedium {
    @Id // UUID.randomUUID().toString().replaceAll("-", ""); //strips '-'
    private String uuid;

    //Двусторонний OneToOne
    @OneToOne(mappedBy = "wallets", cascade = CascadeType.ALL)
    private Individual individual;

    //двусторонний  @OneToOne
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "rubWalletID")
    private RubWallet rubWallets;

    //двусторонний  @OneToOne
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "euroWalletID")
    private EuroWallet euroWallets;

    //двусторонний  @OneToOne
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "usdWalletID")
    private UsdWallet usdWallets;


}
