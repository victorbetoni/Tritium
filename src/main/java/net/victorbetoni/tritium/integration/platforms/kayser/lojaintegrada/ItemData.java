package net.victorbetoni.tritium.integration.platforms.kayser.lojaintegrada;

public record ItemData (
        String kayserId,
        int grupo,
        int lojaIntegradaId,
        String codigoDeBarra,
        String referencia
) {}
