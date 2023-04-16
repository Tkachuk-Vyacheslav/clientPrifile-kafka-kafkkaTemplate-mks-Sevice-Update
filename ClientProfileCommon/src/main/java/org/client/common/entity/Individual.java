package org.client.common.entity;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;


@Data
@Entity
@Table
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Individual {

    @Id @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    private String uuid;

    private String icp;
    private String name;
    private String surname;
    private String patronymic;
    private String fullName;
    private String gender;
    private String placeOfBirth;
    private String countryOfBirth;
    private Date birthDate;

    private boolean isArchived = false;

    private String actualIcp = icp;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "documentID")
    private Documents documents;

    @OneToMany(mappedBy = "individual", cascade = CascadeType.ALL)
    private Collection<RFPassport> passport;

    //Двусторонний OneToOne
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "contactID")
    private ContactMedium contacts;

    //двусторонний  @OneToOne
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "walletID")
    private WalletMedium wallets;

    @ManyToOne
    @JoinColumn(name = "avatar_uuid")
    private Avatar avatar;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name="individ_address",
            joinColumns=  @JoinColumn(name="individ_uuid", referencedColumnName="uuid"),
            inverseJoinColumns= @JoinColumn(name="address_uuid", referencedColumnName="uuid") )
    private Collection<Address> addresses;

}
