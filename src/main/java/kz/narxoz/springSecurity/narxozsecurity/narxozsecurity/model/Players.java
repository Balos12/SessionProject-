package kz.narxoz.springSecurity.narxozsecurity.narxozsecurity.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "t_players")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Players {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "student_name")
    private String name;

    @Column(name = "student_surname")
    private String surname;

    @Column(name = "birthdate")
    private String date;

    @ManyToOne(fetch = FetchType.EAGER)
    private Position position;

    @ManyToOne(fetch = FetchType.EAGER)
    private Img image;


}
