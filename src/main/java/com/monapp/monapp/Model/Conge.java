package com.monapp.monapp.Model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="conge")
public class Conge {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Enumerated(EnumType.STRING)
    private Type type;

    @Column(name = "date_dem")
    private Date date_demande;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @Column(name = "date_deb")
    private Date date_debut;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @Column(name = "date_fin")
    private Date date_fin;

    @Column(name = "durée")
    private int duree;

    @Column(name = "statut")  //en_attente /acceptee/refusee
    private String statut;

    @Column(name = "solde")
    private int solde;

    @Column(name = "motif")
    private String motif;

    @Column(name = "file")
    private String file;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;


    public Conge(int duree, Type type, String motif, Date dateDebut, Date dateFin, String statut, User user, int solde) {
        this.duree=duree;
        this.type=type;
        this.date_debut=dateDebut;
        this.date_fin=dateFin;
        this.motif=motif;
        this.statut=statut;
        this.user=user;
        this.solde=solde;

    }
}