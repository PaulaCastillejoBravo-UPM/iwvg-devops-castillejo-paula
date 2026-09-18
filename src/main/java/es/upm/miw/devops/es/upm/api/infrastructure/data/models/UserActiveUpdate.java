package es.upm.miw.devops.es.upm.api.infrastructure.data.models;

import java.util.UUID;

public class UserActiveUpdate {
    private UUID id;
    private Boolean active;

    public UserActiveUpdate(UUID id, Boolean active) {
        this.id = id;
        this.active = active;
    }

    public UUID getId() { return id; }

    public Boolean getActive() { return active; }
}
