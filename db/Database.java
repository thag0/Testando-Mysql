package db;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/**
 * Interface simples para conexão com banco de dados.
 */
public class Database{
   static Database instancia;
   Connection conexao = null;
   String nome = "";

   /**
    * Interface para conexão com banco de dados.
    */
   private Database(){}

   /**
    * Retorna uma instância única do database para toda a aplicação.
    * @return {@code Database} responável pela conexão com banco de dados.
    */
   public static Database getInstance(){
      if(instancia == null){
         instancia = new Database();
      }

      return instancia;
   }

   /**
    * Tenta conectar ao banco de dados desejado.
    * @param caminho {@code String} contendo o caminho do arquivo de
    * propriedades do banco de dados.
    */
   public boolean conectar(String caminho){
      Properties props = lerProperties(caminho);
      try{
         String url = props.getProperty("db.url") + props.getProperty("db.nome");
         String usuario = props.getProperty("db.usuario");
         String senha = props.getProperty("db.senha");
         conexao = DriverManager.getConnection(url, usuario, senha);

      }catch(SQLException e){
         System.err.println("\nNão foi possível conectar ao banco de dados:");
         System.err.println(e.getClass().getSimpleName() + ": " + e.getMessage());
         System.err.println("\nVerifique o arquivo de propriedades do ambiente");
      }

      return conectado();
   }

   /**
    * Carrega os dados de propriedade necessários para conectar
    * no banco de dados.
    * @param caminho caminho do arquivo de propriedades do banco de dados.
    * @return propriedades lidas do arquivo.
    */
   private Properties lerProperties(String caminho){
      Properties props = new Properties();

      try(FileInputStream fis = new FileInputStream(caminho)){
         props.load(fis);

      }catch(Exception e){
         throw new RuntimeException(e);
      }

      return props;
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
    * @param parametros argumentos da query.
    * @return resultado da consulta.
    */
   public ResultSet query(String sql, String... parametros){
      ResultSet res = null;

      if(conectado()){
         try{
            PreparedStatement state = conexao.prepareStatement(sql);
            for(int i = 0; i < parametros.length; i++){
               state.setString(i+1, parametros[i]);
            }

            res = state.executeQuery();
   
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
    * @return quantidade de alterações feitas.
    */
   public int update(String sql){
      int alt = 0;

      if(conectado()){
         try{
            //TODO adicionar proteção contra sql injection
            Statement state = conexao.createStatement();
            alt = state.executeUpdate(sql);
   
         }catch(SQLException e){
            System.out.println("Erro ao executar update:");
            System.out.println(e.getClass().getSimpleName() + ": " + e.getMessage());
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
