package net.victorbetoni.tritium.integration.platforms.kayser.lojaintegrada;

import net.victorbetoni.tritium.platform.erp.kayser.v1.KayserSolucoesV1;
import net.victorbetoni.tritium.platform.marketplace.lojaintegrada.v1.LojaIntegradaV1;
import net.victorbetoni.tritium.integration.Integration;
import net.victorbetoni.tritium.integration.platforms.kayser.lojaintegrada.modules.prevendaautomatica.KLIPreVendaAutomatica;

import java.sql.*;
import java.util.List;

public class KayserLojaIntegrada extends Integration<KayserSolucoesV1, LojaIntegradaV1, KLIConfig> {

    public static final String ID = "kayser-lojaintegrada";
    private Connection db;

    public KayserLojaIntegrada(String id) {
        super(id, new KayserSolucoesV1(), new LojaIntegradaV1(), KLIConfig.class, getIntegrationConfigPath(id));
        this.consumed.withCredentials(this.config.LOJA_INTEGRADA_API_KEY, this.config.LOJA_INTEGRADA_APP_KEY);
        this.consumer.withCredentials(this.config.KAYSER_API_KEY, this.config.KAYSER_CODIGO_EMPRESA);
        getConnetion();
    }

    protected Connection getConnetion() {
        if(db == null) {
            try {
                String dbPath = String.format("%s\\config\\%s\\mappings.db", System.getProperty("user.dir"), ID);
                String url = "jdbc:sqlite:" + dbPath;
                db =  DriverManager.getConnection(url);
            } catch (SQLException ex){
                ex.printStackTrace();
            }
        }
        return db;
    }

    @Override
    public void setupModules(List<String> enabled) {
        this.moduleStates.put("pre-venda-automatica", false);
        this.modules.put("pre-venda-automatica", new KLIPreVendaAutomatica(KLIPreVendaAutomatica.ID, this));

        for(String module : enabled) {
            this.moduleStates.remove(module);
            this.moduleStates.put(module, true);
        }

    }

    public void createMapping(int li, String kayser) throws Exception {
        var optItem = this.consumed.findItem(li);
        if(!optItem.ok()) {
            throw new Exception("Item não encontrado na Loja Integrada.");
        }
        try(PreparedStatement stmt = getConnetion().prepareStatement("SELECT * FROM product_mappings WHERE li_id = ?")) {
            stmt.setInt(1, li);
            if(stmt.getResultSet().next()) {
                throw new Exception("Item já está mapeado.");
            }
        }
        try (PreparedStatement pstmt = getConnetion().prepareStatement("INSERT INTO product_mappings VALUES(?,?,?)")) {
            pstmt.setString(1, kayser);
            pstmt.setInt(2, li);
            pstmt.setString(3, optItem.body().nome().toUpperCase());
            pstmt.execute();
        }
    }

    public ItemData getItemData(int lojaIntegradaId) {
        try (PreparedStatement pstmt = getConnetion().prepareStatement("SELECT * FROM product_mappings WHERE li_id = ?")) {
            pstmt.setInt(1, lojaIntegradaId);
            ResultSet rs = pstmt.executeQuery();
            if(!rs.next()) {
                return null;
            }
            return new ItemData(rs.getString("kayser_id"), rs.getInt("gp"), rs.getInt("li_id"), rs.getString("gtin"), rs.getString("ref"));
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}
