package contr;

import modelo.DadosSessao;
import ui.painel.PainelMenu;

public class ContPainelMenu{
   private PainelMenu painel;
   DadosSessao sessao = DadosSessao.getInstance();

   public ContPainelMenu(PainelMenu pm){
      this.painel = pm;
   }

   public DadosSessao getSessao(){
      return sessao;
   }

   public void voltarParaLogin(){
      sessao.setUsuarioLogado("");
      painel.redirecionar(painel, "login");
   }
}
