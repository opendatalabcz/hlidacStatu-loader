package eu.profinit.hlidacStatuLoader.institution;

import org.json.JSONObject;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;

public interface InstitutionCreator{
    public JSONObject createJson(ResultSet rs, DecimalFormat ft)  throws SQLException;
    public String getQuery();
    public String getDatasetId();
}
