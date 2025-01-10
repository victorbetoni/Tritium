package net.victorbetoni.tritium.exception;

public class EntityNotMappedException extends RuntimeException {
    public EntityNotMappedException(String message) {
        super(message);
    }
}
