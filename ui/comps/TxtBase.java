package ui.comps;

import java.awt.Color;

import javax.swing.JTextField;

public class TxtBase extends JTextField{
   private final int altura;
   private final int largura;
   Color corBackground = new Color(255, 255, 255);
   Color corForeGround = new Color(0, 0, 0);
   
   public TxtBase(int altura, int largura, String texto){
      setBackground(corBackground);
      setForeground(corForeGround);
      setText(texto);

      setVisible(true);
      this.altura = altura;
      this.largura = largura;
   }

   public int altura(){
      return altura;
   }

   public int largura(){
      return largura;
   }
}
