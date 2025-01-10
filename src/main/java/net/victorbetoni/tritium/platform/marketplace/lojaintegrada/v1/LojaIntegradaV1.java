package net.victorbetoni.tritium.platform.marketplace.lojaintegrada.v1;

import com.google.gson.Gson;
import net.victorbetoni.tritium.platform.MultiBodyResponse;
import net.victorbetoni.tritium.platform.SingleBodyResponse;
import net.victorbetoni.tritium.platform.marketplace.IMarketplacePlatform;
import net.victorbetoni.tritium.platform.response.loja_integrada.LIMetadataAttachedResponse;
import net.victorbetoni.tritium.dto.marketplace.loja_integrada.v1.*;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

public class LojaIntegradaV1 implements IMarketplacePlatform<Integer, LIPagamentoDTO, LIClienteDTO, LISituacaoDTO, LIItemDTO, LIEnvioDTO, LIEnderecoEntrega, LIPedidoDTO> {

    private String API_KEY = "";
    private String APP_KEY = "";

    public void withCredentials(String apiKey, String appKey) {
        this.APP_KEY = appKey;
        this.API_KEY = apiKey;
    }

    public String id() {
        return "loja-integrada";
    }

    public String version() {
        return "v1";
    }

    public String URL() {
        return "https://api.awsli.com.br";
    }

    public String name() {
        return "API Loja Integrada (V1)";
    }

    public SingleBodyResponse<LIItemDTO> findItem(Integer id) {
        String ENDPOINT = String.format("produto/%d", id);

        try (var client = HttpClient.newHttpClient()) {
            var builder = this.createRequest(ENDPOINT).GET();
            this.authorize(builder);
            var req = builder.build();
            var resp = client.send(req, HttpResponse.BodyHandlers.ofString());
            if (resp.statusCode() == 200) {
                Gson gson = new Gson();
                return new SingleBodyResponse<>(gson.fromJson(resp.body(), LIItemDTO.class), "", 200);
            }
            return new SingleBodyResponse<>(null, "", resp.statusCode());
        } catch(Exception ex){
            return new SingleBodyResponse<>(null, ex.getMessage(), 505);
        }
    }

    public MultiBodyResponse<LIPedidoDTO> listOrdersAfter(Integer id) {

        String ENDPOINT = "pedido/search/?since_numero=" + id;

        try (var client = HttpClient.newHttpClient()) {
            var builder = this.createRequest(ENDPOINT).GET();
            this.authorize(builder);
            var req = builder.build();
            var resp = client.send(req, HttpResponse.BodyHandlers.ofString());
            if (resp.statusCode() == 200) {
                List<LIPedidoDTO> pedidos = new ArrayList<>();
                Gson gson = new Gson();
                var parsed = gson.fromJson(resp.body(), LIMetadataAttachedResponse.class);
                parsed.objects().stream().map(SimpleOrderDTO::numero).forEach(x -> {
                    var opt = findOrder(x);
                    if(!opt.ok()) {
                        System.out.println("Couldn't download order: " + x);
                        return;
                    }
                    pedidos.add(opt.body());
                });
                return new MultiBodyResponse<>(pedidos, "", 200);
            }
            return new MultiBodyResponse<>(List.of(), "", resp.statusCode());
        } catch(Exception ex){
            return new MultiBodyResponse<>(List.of(), ex.getMessage(), 505);
        }
    }

    public SingleBodyResponse<LIPedidoDTO> findOrder(Integer identifier) {

        String ENDPOINT = String.format("pedido/%d", identifier);

        try (var client = HttpClient.newHttpClient()) {
            var builder = this.createRequest(ENDPOINT).GET();
            this.authorize(builder);
            var req = builder.build();
            var resp = client.send(req, HttpResponse.BodyHandlers.ofString());
            if (resp.statusCode() == 200) {
                Gson gson = new Gson();
                return new SingleBodyResponse<>(gson.fromJson(resp.body(), LIPedidoDTO.class), "", 200);
            }
            return new SingleBodyResponse<>(null, "", resp.statusCode());
        } catch(Exception ex) {
            return new SingleBodyResponse<>(null, ex.getMessage(), 505);
        }

    }

    private void authorize(HttpRequest.Builder req) {
        req.header("Authorization", String.format("chave_api %s aplicacao %s", API_KEY, APP_KEY));
    }
}
