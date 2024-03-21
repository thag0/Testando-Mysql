package ui.comps;

import java.awt.Color;

import javax.swing.JPasswordField;

public class TxtSenhaBase extends JPasswordField{
   private final int altura;
   private final int largura;
   Color corBackground = new Color(255, 255, 255);
   Color corForeGround = new Color(0, 0, 0);
   
   public TxtSenhaBase(int altura, int largura, String texto){
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