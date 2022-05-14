package kz.narxoz.springSecurity.narxozsecurity.narxozsecurity.model;

import javax.persistence.*;

@Entity
@Table
public class Img {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "image")
    private String image;
}
