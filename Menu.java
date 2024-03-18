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
      sb.append("5 - Consultar usuários").append("\n");
      sb.append("6 - Fazer alteração").append("\n");
      sb.append("7 - Sair").append("\n");
      
      sb.append("\nConectado: " + sessao.dbConectado);
      sb.append(sessao.dbConectado ? " (" + sessao.dbNome +")" : "");
      sb.append("\n");
      
      System.out.println(sb.toString());

      System.out.print("Digite uma opção: ");
   }

   /**
    * Adiciona um novo usuário ao banco de dados.
    * @return sql contendo a query para adição.
    */
   public String addUsuario(){
      System.out.println("Informações do usuário.");

      Usuario usuario = new Usuario();
      String[] chaves = usuario.chaves();
      String[] valores = new String[chaves.length];

      for(int i = 0; i < chaves.length; i++){
         System.out.print(chaves[i] + ": ");
         valores[i] = "\'" + lerTeclado() + "\'";
      }

      StringBuilder sb = new StringBuilder();

      sb.append("INSERT INTO usuarios (");
         sb.append(chaves[0]);
         for(int i = 1; i < chaves.length; i++){
            sb.append(", ").append(chaves[i]);
         }
      sb.append(") ");

      sb.append("VALUES (");
         sb.append(valores[0]);
         for(int i = 1; i < valores.length; i++){
            sb.append(", ").append(valores[i]);
         }
      sb.append(")");

      return sb.toString();
   }

   /**
    * Remove um usuário ao banco de dados.
    * @return sql contendo a query para remoção.
    */
   public String removerUsuario(){
      System.out.println("Informações para remoção");
      
      System.out.print("Nome: ");
      String nome = "\'" + lerTeclado() + "\'";
      
      System.out.print("Senha: ");
      String senha = "\'" + lerTeclado() + "\'";

      StringBuilder sb = new StringBuilder();
      sb.append("DELETE FROM usuarios WHERE ");
      sb.append("nome = ").append(nome).append(" AND ");
      sb.append("senha = ").append(senha);

      return sb.toString();
   }

   /**
    * Realiza uma query qualquer digitada pelo usuário.
    * @return query lida.
    */
   public String fazerAlteracao(){
      System.out.print("Query de alteração: ");
      String query = lerTeclado();
      return query;
   }

   /**
    * 
    * @param sql
    * @param res
    */
   public void imprimirConsulta(String sql, ResultSet res){
      String pad = "   ";
      
      try{
         ResultSetMetaData metaData = res.getMetaData();
         int numColunas = metaData.getColumnCount();
         
         ged.limparConsole();
         System.out.println("Usuários registrados\n");
         
         while(res.next()){
            for(int i = 1; i <= numColunas; i++){
               String nomeColuna = metaData.getColumnName(i);
               String valorColuna = res.getString(i);
               System.out.print(nomeColuna + ": " + valorColuna + pad);
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
