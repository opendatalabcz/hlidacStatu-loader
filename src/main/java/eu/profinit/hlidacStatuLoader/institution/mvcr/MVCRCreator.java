package eu.profinit.hlidacStatuLoader.institution.mvcr;

import eu.profinit.hlidacStatuLoader.institution.InstitutionCreator;
import org.json.JSONObject;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;

public class MVCRCreator implements InstitutionCreator {
    @Override
    public JSONObject createJson(ResultSet rs, DecimalFormat ft) throws SQLException {
        JSONObject json = new JSONObject();
        JSONObject partner = new JSONObject();

        partner.put("name", rs.getString("name"));
        partner.put("ICO", rs.getString("ico"));

        json.put("Id", rs.getString("master_id"));
        json.put("dateCreated", rs.getString("date_created"));
        json.put("currency", rs.getString("currency"));

        String originalCurrencyAmount = rs.getString("original_currency_amount");
        if (originalCurrencyAmount != null && !originalCurrencyAmount.isEmpty()) {
            json.put("originalCurrencyAmount", ft.format(Double.parseDouble(rs.getString("original_currency_amount"))));
        } else {
            json.put("originalCurrencyAmount", originalCurrencyAmount);
        }
//                json.put("originalCurrencyAmount", rs.getString("original_currency_amount"));

        String amountCzk = rs.getString("amount_czk");
        if (amountCzk != null && !amountCzk.isEmpty()) {
            json.put("amountCzk", ft.format(Double.parseDouble(amountCzk)));
        } else {
            json.put("amountCzk", amountCzk);
        }
//                json.put("amountCzk", rs.getString("amount_czk"));

        json.put("partner", partner);
        json.put("authorityIdentifier", rs.getString("authority_identifier"));

        return json;
    }

    @Override
    public String getQuery() {
        return "select master_id, " +
                "date_created, " +
                "currency, " +
                "original_currency_amount, " +
                "amount_czk, " +
                "en.name, " +
                "en.ico, " +
                "authority_identifier " +
                "from record " +
                "left join entity as en on partner = entity_id " +
                "where authority = (select entity_id from entity as en where en.name like 'Ministerstvo vnitra') " +
                "and record_type in ('invoice', 'payment') and partner is null;";
    }

    @Override
    public String getDatasetId() {
        return "faktury-mvcr";
    }
}
