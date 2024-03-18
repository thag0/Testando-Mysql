import java.sql.ResultSet;

import db.Database;
import ui.Janela;

public class Main{
   static final String CAMINHO_PROPERTIES = "database.properties";

   public static void main(String[] args){
      Database db = new Database();
      ResultSet res = null;
      DadosSessao sessao = new DadosSessao();
      
      boolean rodando = true;
      String op = "", entrada;
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

                  System.out.println("conectando...");
                  
                  if(db.conectar(CAMINHO_PROPERTIES)){
                     System.out.println("\nConexão estabelecida com: " + db.nome());
                  }
                  sessao.dbNome = db.nome();
                  menu.esperarTecla();
               break;

               case 2://desconectar database
                  System.out.print("desconectando...");
                  db.desconectar();
                  sessao.dbNome = "";//melhorar isso se der
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

                  entrada = menu.removerUsuario();

                  System.out.println();
                  if(db.update(entrada) > 0){
                     System.out.println("Usuário removido.");

                  }else{
                     System.out.println("Usuário não encontrado ou senha incorreta.");
                  }

                  menu.esperarTecla();
               break;
   
               case 5://fazer consulta
                  if(!sessao.dbConectado){
                     System.out.println("É necessário conexão com banco de dados.");
                     menu.esperarTecla();
                     break;
                  }

                  entrada = "SELECT * FROM usuarios";
                  res = db.query(entrada);
                  menu.imprimirConsulta(entrada, res);

                  menu.esperarTecla();
               break;

               case 6://fazer alteração
                  if(!sessao.dbConectado){
                     System.out.println("É necessário conexão com banco de dados.");
                     menu.esperarTecla();
                     break;
                  }

                  db.update((menu.fazerAlteracao()));
                  menu.esperarTecla();
               break;
   
               case 7://sair
                  System.out.println("Saindo");
                  rodando = false;
               break;
            }

            sessao.dbConectado = db.conectado();
            sessao.dbNome = db.nome();

         }catch(Exception e){
            e.printStackTrace();
            menu.esperarTecla();
         }
      }

      db.desconectar();//garantia
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