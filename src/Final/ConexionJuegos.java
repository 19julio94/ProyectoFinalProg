/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Final;

import java.sql.Connection;
import java.sql.*;
import javax.swing.JOptionPane;

/**
 *
 * @author JP
 */
public class ConexionJuegos {
   Connection con =null;
   public Connection Conexion(){
     
     try {
         
         con=DriverManager.getConnection("jdbc:mysql://localhost/proyectofinal","root","");
         System.out.println("Conexion establecida");
         JOptionPane.showMessageDialog(null,"Conexion establecida");
     } catch (SQLException e){
         System.out.println("Error de conexion");
     }
     
     return con;
 
}
}
