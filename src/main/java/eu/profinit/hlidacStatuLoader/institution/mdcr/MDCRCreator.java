package eu.profinit.hlidacStatuLoader.institution.mdcr;

import eu.profinit.hlidacStatuLoader.institution.InstitutionCreator;
import org.json.JSONObject;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;

public class MDCRCreator implements InstitutionCreator {

    @Override
    public JSONObject createJson(ResultSet rs, DecimalFormat ft) throws SQLException {
        JSONObject json = new JSONObject();
        JSONObject partner = new JSONObject();

        partner.put("name", rs.getString("name"));
        partner.put("ICO", rs.getString("ico"));

        json.put("Id", rs.getString("master_id"));
        json.put("subject", rs.getString("subject"));
        json.put("budgetCategory", rs.getString("budget_category"));
        json.put("dateCreated", rs.getString("date_created"));
        json.put("dueDate", rs.getString("due_date"));
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
                "subject, " +
                "budget_category, " +
                "date_created, " +
                "due_date, " +
                "currency, " +
                "original_currency_amount, " +
                "amount_czk, " +
                "en.name, " +
                "en.ico, " +
                "authority_identifier " +
                "from record " +
                "join entity as en on partner = entity_id " +
                "where authority = (select entity_id from entity as en where en.name like 'Ministerstvo dopravy') " +
                "and record_type in ('invoice', 'payment');";
    }

    @Override
    public String getDatasetId() {
        return "faktury-mdcr";
    }
}
