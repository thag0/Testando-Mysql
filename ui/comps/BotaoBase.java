package ui.comps;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JButton;

/**
 * Modelo base de botão dentro da aplicação.
 */
public class BotaoBase extends JButton{
   private final int altura;
   private final int largura;
   Color corBackground = new Color(30, 30, 30);
   Color corForeGround = new Color(200, 200, 200);

   final int borda = 30;
   
   public BotaoBase(int altura, int largura, String texto){
      setPreferredSize(new Dimension(largura, altura));
      setText(texto);
      setBackground(corBackground);
      setForeground(corForeGround);

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
