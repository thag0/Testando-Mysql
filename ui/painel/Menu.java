package ui.painel;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class Menu extends PainelBase{
   
   public Menu(int altura, int largura){
      super(altura, largura);
      configPadrao();

      setLayout(null);
   }

   @Override
   public void atualizar(){

   }

   @Override
   public void desenhar(){
      repaint();
   }

   @Override
   protected void paintComponent(Graphics g){
      super.paintComponent(g);
      Graphics2D g2 = (Graphics2D) g;

      g2.setColor(corTexto);
      g2.setFont(getFont().deriveFont(Font.BOLD));
      g2.setFont(getFont().deriveFont(20.f));
      FontMetrics fm = g2.getFontMetrics();

      String texto = "Testando alinhamento";
      float textoLargura = fm.stringWidth(texto);
      float textoAltura = fm.getHeight();
  
      float x = (getWidth()  - getInsets().left - getInsets().right - textoLargura) / 2;
      float y = (getHeight() - getInsets().top  - getInsets().bottom - textoAltura) / 2 + fm.getAscent();

      g2.drawString(texto, x, y);
  
      g2.dispose();
   }
}
