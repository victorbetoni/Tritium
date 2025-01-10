package net.victorbetoni.tritium.platform;

import net.victorbetoni.tritium.dto.common.DTO;

public record SingleBodyResponse<T extends DTO>(
        T body,
        String message,
        int status
) {
    public boolean ok() {
        return status == 200;
    }
}
