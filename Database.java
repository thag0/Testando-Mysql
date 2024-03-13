import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Database{
   Connection conexao = null;
   String nome = null;

   public Database(){}

   /**
    * Conecta ao banco de dados desejado.
    * @param url
    * @param usuario
    * @param senha
    */
   public void conectar(String url, String usuario, String senha){
      try{
         conexao = DriverManager.getConnection(url, usuario, senha);
      
      }catch(SQLException e){
         e.printStackTrace();
      }
   }

   /**
    * Desconecta o banco de dados caso ele possua uma conexão ativa.
    */
   public void desconectar(){
      try{
         if(conexao != null){
            conexao.close();
         }
      
      }catch(SQLException e){
         e.printStackTrace();
      } 
   }

   /**
    * Realiza uma consulta no banco de dados conectado.
    * @param sql {@code String} contendo a query desejada.
    * @return resultado da consulta.
    */
   public ResultSet query(String sql){
      ResultSet res = null;

      try{
         Statement state = conexao.createStatement();
         res = state.executeQuery(sql);

      }catch(SQLException e){
         e.printStackTrace();
      }

      return res;
   }

   public void update(String sql){
      try{
         Statement state = conexao.createStatement();
         state.executeUpdate(sql);

      } catch (SQLException e){
         e.printStackTrace();
      }
  }
  

   /**
    * Verifica se o banco de dados possui uma conexão ativa.
    * @return {@code true} caso o banco de dados esteja conectado,
    * {@code false} caso contrário.
    */
   public boolean conectado(){
      try{
         return (conexao != null) && (!conexao.isClosed());
      }catch(SQLException e){}
   
      return false;
   }
}
