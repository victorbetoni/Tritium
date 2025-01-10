package net.victorbetoni.tritium.platform.erp;

import net.victorbetoni.tritium.platform.Platform;
import net.victorbetoni.tritium.platform.SimpleResponse;
import net.victorbetoni.tritium.dto.common.ICustomerDTO;
import net.victorbetoni.tritium.dto.common.IItemDTO;
import net.victorbetoni.tritium.dto.common.IPaymentDTO;
import net.victorbetoni.tritium.dto.marketplace.IOrderDTO;

public interface IERPPlatform<
        IDENTIFIER_TYPE,
        A extends IPaymentDTO,
        B extends ICustomerDTO,
        D extends IItemDTO,
        G extends IOrderDTO> extends Platform {

    String id();

    SimpleResponse createOrder(G order);
}
