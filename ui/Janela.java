package ui;

import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import ui.painel.PainelBase;
import ui.painel.PainelLogin;
import ui.painel.PainelMenu;

public class Janela extends JFrame{
   PainelLogin pLogin;
   PainelMenu pMenu;

   final int minLargura;
   final int minAltura;
   final int escala = 40;
   
   final String CAMINHO_ICONE = "img/icone-janela.png";

   public Janela(int altura, int largura, String titulo){
      setTitle(titulo);
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

      minLargura = escala * 16;
      minAltura  = escala *  9;
      setMinimumSize(new Dimension(minLargura, minAltura));
      
      try{
         BufferedImage icone = ImageIO.read(new File(CAMINHO_ICONE));
         setIconImage(icone);
      }catch(Exception e){
         System.out.println(e);
      }

      initComps();

      add(pLogin);
      add(pMenu);

      setLayout(new CardLayout());//layout para os painéis
      
      pack();
      setLocationRelativeTo(null);

      setVisible(true);
      pLogin.setVisible(true);
   }

   /**
    * Inicialização dos componentes da janela da aplicação.
   */
   private void initComps(){
      pLogin = new PainelLogin(this, minAltura, minLargura);
      pMenu = new PainelMenu(this, minAltura, minLargura);
   }

   public void redirecionar(PainelBase base, String dest){
      base.desativar();

      dest = dest.toLowerCase();
      switch(dest){
         case "menu":
            pMenu.ativar();
         break;

         case "login":
            pLogin.ativar();
         break;
      
         default:
            System.out.println("Destino (" + dest + ") inválido");
         break;
      }
   }
}
