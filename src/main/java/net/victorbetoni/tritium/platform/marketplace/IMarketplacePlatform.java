package net.victorbetoni.tritium.platform.marketplace;

import net.victorbetoni.tritium.platform.Platform;
import net.victorbetoni.tritium.platform.MultiBodyResponse;
import net.victorbetoni.tritium.platform.SingleBodyResponse;
import net.victorbetoni.tritium.dto.common.*;
import net.victorbetoni.tritium.dto.marketplace.IOrderDTO;
import net.victorbetoni.tritium.dto.marketplace.IShippingDTO;

public interface IMarketplacePlatform<
        IDENTIFIER_TYPE,
        PAYMENT extends IPaymentDTO,
        CUST extends ICustomerDTO,
        STATUS extends IStatusDTO,
        ITEM extends IItemDTO,
        SHIPPING extends IShippingDTO,
        ADDRESS extends IAddress,
        ORDER extends IOrderDTO> extends Platform {

    String id();

    MultiBodyResponse<ORDER> listOrdersAfter(IDENTIFIER_TYPE identifier);
    SingleBodyResponse<ORDER> findOrder(IDENTIFIER_TYPE identifier);
    SingleBodyResponse<ITEM> findItem(IDENTIFIER_TYPE identifier);

}
