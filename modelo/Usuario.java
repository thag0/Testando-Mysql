package modelo;

/**
 * Modelo de usuário do banco de dados.
 */
public class Usuario{

   /**
    * Atributo padrão do usuário.
    */
   String nome, cidade, senha;

   /**
    * Permissão de administrador na aplicação.
    */
   boolean admin = false;

   /**
    * Instancia um novo usuário de acordo com a estrutura do banco de dados
    * @param nome nome do usuário.
    * @param cidade cidade do usuário.
    * @param senha senha do usuário.
    */
   public Usuario(String nome, String cidade, String senha){
      setNome(nome);
      setCidade(cidade);
      setSenha(senha);  
   }

   /**
    * Configura o nome do usuário caso o valor recebido seja válido.
    * @param nome novo nome do usuário.
    */
   public void setNome(String nome){
      if(nome != null){
         nome = nome.trim();
         if(!nome.isBlank() && !nome.isEmpty()){
            this.nome = nome;
         }
      
      }else{
         this.nome = "USUARIO_NOME";//nome padrão
      }
   }

   /**
    * Configura a cidade do usuário caso o valor recebido seja válido.
    * @param cidade nova cidade do usuário.
    */
   public void setCidade(String cidade){
      if(cidade != null){
         cidade = cidade.trim();
         if(!cidade.isBlank() && !cidade.isEmpty()){
            this.cidade = cidade;
         }
      
      }else{
         this.cidade = "USUARIO_CIDADE";//cidade padrão
      }
   }

   /**
    * Configura a senha do usuário caso o valor recebido seja válido.
    * @param senha nova senha do usuário.
    */
   public void setSenha(String senha){
      //adicionar suporte a criptografia
      //considerar melhores soluções para senha padrão

      if(senha != null){
         senha = senha.trim();
         if(!senha.isBlank() && !senha.isEmpty()){
            this.senha = senha;
         }
      
      }else{
         this.senha = "USUARIO_SENHA";//senha padrão
      }
   }

   /**
    * Configura a permissão de admnistrador do usuário.
    * @param admin permisão de administrador para o usuário.
    */
   public void setAdmin(boolean admin){
      this.admin = admin;
   }

   /**
    * Retorna o nome do usuário.
    * @return nome do usuário.
    */
   public String getNome(){
      return this.nome;
   }

   /**
    * Retorna a cidade do usuário.
    * @return cidade do usuário.
    */
   public String getCidade(){
      return this.cidade;
   }

   /**
    * Retorna senha do usuário.
    * @return senha do usuário.
    */
   public String getSenha(){
      return this.senha;
   }

   /**
    * Retorna a permissão de amnistrador do usuário.
    * @return permissão de amnistrador do usuário.
    */
   public boolean getAdmin(){
      return this.admin;
   }
}
