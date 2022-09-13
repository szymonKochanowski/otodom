package com.otodom.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "offers")
public class Offer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

//    @NotNull
//    @NotBlank
    @OneToMany(cascade = CascadeType.DETACH)
    @JoinColumn(name = "offer_id", updatable = false, insertable = false)
    private List<Link> links;

    private LocalDateTime createdOn;

    @Column(name = "number_of_links")
    private Integer numberOfLinks;



}
