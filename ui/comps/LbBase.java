package ui.comps;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;

public class LbBase extends JLabel{
      private final int altura;
      private final int largura;
      Color corForeGround = new Color(255, 255, 255);
      Color corBackground = new Color(0, 0, 0);
      Color corSombra = new Color(20, 20, 20);
      int deslocSombra = 1;
      
      public LbBase(int altura, int largura, String texto){
         setBackground(corBackground);
         setForeground(corForeGround);
         
         setFont(getFont().deriveFont(Font.BOLD).deriveFont(11.5f));

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
