package PRT;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
/**
 * Classe responsÃ¡vel pela conexÃ£o com banco de dados MySQL
 */

public class AcessoDB
{
 // -----------------------------------------------------------
 // Carrega driver JDBC
 //
   static
   {
      try
      {
         Class.forName("com.mysql.jdbc.Driver");
      }
      catch (ClassNotFoundException e)
      {
         throw new RuntimeException(e);
      }
   } 
 // -----------------------------------------------------------
 // ObtÃ©m conexÃ£o com o banco de dados
   public Connection obtemConexao() throws SQLException
   {        
      String user      = "root"; // <- Login da conexao do MySql
      String pass      = "root"; // <- Senha da conexao do MySql
      String nomeBanco = "cadastrocliente";  // <- Nome da tabela MySql
      
      String txt  = "jdbc:mysql://localhost/"+ nomeBanco +"?user="+ user +"&password="+ pass ;            
                  //"jdbc:mysql://localhost/"tutorial"?user=root&password=senha" 
      return DriverManager.getConnection(txt);
   }
}
