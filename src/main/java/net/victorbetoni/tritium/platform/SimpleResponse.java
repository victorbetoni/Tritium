package net.victorbetoni.tritium.platform;

public record SimpleResponse (
        String message,
        int status
) {
    public boolean ok() {
        return status == 200;
    }
}
