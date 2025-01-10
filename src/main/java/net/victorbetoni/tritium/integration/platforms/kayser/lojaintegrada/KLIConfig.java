package net.victorbetoni.tritium.integration.platforms.kayser.lojaintegrada;

import com.google.gson.annotations.SerializedName;

public class KLIConfig {

    @SerializedName("loja-integrada.appkey")
    public String LOJA_INTEGRADA_APP_KEY;

    @SerializedName("loja-integrada.apikey")
    public String LOJA_INTEGRADA_API_KEY;

    @SerializedName("kayser.apikey")
    public String KAYSER_API_KEY;

    @SerializedName("kayser.codigoEmpresa")
    public int KAYSER_CODIGO_EMPRESA;

}
