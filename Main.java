import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.Properties;

import ged.Ged;

public class Main{
   static Ged ged = new Ged();

   static final String CAMINHO_PROPERTIES = "config.properties";

   public static void main(String[] args){
      Properties props = lerProperties(CAMINHO_PROPERTIES);
      String usuario = props.getProperty("db.usuario");
      String senha = props.getProperty("db.senha");
      String url = props.getProperty("db.url");
      
      Database db = new Database();
      ResultSet res = null;
      DadosSessao sessao = new DadosSessao();;
      
      boolean rodando = true;
      String op = "";
      while(rodando){
         printMenu(sessao);
         op = lerTeclado();
         System.out.println();

         try{
            int opNum = Integer.parseInt(op);
            switch(opNum){
               case 1:
                  System.out.println("conectando...");
                  db.conectar(url, usuario, senha);
               break;

               case 2:
                  System.out.println("desconectando...");
                  db.desconectar();
               break;
   
               case 3:
                  if(!sessao.dbConectado){
                     System.out.println("É necessário conexão com banco de dados.");
                     esperarTecla();
                     break;
                  }

                  System.out.print("Digite a query: ");
                  String query = lerTeclado();
                  res = db.query(query);
                  esperarTecla();
               break;
   
               case 4:
                  res = null;
               break;
   
               case 5:
                  if(res == null){
                     System.out.println("Nenhum resultado de query encontrado");
                     esperarTecla();
                     break;
                  }

                  try{
                     ResultSetMetaData metaData = res.getMetaData();
                     int numColunas = metaData.getColumnCount();
             
                     while(res.next()){
                        for(int i = 1; i <= numColunas; i++){
                           String nomeColuna = metaData.getColumnName(i);
                           String valorColuna = res.getString(i);
                           System.out.print(nomeColuna + ": " + valorColuna + "  ");
                        }
                        System.out.println();
                     }
                  }catch(Exception e){}

                  esperarTecla();
               break;
   
               case 6:
                  System.out.println("Saindo");
                  rodando = false;
               break;
            
               default:
                  System.out.println("Opção inválida.");
            }

            sessao.dbConectado = db.conectado();
            sessao.resQuery = res != null;

         }catch(Exception e){
            e.printStackTrace();
            esperarTecla();
         }

      }

      db.desconectar();//garantia
   }


   /**
    * Menu básico via terminal
    */
   static void printMenu(DadosSessao sessao){
      ged.limparConsole();
      System.out.println("Menu");
      System.out.println("1 - Conectar ao database");
      System.out.println("2 - Desconectar database");
      System.out.println("3 - Fazer query");
      System.out.println("4 - Limpar query");
      System.out.println("5 - Imprimir query");
      System.out.println("6 - Sair");
      
      System.out.println("\nConectado: " + sessao.dbConectado);
      System.out.println("Query: " + sessao.resQuery);
      
      System.out.print("\nDigite uma opção: ");
   }

   /**
    * Carrega os dados de propriedade necessários para conectar
    * no banco de dados.
    * @param caminho caminho do arquivo de configurações.
    * @return propriedades lidas do arquivo.
    */
   static Properties lerProperties(String caminho){
      Properties props = new Properties();

      try(FileInputStream fis = new FileInputStream(caminho)){
         props.load(fis);

      }catch(Exception e){
         throw new RuntimeException(e);
      }

      return props;
   }

   /**
    * Auxiliar
    */
   static void esperarTecla(){
      System.out.print("\nDigite qualquer tecla para continuar...");
      lerTeclado();
   }

   /**
    * Captura a entrada do teclado.
    * @return string lida.
    */
   static String lerTeclado(){
		String s;

		try{
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			s = br.readLine();
		
		}catch(Exception e){
			s = "";
		}

		return s;
   }

}