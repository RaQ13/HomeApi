package pl.homeapp.homeapi.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Map;

public class ApiCommandDTO {
//    @Schema(description = "ID urządzenia zdalnego", example = "10", required = true)
    private Long remoteDeviceId;

//    @Schema(description = "Treść komendy", example = "setState: {power: on}", required = true)
    private Map<String, Object> body;

    public Long getRemoteDeviceId() {
        return remoteDeviceId;
    }

    public void setRemoteDeviceId(Long remoteDeviceId) {
        this.remoteDeviceId = remoteDeviceId;
    }

    public Map<String, Object> getBody() {
        return body;
    }

    public void setBody(Map<String, Object> body) {
        this.body = body;
    }
}