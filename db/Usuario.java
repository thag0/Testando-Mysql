package db;
/**
 * Modelo de usuário do banco de dados.
 */
public class Usuario{
   private String nome;

   public Usuario(String nome){
      this.nome = nome;
   }

   public String nome(){
      return this.nome;
   }
}
