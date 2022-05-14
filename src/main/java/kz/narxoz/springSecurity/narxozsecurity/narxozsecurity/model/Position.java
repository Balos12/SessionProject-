package kz.narxoz.springSecurity.narxozsecurity.narxozsecurity.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "t_position")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Position {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "position")
    private String position ;




}
