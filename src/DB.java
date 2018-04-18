//STEP 1. Import required packages
import java.lang.reflect.Array;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.HashMap;

//We copied and edited code from tutorialspoint.com to fit our needs.
public class DB {
    // JDBC driver name and database URL
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost/wikirank";

    //  Database credentials
    static final String USER = "root";
    static final String PASS = "";

    public HashSet<String> names = new HashSet<String>();
    public HashMap<String, ArrayList<String>> map = new HashMap<String, ArrayList<String>>();

    public DB(int limit) {
        Connection conn = null;
        Statement stmt = null;
        PreparedStatement pstmt = null;

        try{
            //STEP 2: Register JDBC driver
            Class.forName("com.mysql.jdbc.Driver");

            //STEP 3: Open a connection
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL,USER,PASS);

            //STEP 4: Execute a query
            stmt = conn.createStatement();
            String sql = "";
            if (limit == 0) {
                sql = "SELECT * FROM pagelinks";
            } else {
                sql = "SELECT * FROM pagelinks LIMIT " + limit;
            }
            ResultSet rs_pl = stmt.executeQuery(sql);
            int count = 0;
            //STEP 5: Extract data from result set
            while(rs_pl.next()){
                if (count % 10000 == 0) {
                    System.out.println("Number of pages imported: " + count);
                }
                //Retrieve by column name
                int pl_from  = rs_pl.getInt("pl_from");
                String pl_to_title = rs_pl.getString("pl_title");

                // Query Id given title
                pstmt = conn.prepareStatement("SELECT page_title FROM page WHERE page_id=?");
                pstmt.setInt(1, pl_from);
                ResultSet rs_pg = pstmt.executeQuery();
                String pg_from_title = "";

                if (rs_pg.next()) {
                    pg_from_title = rs_pg.getString("page_title");

                    names.add(pg_from_title);
                    names.add(pl_to_title);

                    if (!map.containsKey(pg_from_title)) {
                        map.put(pg_from_title, new ArrayList<String>());
                    }
                    ArrayList<String> set = map.get(pg_from_title);
                    set.add(pl_to_title);


                }
                rs_pg.close();
                pstmt.close();
                count++;

            }
            //STEP 6: Clean-up environment

            rs_pl.close();
            stmt.close();
            conn.close();

        }catch(SQLException se){
            //Handle errors for JDBC
            se.printStackTrace();
        }catch(Exception e){
            //Handle errors for Class.forName
            e.printStackTrace();
        }finally{
            //finally block used to close resources
            try{
                if(stmt!=null)
                    stmt.close();
            }catch(SQLException se2){
            }// nothing we can do
            try{
                if(conn!=null)
                    conn.close();
            }catch(SQLException se){
                se.printStackTrace();
            }//end finally try
        }//end try
        System.out.println("\nGoodbye!");

    }//end main

    public HashMap<String, ArrayList<String>> getMap() {
        return this.map;
    }

    public HashSet<String> getSet() {
        return this.names;
    }
}//end FirstExample