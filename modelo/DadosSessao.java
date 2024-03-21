package modelo;
/**
 * Auxilar para guardar informações da sessão atual.
 */
public class DadosSessao{
   static DadosSessao instancia = null;

   public String dbNome = "";
   public boolean dbConectado;
   public String usuarioLogado = "";

   private DadosSessao(){}

   public static DadosSessao getInstance(){
      if(instancia == null){
         instancia = new DadosSessao();
      }

      return instancia;
   }
}
