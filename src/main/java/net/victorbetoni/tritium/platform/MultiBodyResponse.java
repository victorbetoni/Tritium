package net.victorbetoni.tritium.platform;

import net.victorbetoni.tritium.dto.common.DTO;

import java.util.List;

public record MultiBodyResponse<T extends DTO>(
        List<T> body,
        String message,
        int status
) {
    public boolean ok() {
        return status == 200;
    }
}
