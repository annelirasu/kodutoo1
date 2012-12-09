/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.dbutils.DbUtils;

/**
 *
 * @author reget.kalamees
 */
public class MkDb extends HttpServlet {
    
    @Override
    public void init(){
    try{
    Class.forName("org.hsqldb.jdbcDriver");
   } catch (ClassNotFoundException e){
    throw new RuntimeException(e);
   }
    }

    /**
     * Processes requests for both HTTP
     * <code>GET</code> and
     * <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            setupDatabase();
            /* TODO output your page here. You may use following sample code. */
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet MkDb</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet MkDb at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        } finally {            
            out.close();
        }
    }
    
    private static void setupDatabase()  {

  
  Connection conn=null;

  Statement stmt = null;
  ResultSet rset = null; // et midagi välja printida, praegu pole vaja

  try {
      conn = DriverManager.getConnection("jdbc:hsqldb:file:${user.home}/piirivalveDb;shutdown=true");
   stmt = conn.createStatement();
   //loome tabeli RIIGI_ADMIN_YKSUSE_Liik
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS RIIGI_ADMIN_YKSUSE_LIIK (riigi_admin_yksuse_liik_id INTEGER IDENTITY PRIMARY KEY, kood VARCHAR(10) NOT NULL ,nimetus VARCHAR(100) NOT NULL,kommentaar LONGVARCHAR NOT NULL,avaja VARCHAR(32) NOT NULL,avatud DATE NOT NULL,muutja VARCHAR(32) NOT NULL,muudetud DATE NOT NULL,sulgeja VARCHAR(32) NULL,suletud DATE NOT NULL ) ");
            //sisestame sinna administratiivyksused
            stmt.executeUpdate(" INSERT INTO RIIGI_ADMIN_YKSUSE_LIIK  VALUES (1, 'mk','maakond','suurim v�imalik administratiiv�ksus Eesti vabariigis, sisaldab linnu ja valdasid','An4', CURRENT_DATE,'Nobody','9999-12-31', NULL,'9999-12-31' )");
            stmt.executeUpdate(" INSERT INTO RIIGI_ADMIN_YKSUSE_LIIK  VALUES (2, 'ln','linn','suuruselt teine admin �ksus, sisaldub maakonnas,  Tallinn sisaldab linnaosasid','An4', CURRENT_DATE,'Nobody','9999-12-31', NULL,'9999-12-31' )");
            stmt.executeUpdate(" INSERT INTO RIIGI_ADMIN_YKSUSE_LIIK  VALUES (3, 'v','vald','samuti suuruselt teine admin �ksus, sisaldub maakonnas, v�ib sisaldada ka linna, sisaldab aleveid, alevikke ja k�lasid','An4', CURRENT_DATE,'Nobody','9999-12-31', NULL,'9999-12-31' )");
            stmt.executeUpdate(" INSERT INTO RIIGI_ADMIN_YKSUSE_LIIK  VALUES (4, 'a','alev','sisaldub vallas','An4', CURRENT_DATE,'Nobody','9999-12-31', NULL,'9999-12-31' )");

            //loome tabeli VOIMALIK_ALLUVUS NB! Rings�ltuvused pole lubatud, aga praegu need v�listatud ei ole
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS VOIMALIK_ALLUVUS (riigi_admin_yksuse_liik_id INTEGER, voimalik_alluv_liik_id INTEGER, avaja VARCHAR(32) NOT NULL,avatud DATE NOT NULL,muutja VARCHAR(32) NOT NULL,muudetud DATE NOT NULL,sulgeja VARCHAR(32) NULL,suletud DATE NOT NULL ) ");
            //seame v�lisv�tmed VOIMALIK ALLUVUS tulpadele
            stmt.executeUpdate("ALTER TABLE VOIMALIK_ALLUVUS  ADD CONSTRAINT Voimalik_Alluvus_CR_FK_ylem FOREIGN KEY (riigi_admin_yksuse_liik_id) REFERENCES RIIGI_ADMIN_YKSUSE_LIIK (riigi_admin_yksuse_liik_id);");
            stmt.executeUpdate("ALTER TABLE VOIMALIK_ALLUVUS  ADD CONSTRAINT Voimalik_Alluvus_CR_FK_alam FOREIGN KEY (voimalik_alluv_liik_id) REFERENCES RIIGI_ADMIN_YKSUSE_LIIK (riigi_admin_yksuse_liik_id);");
            //lisame alluvused
            stmt.executeUpdate(" INSERT INTO VOIMALIK_ALLUVUS  VALUES (1,2,'An4', CURRENT_DATE,'Nobody','9999-12-31', NULL,'9999-12-31' )");
            stmt.executeUpdate(" INSERT INTO VOIMALIK_ALLUVUS  VALUES (1,3,'An4', CURRENT_DATE,'Nobody','9999-12-31', NULL,'9999-12-31' )");

            //loome tabeli RIIGI_ADMIN_YKSUS
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS RIIGI_ADMIN_YKSUS (riigi_admin_yksus_id INTEGER IDENTITY PRIMARY KEY, kood VARCHAR(20) NOT NULL ,nimetus VARCHAR(100) NOT NULL,kommentaar LONGVARCHAR NOT NULL,avaja VARCHAR(32) NOT NULL,avatud DATE NOT NULL,muutja VARCHAR(32) NOT NULL,muudetud DATE NOT NULL,sulgeja VARCHAR(32) NULL,suletud DATE NOT NULL, riigi_admin_yksuse_liik_id INTEGER ) ");
            //lisame v�lisv�tme
            stmt.executeUpdate("ALTER TABLE RIIGI_ADMIN_YKSUS ADD CONSTRAINT Riigi_admin_yksus_CR_FK FOREIGN KEY (riigi_admin_yksuse_liik_id) REFERENCES RIIGI_ADMIN_YKSUSE_LIIK (riigi_admin_yksuse_liik_id);");
            //lisame andmed
            stmt.executeUpdate(" INSERT INTO RIIGI_ADMIN_YKSUS VALUES (1, 'V145','Leisi vald','asub Saare mk-s','An4', CURRENT_DATE,'Nobody','9999-12-31', NULL,'9999-12-31',3 )");
            stmt.executeUpdate(" INSERT INTO RIIGI_ADMIN_YKSUS VALUES (2, 'V146','Orissaare vald','asub Saare mk-s','An4', CURRENT_DATE,'Nobody','9999-12-31', NULL,'9999-12-31',3 )");
            stmt.executeUpdate(" INSERT INTO RIIGI_ADMIN_YKSUS VALUES (3, 'Mk100','Harju maakond','suurima rahvastikutihedusega','An4', CURRENT_DATE,'Nobody','9999-12-31', NULL,'9999-12-31',1)");
            stmt.executeUpdate(" INSERT INTO RIIGI_ADMIN_YKSUS VALUES (4, 'Mk123','Saare maakond','asub Saare mk-s','An4', CURRENT_DATE,'Nobody','9999-12-31', NULL,'9999-12-31',1 )");
            stmt.executeUpdate(" INSERT INTO RIIGI_ADMIN_YKSUS VALUES (5, 'L234','Kuressaare linn','asub Saare mk-s','An4', CURRENT_DATE,'Nobody','9999-12-31', NULL,'9999-12-31',2 )");
            stmt.executeUpdate(" INSERT INTO RIIGI_ADMIN_YKSUS VALUES (6, 'A456','Orissaare alev','asub Saare mk-s, Orissaare vallas','An4', CURRENT_DATE,'Nobody','9999-12-31', NULL,'9999-12-31',4 )");

            //loome tabeli ADMIN_ALLUVUS
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS ADMIN_ALLUVUS (ylemus_yksus_id INTEGER , alluv_yksus_id INTEGER,avaja VARCHAR(32) NOT NULL,avatud DATE NOT NULL,muutja VARCHAR(32) NOT NULL,muudetud DATE NOT NULL,sulgeja VARCHAR(32) NULL,suletud DATE NOT NULL ) ");
            //lisame v�lisv�tmed
            stmt.executeUpdate("ALTER TABLE ADMIN_ALLUVUS ADD CONSTRAINT Admin_alluvus_CR_FK_ylem FOREIGN KEY (ylemus_yksus_id) REFERENCES RIIGI_ADMIN_YKSUS (riigi_admin_yksus_id)");
            stmt.executeUpdate("ALTER TABLE ADMIN_ALLUVUS ADD CONSTRAINT Admin_alluvus_CR_FK_alam FOREIGN KEY (alluv_yksus_id) REFERENCES RIIGI_ADMIN_YKSUS (riigi_admin_yksus_id)");
            //lisame andmed
            stmt.executeUpdate(" INSERT INTO ADMIN_ALLUVUS VALUES (1,1,'An4', CURRENT_DATE,'Nobody','9999-12-31', NULL,'9999-12-31' );");
            stmt.executeUpdate(" INSERT INTO ADMIN_ALLUVUS VALUES (4,5,'An4', CURRENT_DATE,'Nobody','9999-12-31', NULL,'9999-12-31' )");
            stmt.executeUpdate(" INSERT INTO ADMIN_ALLUVUS VALUES (2,6,'An4', CURRENT_DATE,'Nobody','9999-12-31', NULL,'9999-12-31' )");

  
  } 
  catch(Exception err){
  System.out.println(err.getMessage());
  }
  finally {
   DbUtils.closeQuietly(rset);
   DbUtils.closeQuietly(stmt);
   DbUtils.closeQuietly(conn);
  }

 }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP
     * <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP
     * <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
