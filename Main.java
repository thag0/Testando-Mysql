import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

public class Main{
   static final String CAMINHO_PROPERTIES = "config.properties";

   public static void main(String[] args){
      
      Properties props = lerProperties(CAMINHO_PROPERTIES);
      String usuario = props.getProperty("db.usuario");
      String senha = props.getProperty("db.senha");
      String url = props.getProperty("db.url");
      Connection conexao = null;
      
      try{
         conexao = DriverManager.getConnection(url, usuario, senha);
         String query = "SELECT * FROM usuarios";
         ResultSet res = conexao.createStatement().executeQuery(query);

         while(res.next()){
            System.out.println(res.getString("nome"));
         }

      }catch(SQLException sql){
         sql.printStackTrace();
      
      }finally{
         if(conexao != null){
            try{
               conexao.close();
            }catch(Exception e){
               e.printStackTrace();
            }
         }
      }
   }

   static Properties lerProperties(String caminho){
      Properties props = new Properties();

      try(FileInputStream fis = new FileInputStream(caminho)){
         props.load(fis);

      }catch(Exception e){
         throw new RuntimeException(e);
      }

      return props;
   }
}