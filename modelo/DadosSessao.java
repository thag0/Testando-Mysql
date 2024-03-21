package modelo;
/**
 * Auxilar para guardar informações da sessão atual.
 */
public class DadosSessao{

   /**
    * Instância única da sessão.
    */
   private static DadosSessao instancia = null;

   /**
    * Nome do banco de dados conectado.
    */
   private String dbNome = "";

   /**
    * Conexão com banco de dados estabelecida.
    */
   private boolean dbConectado = false;

   /**
    * Nome do usuário logado na sesão.
    */
   private String usuarioLogado = "";

   /**
    * Construtor privado pra usar a instância única.
    */
   private DadosSessao(){}

   /**
    * Retorna uma instância única dos dados da sessão atual.
    * @return dados da sessão.
    */
   public static DadosSessao getInstance(){
      if(instancia == null){
         instancia = new DadosSessao();
      }

      return instancia;
   }

   /**
    * Configura o novo nome para o banco de dados conectado
    * na sessão.
    * @param nome nome do banco de dados.
    */
   public void setDBNome(String nome){
      if(nome != null){
         this.dbNome = nome;
      }
   }

   /**
    * Retorna o nome do banco de dados conectado na sessão.
    * @return nome do banco de dados conectado.
    */
   public String getDBNome(){
      return this.dbNome;
   }

   /**
    * Configura o novo valor para a conexão estabelecida entre
    * o banco de dados para a sessão.
    * @param conectado {@code true} caso o banco de dados esteja conectado,
    * {@code false} caso contrário.
    */
   public void setDBConectado(boolean conectado){
      this.dbConectado = conectado;
   }

   /**
    * Retorna o status de conexão do banco de dados salvo na sessão.
    * @return valor do banco de dados conectado da sessão.
    */
   public boolean getDBConectado(){
      return this.dbConectado;
   }

   /**
    * Configura o novo nome do atual usuário conectado na sessão.
    * @param usuario nome do usuário conectado.
    */
   public void setUsuarioLogado(String usuario){
      if(usuario != null){
         this.usuarioLogado = usuario;
      }
   }

   /**
    * Retorna o nome do usuário logado na sessão.
    * @return nome do usuário logado.
    */
   public String getUsuarioLogado(){
      return this.usuarioLogado;
   }
}
