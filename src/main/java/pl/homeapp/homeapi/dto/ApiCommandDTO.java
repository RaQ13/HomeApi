package pl.homeapp.homeapi.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public class ApiCommandDTO {
    @Schema(description = "ID urządzenia zdalnego", example = "10", required = true)
    private Long remoteDeviceId;

    @Schema(description = "Treść komendy", example = "setState: {power: on}", required = true)
    private String body;

    public Long getRemoteDeviceId() {
        return remoteDeviceId;
    }

    public void setRemoteDeviceId(Long remoteDeviceId) {
        this.remoteDeviceId = remoteDeviceId;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}