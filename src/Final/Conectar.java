/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Final;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author JP
 */
public class Conectar {

    public String bd = "proyectofinal";
    public String login = "root";
    public String password = "";
    public String url = "jdbc:mysql://localhost/" + bd;
    private Persona pers = new Persona();

    Connection conn = null;

    public Conectar() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(url, login, password);

            if (conn != null) {

                JOptionPane.showMessageDialog(null, "Conexion a base de datos" + bd + ".listo");

            }
        } catch (SQLException e) {
            System.out.println(e);

        } catch (ClassNotFoundException e) {
            System.out.println(e);

        }
    }

    public Connection getConnection() {

        return conn;
    }

    public void desconectar() {
        conn = null;

    }

//________________________________________________________________________________________________________________________________
  
 public Object [][] select(String table, String camDevolver, String where){
      int registros = 0;      
      String colname[] = camDevolver.split(",");

      //Consultas SQL
      String q ="SELECT " + camDevolver + " FROM " + table;
      String q2 = "SELECT count(*) as total FROM " + table;
      if(where!=null)
      {
          q+= " WHERE " + where;
          q2+= " WHERE " + where;
      }
       try{
         PreparedStatement pstm = conn.prepareStatement(q2);
         ResultSet res = pstm.executeQuery();
         res.next();
         registros = res.getInt("total");
         res.close();
      }catch(SQLException e){
         System.out.println(e);
      }
    
    //se crea una matriz con tantas filas y columnas que necesite
    Object[][] data = new String[registros][camDevolver.split(",").length];
    //realizamos la consulta sql y llenamos los datos en la matriz "Object"
      try{
         PreparedStatement pstm = conn.prepareStatement(q);
         ResultSet res = pstm.executeQuery();
         int i = 0;
         while(res.next()){
            for(int j=0; j<=camDevolver.split(",").length-1;j++){
                data[i][j] = res.getString( colname[j].trim() );
            }
            i++;         }
         res.close();
          }catch(SQLException e){
         System.out.println(e);
    }
    return data;
 }
   
   
   public boolean ingresar(String user, String contra)
    {        
        Object[][] res = this.select("users", " usuario , contrasenha ", " usuario='"+user+"' AND contrasenha='"+contra+"' ");
        if( res.length > 0)
        {
            pers.setNombre( res[0][0].toString() );
            pers.setContraseña( res[0][1].toString() );
            
            return true;
        }        
        else
            return false;
    }
   
   public void NuevaPersona(String name, String contra){
       try {            
            PreparedStatement pstm =this.getConnection().prepareStatement("insert into " + 
                    "users(USUARIO,CONTRASENHA) " +
                    " values(?,?)"); 
            pstm.setString(1, name);
            pstm.setString(2, contra);                      
            pstm.execute();
            pstm.close();
            JOptionPane.showMessageDialog(null, "usuario"+" "+name+" "+"creado correctamente");
            
         }catch(SQLException e){
         System.out.println(e);
      }
   }

}
