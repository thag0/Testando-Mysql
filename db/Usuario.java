package db;

import java.util.LinkedHashMap;

/**
 * Modelo de usuário do banco de dados.
 */
public class Usuario{

   /**
    * Informações do usuário.
    */
   private LinkedHashMap <String, String> infos = new LinkedHashMap<>();

   /**
    * Instância um novo usuário de acordo com a estrutura do banco de dados
    * @param nome nome do usuário.
    * @param cidade cidade do usuário.
    * @param senha senha do usuário.
    */
   public Usuario(String nome, String cidade, String senha){
      infos.put("nome", nome);
      infos.put("cidade", cidade);
      infos.put("senha", senha);
   }

   public Usuario(){
      this("", "", "");
   }

   /**
    * Retorna o nome do usuário.
    * @return nome do usuário.
    */
   public String nome(){
      return infos.get("nome");
   }

   /**
    * Retorna a cidade do usuário.
    * @return cidade do usuário.
    */
   public String cidade(){
      return infos.get("cidade");
   }

   /**
    * Retorna a senha do usuário.
    * @return senha do usuário.
    */
   public String senha(){
      return infos.get("senha");
   }

   /**
    * Retorna as chaves para os atributos do usuário.
    * @return atributos do usuário.
    */
   public String[] chaves(){
      return infos.keySet().toArray(new String[0]);
   }
}
