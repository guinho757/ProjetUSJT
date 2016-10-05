package PRT;
import java.awt.*;
import javax.swing.*; 
import java.awt.event.*;
import javax.swing.border.*;

import java.util.ArrayList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;

public class TestConnectionMySql extends JFrame implements ActionListener
{
   protected JButton exibirMensagem; //deve pegar uma informaÃ§Ã£o do bando de dados e imprimir na tela
   protected JButton collectionObj; //deve buscar varias informaÃ§Ãµes como: Nome e email e colocar em um obj
                                       //Ex: Classe pessoa guardarÃ¡ nome e email de cada pessoa.
   protected JButton alterar,consultar,incluir, excluir;   
   
   SqlDAO dao;
   CadastroControl cadastro;
   
   AcessoDB db;   
   Connection conn;
   PreparedStatement st;
   ResultSet rs;
   
   public TestConnectionMySql()
   {
      super();
      
      db             = new AcessoDB(); //classe de conexÃ£o sql     
      conn           = null;           //variavel que recebe a conexÃ£o do sql
      st             = null;            // PreparedStantement
      rs             = null;
      
      cadastro       = new CadastroControl();
      exibirMensagem = new JButton("Mensagem MySql");
      collectionObj  = new JButton("Buscar   dados");
      alterar        = new JButton("Alterar  nome");
      consultar      = new JButton("Consulta nome");
      incluir        = new JButton("Incluir  nome");
      excluir        = new JButton("Excluir  Excluir");     
      
     exibirMensagem.addActionListener(this);
      collectionObj.addActionListener(this);
            alterar.addActionListener(this);
          consultar.addActionListener(this);
            incluir.addActionListener(this);
            excluir.addActionListener(this);
   	  
            //GridBag  
      setLayout(new GridBagLayout());// define o layout      
      ((JComponent)getContentPane()).setBorder(new EmptyBorder(10, 10, 10, 10));
      //define uma borda para aumentar o espaÃƒÂ§co entre as bordas da janela e o gerenciador de layout//
    
      GridBagConstraints cons = new GridBagConstraints();
      // cria o GridBagConstraints
     
      cons.insets = new Insets(10, 10, 10, 10);//Controla a distancia entre os botÃƒÂµoes, cima baixo, esquerda e direita.
      cons.anchor = GridBagConstraints. WEST;//alinhamento dos componeste, esta alinhado a oeste (lado esquerdo)
      
     
      cons.gridy      = 1; // linha
      cons.gridx      = 0; // coluna
      cons.gridwidth  = 1; //direciona os botÃƒÂµes daqui para baixo para ocuparem uma celula na horizontal
      
      cons.anchor = GridBagConstraints. CENTER; //centraliza os botÃƒÂµes
      add(exibirMensagem,cons);
         
      cons.gridy      = 3; // linha
      cons.gridx      = 0; // coluna
      cons.gridwidth  = 1; //direciona os botÃƒÂµes daqui para baixo para ocuparem uma celula na horizontal
      
      cons.anchor = GridBagConstraints. CENTER; //centraliza os botÃƒÂµes
      add(collectionObj,cons);
   
      cons.gridy      = 4; // linha
      cons.gridx      = 0; // coluna
      cons.gridwidth  = 1; //direciona os botÃƒÂµes daqui para baixo para ocuparem uma celula na horizontal
      
      cons.anchor = GridBagConstraints. CENTER; //centraliza os botÃƒÂµes
      add(alterar,cons);   
    
      cons.gridy      = 1; // linha
      cons.gridx      = 4; // coluna
      cons.gridwidth  = 5; //direciona os botÃƒÂµes daqui para baixo para ocuparem uma celula na horizontal
     
      cons.anchor = GridBagConstraints. CENTER; //centraliza os botÃƒÂµes
      add(consultar,cons);
    
      cons.gridy      = 3; // linha
      cons.gridx      = 4; // coluna
      cons.gridwidth  = 5; //direciona os botÃƒÂµes daqui para baixo para ocuparem uma celula na horizontal
     
      cons.anchor = GridBagConstraints. CENTER; //centraliza os botÃƒÂµes   
      add(incluir,cons);
      
      cons.gridy      = 4; // linha
      cons.gridx      = 4; // coluna
      cons.gridwidth  = 5; //direciona os botÃƒÂµes daqui para baixo para ocuparem uma celula na horizontal
     
      cons.anchor = GridBagConstraints. CENTER; //centraliza os botÃƒÂµes
      add(excluir,cons);     
      
      setTitle("TESTE MySql");
      setSize(350,410); //Largura, altura
      setVisible(true);
      setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
      
   }//fim construtor
  
public void insert()throws SQLException
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
        st     = conn.prepareStatement(insert);
	  st.setString(1, cadastro.getNome());
	  st.setString(2, cadastro.getEmail());
	  st.execute();
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
            System.err.print(err);
	}
	finally
	{
            if(st != null)
            {
                try
                {
                    st.close();
                }//Fim Try
                catch(SQLException e2)
                {
                   System.err.print(e2);
                }//catch do finally
            }//if do finally
	}//fim do finally
    }//fim do catch (SQLException e)		
}//Fim insert()

public String select(String nomedb)throws SQLException
{
   String sqlSelect = "SELECT * FROM cadastro WHERE nome like '"+nomedb+"%'";
   ArrayList<CadastroControl> vetCadastro = new ArrayList<CadastroControl>();
   String txt = "";
   try
   {
       conn   = db.obtemConexao();
       st     = conn.prepareStatement(sqlSelect);
       rs = st.executeQuery();
       while(rs.next())
       {
           cadastro = new CadastroControl();
           cadastro.setNome( rs.getString(1) );
           cadastro.setEmail(rs.getString(2) );
           vetCadastro.add(cadastro);      
       }//Fim while
       CadastroControl aux;
       for(int i = 0; i < vetCadastro.size(); i++)
       {
            aux = vetCadastro.get(i);
            txt += "Nome da "+(i+1)+"ª pessoa: "+aux.getNome()+"\nEmail: "+aux.getEmail()+"\n\n\n";
       }  
       
   }catch(Exception e) // Fim try e começo de catch
   {
       e.printStackTrace();
       try
       {
           conn.rollback();
       }catch(SQLException error)
       {
           JOptionPane.showMessageDialog(null, "Error no 2º catch");
       }       
   }//Fim catch(e)
   finally
   {
       if(st != null)
       {
           try
           {
               st.close();
           }
           catch(SQLException error2)
           {
               JOptionPane.showMessageDialog(null, "Erro no catch dentro do IF");
           }
       }//Fim IF()
   }//Fim finally 
   return txt;
}

   public void actionPerformed(ActionEvent event)
   {
      try
      {          
          if(event.getSource() == incluir){insert();}
          
          if(event.getSource() == consultar){ JOptionPane.showMessageDialog(null,select("Hugo"));}
      }
      catch(Exception error)
      {      }
    }

   public static void main(String args[])
   {
      new TestConnectionMySql();
   }
   
}//Fim classe
