package ui.painel;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;

public abstract class PainelBase extends JPanel{
   float largura;
   float altura;

   Color corBackground = new Color(25, 25, 25);
   Color corTexto = new Color(200, 200, 200);

   protected PainelBase(int altura, int largura){
      this.altura  = (float)altura;
      this.largura = (float)largura;
      
      setPreferredSize(new Dimension(largura, altura));
      setDoubleBuffered(true);
   }

   protected void configPadrao(){
      setVisible(false);
      setFocusable(false);
      setBackground(corBackground);
   }

   /**
    * Torna o painel ativo, deixando ele visível.
    */
   public void ativar(){
      setVisible(true);
      setFocusable(true);
   }

   /**
    * Torna o painel inativo, deixando ele invisível.
    */
   public void desativar(){
      setVisible(false);
      setFocusable(false);
   }

   /**
    * Redireciona o painel para o destino.
    * <p>
    *    Redirecionar também quer dizer que o painel base
    *    será desativado, ficando invisível e inativo enquanto
    *    o painel de destino ficará ativo.
    * </p>
    * @param base painel base.
    * @param dest painel destino do redirecionamento.
    */
   public abstract void redirecionar(PainelBase base, String dest);
}
