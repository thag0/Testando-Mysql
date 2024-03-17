import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

import db.Usuario;
import ged.Ged;

public class Menu{
   Ged ged = new Ged();
   
   /**
    * Menu básico via terminal
    */
   public void print(DadosSessao sessao){
      ged.limparConsole();

      StringBuilder sb = new StringBuilder();
      
      sb.append("\tMenu\n").append("\n");
      sb.append("1 - Conectar ao database").append("\n");
      sb.append("2 - Desconectar database").append("\n");
      sb.append("3 - Adicionar usuário").append("\n");
      sb.append("4 - Remover usuário").append("\n");
      sb.append("5 - Fazer query").append("\n");
      sb.append("6 - Imprimir query").append("\n");
      sb.append("7 - Limpar query").append("\n");
      sb.append("8 - Sair").append("\n");
      
      sb.append("\nConectado: " + sessao.dbConectado);
      sb.append(sessao.dbConectado ? " (" + sessao.dbNome +")" : "");
      sb.append("\n");
      
      sb.append("Query: " + sessao.resQuery).append("\n");
      
      System.out.println(sb.toString());

      System.out.print("Digite uma opção: ");
   }

   /**
    * Adiciona um novo usuário ao banco de dados.
    * @return sql contendo a query para adição.
    */
   public String addUsuario(){
      System.out.print("Digite o nome do usuário: ");
      String nome = lerTeclado();
      Usuario usuario = new Usuario(nome);
      String sql = "INSERT INTO usuarios (nome) VALUES (\'" + usuario.nome() + "\')";
      return sql;
   }

   /**
    * Remove um usuário ao banco de dados.
    * @return sql contendo a query para remoção.
    */
   public String removerUsuario(){
      System.out.print("Digite o nome do usuário: ");
      String nome = lerTeclado();
      Usuario usuario = new Usuario(nome);
      String sql = String.format("DELETE FROM usuarios WHERE nome = \'%s\'", usuario.nome());
      return sql;    
   }

   /**
    * Realiza uma query qualquer digitada pelo usuário.
    * @return query lida.
    */
   public String fazerQuery(){
      System.out.print("Digite a query: ");
      String query = lerTeclado();
      return query;
   }

   /**
    * 
    * @param res
    */
   public void imprimirQuery(ResultSet res){
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
   }

   /**
    * Captura a entrada do teclado digitada pelo usuário.
    * @return string lida.
    */
   public String lerTeclado(){
		String s = "";

		try{
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			s = br.readLine();
		
		}catch(IOException e){
			e.printStackTrace();
		}

		return s;
   }

   /**
    * Auxiliar para segurar o fluxo de execução do programa.
    */
    public void esperarTecla(){
      System.out.print("\nDigite qualquer tecla para continuar...");
      lerTeclado();
   }

}
