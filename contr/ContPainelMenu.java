package contr;

import modelo.DadosSessao;
import ui.painel.PainelMenu;

public class ContPainelMenu{
   private PainelMenu pm;
   DadosSessao sessao = DadosSessao.getInstance();

   public ContPainelMenu(PainelMenu pm){
      this.pm = pm;
   }

   public DadosSessao getSessao(){
      return sessao;
   }

   public void voltarLogin(){
      DadosSessao.getInstance().setUsuarioLogado("");;
      pm.redirecionar(pm, "login");
   }
}
