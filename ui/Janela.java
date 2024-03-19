package ui;

import java.awt.Dimension;

import javax.swing.JFrame;

import ui.painel.PainelLogin;
import ui.painel.PainelBase;

public class Janela extends JFrame{
   PainelLogin menu;

   final int minLargura;
   final int minAltura;
   final int escala = 40;
   
   public Janela(int altura, int largura, String titulo){
      setTitle(titulo);
      setVisible(true);
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

      minLargura = escala * 16;
      minAltura  = escala *  9;
      setMinimumSize(new Dimension(minLargura, minAltura));
      
      menu = new PainelLogin(altura, largura);
      menu.setVisible(true);
      add(menu);
      
      pack();
      setLocationRelativeTo(null);
   }

   public PainelBase painelAtual(){
      return menu;//temp
   }
}
