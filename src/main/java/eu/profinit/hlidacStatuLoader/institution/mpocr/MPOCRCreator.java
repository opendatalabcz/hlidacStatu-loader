package eu.profinit.hlidacStatuLoader.institution.mpocr;

import eu.profinit.hlidacStatuLoader.institution.InstitutionCreator;
import org.json.JSONObject;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;

public class MPOCRCreator implements InstitutionCreator {

    public JSONObject createJson(ResultSet rs, DecimalFormat ft) throws SQLException {
        JSONObject json = new JSONObject();
        JSONObject partner = new JSONObject();

        partner.put("name", rs.getString("name"));
        partner.put("ICO", rs.getString("ico"));

        json.put("Id", rs.getString("master_id"));
        json.put("subject", rs.getString("subject"));
        json.put("variableSymbol", rs.getString("variable_symbol"));
        json.put("dateCreated", rs.getString("date_created"));
        json.put("dueDate", rs.getString("due_date"));
        json.put("dateOfPayment", rs.getString("date_of_payment"));
        json.put("currency", rs.getString("currency"));
        json.put("originalCurrencyAmount", ft.format(Double.parseDouble(rs.getString("original_currency_amount"))));
//                json.put("originalCurrencyAmount", rs.getString("original_currency_amount"));
        json.put("amountCzk", ft.format(Double.parseDouble(rs.getString("amount_czk"))));
//                json.put("amountCzk", rs.getString("amount_czk"));
        json.put("partner", partner);
        json.put("authorityIdentifier", rs.getString("authority_identifier"));

        return json;
    }

    public String getQuery() {
        return "select master_id, " +
                "subject, " +
                "variable_symbol, " +
                "date_created, " +
                "due_date, " +
                "date_of_payment, " +
                "currency, " +
                "original_currency_amount, " +
                "amount_czk, " +
                "en.name, " +
                "en.ico, " +
                "authority_identifier " +
                "from record " +
                "left join entity as en on partner = entity_id " +
                "where authority = (select entity_id from entity as en where en.name like 'Ministerstvo průmyslu a obchodu') " +
                "and record_type in ('invoice', 'payment')";
    }

    public String getDatasetId() {
        return "faktury-mpocr";
    }
}
