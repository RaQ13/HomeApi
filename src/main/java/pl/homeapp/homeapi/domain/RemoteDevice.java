package pl.homeapp.homeapi.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class RemoteDevice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String brand;
    private String model;
    private String softwareVersion;
    private String type;

    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room;
}
