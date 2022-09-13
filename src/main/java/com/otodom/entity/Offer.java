package com.otodom.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "offers")
public class Offer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToMany(cascade = CascadeType.DETACH)
    @JoinColumn(name = "offer_id", updatable = false, insertable = false)
    private List<Link> links;

    private LocalDateTime createdOn;

    @Column(name = "number_of_links")
    private Integer numberOfLinks;

}
