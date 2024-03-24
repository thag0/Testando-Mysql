import java.awt.Dimension;
import java.io.FileInputStream;
import java.sql.SQLException;
import java.util.Properties;

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

      try{
         db.desconectar();//garantia
      }catch(SQLException e){
         System.out.println(e);
      }
   }
    
   /**
    * Abre um explorador de arquivos para buscar o caminho do arquivo
    * de propriedades do database.
    * @return caminho do arquivo {@code database.properties}.
    */
   static String buscarCaminhoDatabase(){
      JFileChooser fc = new JFileChooser();
      fc.setPreferredSize(new Dimension(600, 400));
      fc.setDialogTitle("Selecione o arquivo de propriedades do database");

      String desc = "Arquivos de Propriedades (*.properties)";
      String extensao = "properties";
      fc.setFileFilter(new FileNameExtensionFilter(desc, extensao));
      
      if(fc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION){
         return fc.getSelectedFile().getAbsolutePath();
      
      }else{
         return null;
      }
   }

   /**
    * Carrega os dados de propriedade necessários para conectar
    * no banco de dados.
    * @param caminho caminho do arquivo de propriedades do banco de dados.
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
   static void rodarEmJanela(Database db){
      String caminhoPropriedades = buscarCaminhoDatabase();

      if(caminhoPropriedades == null){
         JOptionPane.showMessageDialog(
            null, 
            "Nenhum arquivo de propriedades para o banco de dados selecionado", 
            "Arquivo não informado", 
            JOptionPane.PLAIN_MESSAGE
         );
         return;
      }

      Properties propsDatabase = lerProperties(caminhoPropriedades);
      
      try{
         db.conectar(propsDatabase);
         Janela janela = new Janela(600, 400, "Conectado em: " + db.nome());
         
         while(janela.isEnabled()){
            //loop pra segurar a execução do programa
         }
      
      }catch(SQLException e){
         StringBuilder sb = new StringBuilder();
         sb.append("Não foi possível estabelecer conexão: ");
         sb.append(e.getMessage()).append("\n");
         sb.append("Verifique se o arquivo selecionado contém os valores corretos.");

         JOptionPane.showMessageDialog(
            null, 
            sb.toString(), 
            "Erro de conexão", 
            JOptionPane.PLAIN_MESSAGE
         );

      }catch(IllegalArgumentException il){
         StringBuilder sb = new StringBuilder();
         sb.append("Falha ao ler as propriedades do arquivo: ");
         sb.append(il.getMessage()).append("\n");
         sb.append("Verifique se o arquivo selecionado possui as propriedades requeridas.");

         JOptionPane.showMessageDialog(
            null, 
            sb.toString(), 
            "Formato de arquivo inválido", 
            JOptionPane.PLAIN_MESSAGE
         );
      }
   }
   
   /**
    * Execução padrão e mais básica.
    */
   static void rodarEmTerminal(Database db){
      DAOUsuario daoUser = new DAOUsuario();
      DadosSessao sessao = DadosSessao.getInstance();
      Properties propsDatabase = lerProperties(CAMINHO_PROPERTIES);

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
                  if(sessao.getDBConectado()) break;

                  try{
                     db.conectar(propsDatabase);

                  }catch(SQLException e){
                     System.out.println("Não foi possível conectar ao banco de dados");
                     System.out.println("Verifique o arquivo de propriedades");
                     System.out.println("Erro: " + e.getMessage());
                  }

                  sessao.setDBNome(db.nome());;
                  menu.esperarTecla();
               break;

               case 2://desconectar database
                  System.out.print("desconectando...");
                  db.desconectar();
                  sessao.setDBNome("");//melhorar isso se der
               break;
   
               case 3://add usuario
                  if(!sessao.getDBConectado()){
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
                  if(!sessao.getDBConectado()){
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
                  if(!sessao.getDBConectado()){
                     System.out.println("É necessário conexão com banco de dados.");
                     menu.esperarTecla();
                     break;
                  }

                  entrada = "SELECT * FROM usuarios";
                  menu.imprimirConsulta(entrada, db.query(entrada));

                  menu.esperarTecla();
               break;

               case 6://fazer alteração
                  if(!sessao.getDBConectado()){
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

            sessao.setDBConectado(db.conectado());
            sessao.setDBNome(db.nome());

         }catch(Exception excep){
            excep.printStackTrace();
            menu.esperarTecla();
         }
      }
   }
}