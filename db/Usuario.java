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
    * Instancia um novo usuário de acordo com a estrutura do banco de dados
    * @param nome nome do usuário.
    * @param cidade cidade do usuário.
    * @param senha senha do usuário.
    */
   public Usuario(String nome, String cidade, String senha){
      infos.put("nome", nome);
      infos.put("cidade", cidade);
      infos.put("senha", senha);
   }

   /**
    * Instancia um usuário com os campos vazios.
    */
   public Usuario(){
      this("", "", "");
   }

   /**
    * Retorna o valor contido na coluna da tabela de usuários.
    * @return valor da coluna especificada.
    */
   public String get(String coluna){
      String s = infos.get(coluna);
      return s == null ? "" : s;
   }

   /**
    * Retorna as chaves para os atributos do usuário.
    * @return atributos do usuário.
    */
   public String[] chaves(){
      return infos.keySet().toArray(new String[0]);
   }

   /**
    * Retorna os valores de cada atributo do usuário.
    * @return valores de atributo do usuário.
    */
   public String[] valores(){
      String[] v = new String[infos.size()];
      
      int i = 0;
      for(String chave : chaves()){
         v[i] = infos.get(chave);
         i++;
      }

      return v;
   }
}
