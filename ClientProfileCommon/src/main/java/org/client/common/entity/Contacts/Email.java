package org.client.common.entity.Contacts;

import lombok.*;
import org.client.common.entity.ContactMedium;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.UUID;

@Data
@Entity
@Table
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Email {

    @Id @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    private String uuid;

    private String value;

    private Boolean verification;

//    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//    @JoinColumn(name = "contactmedium_id")
//    @ToString.Exclude
//    private ContactMedium contactMedium;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "contactmedium_id")
    @ToString.Exclude
    private ContactMedium contactMedium;

    // verification status
    // . email have to get verefication in CP_Notification

}
