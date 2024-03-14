package ui.painel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;

import javax.swing.JPanel;

public abstract class PainelBase extends JPanel{
   float largura;
   float altura;

   Color corBackground = new Color(25, 25, 25);
   Color corTexto = new Color(200, 200, 200);

   protected PainelBase(int altura, int largura){
      this.altura  = (float)altura;
      this.largura = (float)largura;
      
      setPreferredSize(new Dimension(altura, largura));
   }

   protected void configPadrao(){
      setVisible(false);
      setFocusable(false);
      setBackground(corBackground);
   }

   protected float textoLargura(Graphics2D g2, String str){
      return (float) g2.getFontMetrics().getStringBounds(str, g2).getWidth();
   }

   protected float textoAltura(Graphics2D g2, String str){
      return (float) g2.getFontMetrics().getStringBounds(str, g2).getHeight();
   }

   public abstract void atualizar();

   public abstract void desenhar();

}
