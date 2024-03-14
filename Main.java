import java.io.FileInputStream;
import java.sql.ResultSet;
import java.util.Properties;

import ui.Janela;

public class Main{

   static final String CAMINHO_PROPERTIES = "config.properties";

   public static void main(String[] args){      
      Properties props = lerProperties(CAMINHO_PROPERTIES);
      Database db = new Database();
      ResultSet res = null;
      DadosSessao sessao = new DadosSessao();
      
      boolean rodando = true;
      String op = "";
      Menu menu = new Menu();
      while(rodando){
         menu.print(sessao);
         op = menu.lerTeclado();
         System.out.println();

         try{
            int opNum = Integer.parseInt(op);
            switch(opNum){
               case 1://conectar database
                  if(sessao.dbConectado) break;

                  System.out.print ("conectando...");
                  db.conectar(
                     props.getProperty("db.url"), 
                     props.getProperty("db.usuario"), 
                     props.getProperty("db.senha")
                  );
               break;

               case 2://desconectar database
                  System.out.print("desconectando...");
                  db.desconectar();
               break;
   
               case 3://add usuario
                  if(!sessao.dbConectado){
                     System.out.println("É necessário conexão com banco de dados.");
                     menu.esperarTecla();
                     break;
                  }

                  db.update(menu.addUsuario());
                  menu.esperarTecla();
               break;

               case 4://remover usuario
                  if(!sessao.dbConectado){
                     System.out.println("É necessário conexão com banco de dados.");
                     menu.esperarTecla();
                     break;
                  }

                  db.update(menu.removerUsuario());
                  menu.esperarTecla();
               break;
   
               case 5://fazer query
                  if(!sessao.dbConectado){
                     System.out.println("É necessário conexão com banco de dados.");
                     menu.esperarTecla();
                     break;
                  }

                  res = db.query(menu.fazerQuery());
                  menu.esperarTecla();
               break;
   
               case 6://imprimir query
                  if(res == null){
                     System.out.println("Nenhum resultado de query encontrado");
                     menu.esperarTecla();
                     break;
                  }

                  menu.imprimirQuery(res);

                  menu.esperarTecla();
               break;

               case 7://limpar query
                  res = null;
               break;
   
               case 8://sair
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
            menu.esperarTecla();
         }

      }

      db.desconectar();//garantia
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
    * Testando ainda
    */
   static void rodarEmJanela(){
      Janela janela = new Janela(600, 400, "JavaSql");

      while(janela.isEnabled()){
         janela.painelAtual().atualizar();
         janela.painelAtual().desenhar();

         try{
            Thread.sleep(50);
         }catch(InterruptedException e){
            e.printStackTrace();
         }
      }

      janela.dispose();
      System.exit(0);
   }

}