package eu.profinit.hlidacStatuLoader.dbCommunicator;

import eu.profinit.hlidacStatuLoader.institution.InstitutionCreator;
import org.json.JSONObject;

import java.sql.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class DbCommunicator {
    public static List getJsonResult(InstitutionCreator ic) {
        String url = "jdbc:postgresql://localhost:5432/store";
        String user = "postgres";
        String password = "postgres";
        DecimalFormat ft = new DecimalFormat("###,###.##");
        String query = ic.getQuery();
        System.out.println("QUERY: " + query);

        try (Connection con = DriverManager.getConnection(url, user, password);
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(query)) {

            List results = new ArrayList();
            while (rs.next()) {
                results.add(ic.createJson(rs, ft));
            }
            System.out.println("SIZE: " + results.size());
            return results;

        } catch (SQLException ex) {
            System.out.println("EXCEPTION: " + ex.getMessage());
        }
        return null;
    }
}
