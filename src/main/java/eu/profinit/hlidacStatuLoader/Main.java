package eu.profinit.hlidacStatuLoader;

import eu.profinit.hlidacStatuLoader.dbCommunicator.DbCommunicator;
import eu.profinit.hlidacStatuLoader.institution.InstitutionCreator;
import eu.profinit.hlidacStatuLoader.institution.mdcr.MDCRCreator;
import eu.profinit.hlidacStatuLoader.institution.mfcr.MFCRCreator;
import eu.profinit.hlidacStatuLoader.institution.mmrcr.MMRCRCreator;
import eu.profinit.hlidacStatuLoader.institution.mscr.MSCRCreator;
import eu.profinit.hlidacStatuLoader.restCommunication.RestCommunicator;

import java.io.IOException;

/**
 * TODO
 *
 */
public class Main
{
    public static void main( String[] args ) {
//        InstitutionCreator ic = new MFCRCreator();
//        InstitutionCreator ic = new MSCRCreator();
//        InstitutionCreator ic = new MMRCRCreator();
        InstitutionCreator ic = new MDCRCreator();
        try {
            RestCommunicator.POSTRequest(DbCommunicator.getJsonResult(ic), ic.getDatasetId());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
