package ui;

import javax.swing.JFrame;

import ui.painel.Menu;
import ui.painel.PainelBase;

public class Janela extends JFrame{
   Menu menu;
   
   public Janela(int altura, int largura, String titulo){
      setTitle(titulo);
      setVisible(true);
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      setResizable(false);
      
      menu = new Menu(altura, largura);
      menu.setVisible(true);
      add(menu);
      
      pack();
      setLocationRelativeTo(null);
   }

   public PainelBase painelAtual(){
      return menu;//temp
   }
}
