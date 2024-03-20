package cont;

import ui.painel.PainelMenu;

public class ContPainelMenu{
   private PainelMenu pm;

   public ContPainelMenu(PainelMenu pm){
      this.pm = pm;
   }

   public void voltarLogin(){
      pm.redirecionar(pm, "login");
   }
}
