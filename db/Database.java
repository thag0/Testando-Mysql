package db;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Interface simples para conexão com banco de dados.
 */
public class Database{
   Connection conexao = null;
   String nome = "";

   /**
    * Interface para conexão com banco de dados.
    */
   public Database(){}

   /**
    * Tenta conectar ao banco de dados desejado.
    * @param url 
    * @param usuario nome do usuário do banco de dados.
    * @param senha senha do usuário.
    */
   public void conectar(String url, String usuario, String senha){
      try{
         conexao = DriverManager.getConnection(url, usuario, senha);
      
      }catch(SQLException e){
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

   /**
    * Desconecta o banco de dados caso ele possua uma conexão ativa.
    */
   public void desconectar(){
      if(conectado()){
         try{
            conexao.close();
         
         }catch(SQLException e){
            e.printStackTrace();
         } 
      }
   }

   /**
    * Realiza uma consulta no banco de dados conectado.
    * @param sql {@code String} contendo a query desejada.
    * @return resultado da consulta.
    */
   public ResultSet query(String sql){
      ResultSet res = null;

      if(conectado()){
         try{
            Statement state = conexao.createStatement();
            res = state.executeQuery(sql);
   
         }catch(SQLException e){
            System.out.println("Erro ao executar consulta:");
            System.out.println(e.getClass().getSimpleName() + ": " + e.getMessage());
         }
      }

      return res;
   }

   /**
    * Realiza operações de atualização dentro do banco de dados, essas operações
    * incluem {@code INSERT}, {@code DELETE} ou {@code UPDATE}.
    * @param sql {@code String} contendo a query desejada.
    */
   public int update(String sql){
      int alt = 0;

      if(conectado()){
         try{
            Statement state = conexao.createStatement();
            alt = state.executeUpdate(sql);
   
         }catch(SQLException e){
            e.printStackTrace();
         }
      }

      return alt;
   }

   /**
    * Retorna o nome do banco de dados caso ele esteja conectado.
    * @return {@code String} contendo o nome do banco de dados.
    */
   public String nome(){
      String s = "";

      if(conectado()){
         try{
            DatabaseMetaData dmd = conexao.getMetaData();
            String url = dmd.getURL();
            s = url.substring(url.lastIndexOf("/") + 1);
            
         }catch(SQLException e){}
      }

      return (s.equals("")) ? "N/A" : s;
   }
}
