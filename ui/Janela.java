package ui;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import ui.painel.PainelLogin;

public class Janela extends JFrame{
   PainelLogin pLogin;

   final int minLargura;
   final int minAltura;
   final int escala = 40;
   
   final String CAMINHO_ICONE = "img/icone-janela.png";

   public Janela(int altura, int largura, String titulo){
      setTitle(titulo);
      setVisible(true);
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
      
      pack();
      setLocationRelativeTo(null);
   }

   /**
    * Inicialização dos componentes da janela da aplicação.
   */
   private void initComps(){
      pLogin = new PainelLogin(minAltura, minLargura);
      pLogin.setVisible(true);
   }
}
