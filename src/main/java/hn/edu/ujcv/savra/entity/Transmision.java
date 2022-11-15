package hn.edu.ujcv.savra.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "transmision")
public class Transmision {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idTransmision;
    private String nombre;
}
