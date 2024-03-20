package ui.painel;

import java.awt.Color;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import cont.ContPainelMenu;
import ui.Janela;
import ui.comps.BtBase;
import ui.comps.LbBase;

public class PainelMenu extends PainelBase{
   Janela janela;
   ContPainelMenu controlador = new ContPainelMenu(this);

   LbBase lbTitulo;
   BtBase botaoVoltar;

   public PainelMenu(Janela janela, int altura, int largura){
      super(altura, largura);
      configPadrao();
      setLayout(null);

      this.janela = janela;
      initComps();

      add(lbTitulo);
      add(botaoVoltar);

      // redimensionar dinamicamente os componentes
      addComponentListener(new ComponentAdapter(){
         public void componentResized(ComponentEvent e){
            botaoVoltar.setBounds(
               0,
               getHeight() - botaoVoltar.altura(),
               botaoVoltar.largura(), botaoVoltar.altura()         
            );

            lbTitulo.setBounds(
               0, 0,
               lbTitulo.largura(), lbTitulo.altura()         
            );

         }
      });
   }

   private void initComps(){
      lbTitulo = new LbBase(40, 100, "Menu");
      lbTitulo.setFont(getFont().deriveFont((float)lbTitulo.largura() * 0.35f));
      lbTitulo.setOpaque(true);
      lbTitulo.setBackground(Color.white);
      lbTitulo.setForeground(Color.black);

      botaoVoltar = new BtBase(30, 80, "Voltar");
      botaoVoltar.addActionListener(e -> {
         controlador.voltarLogin();
      });
   }

   @Override
   public void redirecionar(PainelBase base, String dest){
      janela.redirecionar(base, dest);
   }
}
