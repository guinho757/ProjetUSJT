package PRT;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import javax.swing.JOptionPane;


public class SqlDAO
{
    CadastroControl cadastro;
    AcessoDB          db;
    Connection      conn;
    PreparedStatement stm;
    ResultSet          rs;
    
    public SqlDAO()
    {
        cadastro = new CadastroControl();
        db       = new AcessoDB();
        conn     = null;
        stm      = null;
    }
    
public void insert()
{
    String nome, email, insert;
    
    nome   = JOptionPane.showInputDialog("Digite o Nome do Cliente:");
    email  = JOptionPane.showInputDialog("Digite o E-Mail do cliente:");
    
    cadastro.setNome(nome);
    cadastro.setEmail(email);
    
    try
    {
        conn   = db.obtemConexao();
        conn.setAutoCommit(false);
        insert = "insert into cadastro(nome, email) values(?,?)";
        stm     = conn.prepareStatement(insert);
	  stm.setString(1, cadastro.getNome());
	  stm.setString(2, cadastro.getEmail());
	  stm.execute();
	conn.commit();
    }//Fim try
    catch(SQLException err)
    {
        try
        {
            conn.rollback();
        }//Fim try
        catch(SQLException e1)
        {
            JOptionPane.showMessageDialog(null, "Erro ao tentar executar rollback!!!");
	}
	finally
	{
            if(stm != null)
            {
                try
                {
                    stm.close();
                }//Fim Try
                catch(SQLException e2)
                {
                   JOptionPane.showMessageDialog(null, "Erro ao tentar fechar a conexÃ£o!!!");
                }//catch do finally
            }//if do finally
	}//fim do finally
    }//fim do catch (SQLException e)		
}//Fim insert()

public String select()
{
   String sqlSelect, nomeCliente, queryNome = "";
   
   nomeCliente = JOptionPane.showInputDialog("Digite o nome do cliente que deseja consultar");   
   sqlSelect   = "SELECT * FROM cadastrocliente WHERE NOME = "+nomeCliente;
   try
   {
      stm = conn.prepareStatement(sqlSelect);
      rs  = stm.executeQuery();
         
         if(rs.next())
         {
            queryNome += rs.getString(1);
         }//Fim IF()      
   }//Fim Try
   catch(Exception e)
   {
      e.printStackTrace();
      try
      {
         conn.rollback();
      }
      catch(SQLException sql)
      {
         System.err.print(sql.getStackTrace());
      }
   }//Fim do catch(e)
   finally
   {
      if(stm != null)
      {
         try
         {
            stm.close();
         }
         catch(SQLException sqlErr)
         {
            System.err.print(sqlErr.getStackTrace());
         }
      }
   }
   return queryNome;
   
}//Fim select()
}
