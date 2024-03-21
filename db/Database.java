package db;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException; 
import java.util.Properties;

/**
 * Interface simples para conexão com banco de dados.
 */
public class Database{

   /**
    * Instância única de um Database.
    */
   static Database instancia;

   /**
    * Conexão com o banco de dados sql.
    */
   Connection conexao = null;

   /**
    * Nome do banco de dados conectado, apenas para estética da aplicação.
    */
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
    * <p>
    *    O arquivo de propriedades deve conter as seguintes chaves:
    * </p>
    * <p>
    *    {@code db.url} = url de conexão com banco de dados.
    * </p>
    * <p>
    *    {@code db.nome} = nome do banco de dados desejado.
    * </p>
    * <p>
    *    {@code db.usuario} = nome do usuário do banco de dados onde 
    *    onde a conexão está sendo feita.
    * </p>
    * <p>
    *    {@code db.senha} = senha do usuário.
    * </p>
    * @param caminho {@code String} contendo o caminho do {@code arquivo de
    * propriedades} do banco de dados.
    * @throws SQLException se ocorrer algum erro na tentativa de conexão.
    * @throws IllegalArgumentException se alguma chave de propriedade não for encontrada
    */
   public void conectar(Properties props) throws SQLException, IllegalArgumentException{
      String url = props.getProperty("db.url");
      if(url == null){
         throw new IllegalArgumentException("\'db.url\' não encontrado.");
      }

      String nome = props.getProperty("db.nome");
      if(nome == null){
         throw new IllegalArgumentException("\'db.nome\' não encontrado.");
      }

      String usuario = props.getProperty("db.usuario");
      if(usuario == null){
         throw new IllegalArgumentException("\'db.usuario\' não encontrado.");
      }

      String senha = props.getProperty("db.senha");
      if(senha == null){
         throw new IllegalArgumentException("\'db.senha\' não encontrado.");
      }
      
      conexao = DriverManager.getConnection(url + nome, usuario, senha);
   }

   /**
    * Verifica se o banco de dados possui uma conexão ativa.
    * @return {@code true} caso o banco de dados esteja conectado,
    * {@code false} caso contrário.
    */
   public boolean conectado(){
      try{
         return (conexao != null) && (!conexao.isClosed());
      
      }catch(SQLException e){
         return false;
      }
   }

   /**
    * Desconecta o banco de dados caso ele possua uma conexão ativa.
    * @throws SQLException caso ocorra algum erro de acesso ao banco
    * de dados.
    */
   public void desconectar() throws SQLException{
      if(conectado()){
         conexao.close();
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
    * @param parametros argumentos da atualização.
    * @return quantidade de alterações feitas.
    */
   public int update(String sql, String... parametros){
      int alts = 0;
   
      if(conectado()){
         try{
            PreparedStatement state = conexao.prepareStatement(sql);
            for(int i = 0; i < parametros.length; i++){
               state.setString(i+1, parametros[i]);
            }
   
            alts = state.executeUpdate();
      
         }catch(SQLException e){
            System.out.println("Erro ao executar update:");
            System.out.println(e.getClass().getSimpleName() + ": " + e.getMessage());
         }
      }
   
      return alts;
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
