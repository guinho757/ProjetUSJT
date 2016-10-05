package PRT;

public class CadastroControl
{
    private String nome, email;
    
    public CadastroControl()
    {
        nome  = "";
        email = "";
    }
    
    public void setNome(String nomeAux)
    {
        nome = nomeAux;
    }
    
    public void setEmail(String emailAux)
    {
        email = emailAux;
    }
    
    public String getNome()
    {
        return nome;
    }
    
    public String getEmail()
    {
        return email;
    }
}
