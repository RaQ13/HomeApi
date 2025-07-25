package pl.homeapp.homeapi.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class RemoteDevice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Unique identifier of remotedevice connected to home local network", example = "1", required = true)
    private Long id;
    @Schema(description = "Brand of remote device", example = "WiZ", required = true)
    private String brand;
    @Schema(description = "Ip of remote device", example = "192.168.1.7", required = true)
    private String ip;
    @Schema(description = "MAC address of remote device", example = "AB:01:CD:02:EF:03", required = true)
    private String mac;
    @Schema(description = "Model of remote device", example = "candle", required = true)
    private String model;
    @Schema(description = "Software version of remote device", example = "1", required = false)
    private String softwareVersion;
    @Schema(description = "Unique identifier of remote device connected to home local network", example = "bulb", required = true)
    private String type;

    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room;
}
