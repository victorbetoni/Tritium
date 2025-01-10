package net.victorbetoni.tritium.integration.factory;

import net.victorbetoni.tritium.integration.Integration;
import net.victorbetoni.tritium.integration.platforms.kayser.lojaintegrada.KayserLojaIntegrada;

public class IntegrationFactory {

    public static Integration<?,?,?> createIntegrationById(String id) {
        switch (id) {
            case "kayser-lojaintegrada":
                return new KayserLojaIntegrada(KayserLojaIntegrada.ID);
            default:
                return null;
        }
    }

}
