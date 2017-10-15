package inventory_app.data_source;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class database {
     public static void main(String[] args) {

            Connection con = null;
            Statement st = null;
            ResultSet rs = null;

            String url = "jdbc:mysql://vm343a.se.rit.edu:3333/inventorymanagement";
            String user = "GroupA";
            String password = "SWEN343";

            try {

                con = DriverManager.getConnection(url, user, password);
                st = con.createStatement();
                rs = st.executeQuery("SELECT VERSION()");

                if (rs.next()) {

                    System.out.println(rs.getString(1));
                }

            } catch (SQLException ex) {

                Logger lgr = Logger.getLogger(database.class.getName());
                lgr.log(Level.SEVERE, ex.getMessage(), ex);

            } finally {

                try {

                    if (rs != null) {
                        rs.close();
                    }

                    if (st != null) {
                        st.close();
                    }

                    if (con != null) {
                        con.close();
                    }

                } catch (SQLException ex) {

                    Logger lgr = Logger.getLogger(database.class.getName());
                    lgr.log(Level.WARNING, ex.getMessage(), ex);
                }
            }
        }
    }