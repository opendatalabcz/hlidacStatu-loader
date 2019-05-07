package eu.profinit.hlidacStatuLoader.institution.cummulative;

import eu.profinit.hlidacStatuLoader.institution.InstitutionCreator;
import org.json.JSONObject;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;

public class CummulativeCreator implements InstitutionCreator {
    @Override
    public JSONObject createJson(ResultSet rs, DecimalFormat ft) throws SQLException {
        JSONObject json = new JSONObject();
        JSONObject partner = new JSONObject();
        JSONObject ministry = new JSONObject();

        String original_currency_amount = rs.getString("original_currency_amount");
        String amount_czk = rs.getString("amount_czk");

        partner.put("name", rs.getString("partner_name"));
        partner.put("ICO", rs.getString("partner_ico"));

        ministry.put("name", rs.getString("ministry_name"));
        ministry.put("ICO", rs.getString("ministry_ico"));

        json.put("Id", rs.getString("master_id"));
        json.put("recordType", rs.getString("record_type"));
        json.put("subject", rs.getString("subject"));
        json.put("budgetCategory", rs.getString("budget_category"));
        json.put("variableSymbol", rs.getString("variable_symbol"));
        json.put("dateCreated", rs.getString("date_created"));
        json.put("dueDate", rs.getString("due_date"));
        json.put("dateOfPayment", rs.getString("date_of_payment"));
        json.put("currency", rs.getString("currency"));

        if (original_currency_amount != null) {
            json.put("originalCurrencyAmount", ft.format(Double.parseDouble(original_currency_amount)));
        } else {
            json.put("originalCurrencyAmount", original_currency_amount);
        }

        if (amount_czk != null) {
            json.put("amountCzk", ft.format(Double.parseDouble(amount_czk)));
        } else {
            json.put("amountCzk", amount_czk);
        }

        json.put("partner", partner);
        json.put("ministry", ministry);
        json.put("authorityIdentifier", rs.getString("authority_identifier"));

        return json;
    }

    @Override
    public String getQuery() {
        return "select master_id, " +
                "record_type, " +
                "subject, " +
                "budget_category, " +
                "variable_symbol, " +
                "date_created, " +
                "due_date, " +
                "date_of_payment, " +
                "currency, " +
                "original_currency_amount, " +
                "amount_czk, " +
                "par.name as partner_name, " +
                "par.ico as partner_ico, " +
                "min.name as ministry_name, " +
                "min.ico as ministry_ico, " +
                "authority_identifier " +
                "from record " +
                "left join entity as par on partner = par.entity_id " +
                "join entity as min on authority = min.entity_id " +
                "where record_type in ('invoice', 'payment', 'transferorder', 'deposit', 'creditnote')" +
                "order by record_id";
    }

    @Override
    public String getDatasetId() {
        return "ministry-invoices";
    }
}
