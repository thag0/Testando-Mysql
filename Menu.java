import java.io.BufferedReader;
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
      System.out.println("Menu");
      System.out.println("1 - Conectar ao database");
      System.out.println("2 - Desconectar database");
      System.out.println("3 - Adicionar usuário");
      System.out.println("4 - Remover usuário");
      System.out.println("5 - Fazer query");
      System.out.println("6 - Imprimir query");
      System.out.println("7 - Limpar query");
      System.out.println("8 - Sair");
      
      System.out.println("\nConectado: " + sessao.dbConectado);
      System.out.println("Query: " + sessao.resQuery);
      
      System.out.print("\nDigite uma opção: ");
   }

   public void conectarDatabase(){
      //...
   }

   public void desconectarDatabase(){
      //...
   }

   public String addUsuario(){
      System.out.print("Digite o nome do usuário: ");
      String nome = lerTeclado();
      Usuario usuario = new Usuario(nome);
      String sql = "INSERT INTO usuarios (nome) VALUES (\'" + usuario.nome() + "\')";
      return sql;
   }

   public String removerUsuario(){
      System.out.print("Digite o nome do usuário: ");
      String nome = lerTeclado();
      Usuario usuario = new Usuario(nome);
      String sql = String.format("DELETE FROM usuarios WHERE nome = \'%s\'", usuario.nome());
      return sql;    
   }

   public String fazerQuery(){
      System.out.print("Digite a query: ");
      String query = lerTeclado();
      return query;
   }

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
    * Captura a entrada do teclado.
    * @return string lida.
    */
   public String lerTeclado(){
		String s;

		try{
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			s = br.readLine();
		
		}catch(Exception e){
			s = "";
		}

		return s;
   }

   /**
    * Auxiliar
    */
    public void esperarTecla(){
      System.out.print("\nDigite qualquer tecla para continuar...");
      lerTeclado();
   }

}
