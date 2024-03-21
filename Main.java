import java.awt.Dimension;
import java.sql.SQLException;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import db.DAOUsuario;
import db.Database;
import modelo.DadosSessao;
import ui.Janela;

public class Main{
   static final String CAMINHO_PROPERTIES = "database.properties";

   public static void main(String[] args){
      Database db = Database.getInstance();

      boolean terminal = false;
      
      if(terminal) rodarEmTerminal(db);
      else rodarEmJanela(db);

      db.desconectar();//garantia
   }
    
   /**
    * Abre um explorador de arquivos para buscar o caminho do arquivo
    * de propriedades do database.
    * @return caminho do arquivo {@code database.properties}.
    */
   static String caminhoPropriedadesDatabase(){
      JFileChooser fc = new JFileChooser();
      fc.setPreferredSize(new Dimension(600, 400));
      fc.setDialogTitle("Selecione o arquivo de propriedades do database");
      FileNameExtensionFilter filtro = new FileNameExtensionFilter("Arquivos de Propriedades (*.properties)", "properties");
      fc.setFileFilter(filtro);
      
      int confirmado = fc.showOpenDialog(null);
      if(confirmado == JFileChooser.APPROVE_OPTION){
         return fc.getSelectedFile().getAbsolutePath();
      
      }else{
         return null;
      }
   }

   /**
    * Testando ainda
    */
   static void rodarEmJanela(Database db){
      // String caminhoProperties = caminhoPropriedadesDatabase();
      String caminhoProperties = CAMINHO_PROPERTIES;//temp

      if(caminhoProperties != null){
         try{
            db.conectar(caminhoProperties);
            Janela janela = new Janela(600, 400, "Conectado em: " + db.nome());
            
            while(janela.isEnabled()){
               //loop pra segurar a execução do programa
            }
         
         }catch(SQLException e){
            StringBuilder sb = new StringBuilder();
            sb.append("Erro ao tentar ler o arquivo: ");
            sb.append(e.getClass().getSimpleName()).append(": ").append(e.getMessage());
            sb.append("Verifique se o arquivo selecionado é um arquivo de propriedades válido.");

            JOptionPane.showMessageDialog(
               null, 
               sb.toString(), 
               "Formato de arquivo inválido", 
               JOptionPane.PLAIN_MESSAGE
            );
         }
      
      }else{
         JOptionPane.showMessageDialog(
            null, 
            "Nenhum arquivo de propriedades para o banco de dados selecionado", 
            "Arquivo não informado", 
            JOptionPane.PLAIN_MESSAGE
         );
         return;
      }
   }

   /**
    * Execução padrão e mais básica.
    */
   static void rodarEmTerminal(Database db){
      DAOUsuario daoUser = new DAOUsuario();
      DadosSessao sessao = DadosSessao.getInstance();

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

                  try{
                     if(db.conectar(CAMINHO_PROPERTIES)){
                        System.out.println("\nConexão estabelecida com: " + db.nome());
                     }
                  }catch(SQLException e){
                     System.out.println("Não foi possível conectar ao banco de dados");
                     System.out.println("Verifique o arquive de propriedades");
                     System.out.println("Erro: " + e.getMessage());
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

                  if(daoUser.inserir(menu.addUsuario())){
                     System.out.println("Usuário adicionado.");
                  
                  }else{
                     System.out.println("Não foi possível adicionar o usuário solicitado.");
                  }

                  menu.esperarTecla();
               break;

               case 4://remover usuario
                  if(!sessao.dbConectado){
                     System.out.println("É necessário conexão com banco de dados.");
                     menu.esperarTecla();
                     break;
                  }

                  System.out.println();
                  if(daoUser.deletar(menu.removerUsuario())){
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
                  menu.imprimirConsulta(entrada, db.query(entrada));

                  menu.esperarTecla();
               break;

               case 6://fazer alteração
                  if(!sessao.dbConectado){
                     System.out.println("É necessário conexão com banco de dados.");
                     menu.esperarTecla();
                     break;
                  }

                  // db.update((menu.fazerAlteracao()));
                  System.out.println("Não implementado");
                  menu.esperarTecla();
               break;
   
               case 7://sair
                  System.out.println("Saindo");
                  rodando = false;
               break;
            }

            sessao.dbConectado = db.conectado();
            sessao.dbNome = db.nome();

         }catch(Exception excep){
            excep.printStackTrace();
            menu.esperarTecla();
         }
      }
   }
}