package cont;

import modelo.DadosSessao;
import ui.painel.PainelMenu;

public class ContPainelMenu{
   private PainelMenu pm;

   public ContPainelMenu(PainelMenu pm){
      this.pm = pm;
   }

   public void voltarLogin(){
      DadosSessao.getInstance().usuarioLogado = "";
      pm.redirecionar(pm, "login");
   }
}
