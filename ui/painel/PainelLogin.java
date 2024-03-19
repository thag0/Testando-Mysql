package ui.painel;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import ui.comps.BotaoBase;
import ui.comps.LbBase;
import ui.comps.TxtBase;

public class PainelLogin extends PainelBase{
   BotaoBase botao;
   TxtBase txtUsuario;
   TxtBase txtSenha;
   LbBase lbUsuario;
   LbBase lbSenha;
   
   Color corCaixaTexto = new Color(255, 255, 255);

   final String CAMINHO_IMG_FUNDO = "/img/img-login.jpg";
   BufferedImage imgFundo;

   public PainelLogin(int altura, int largura){
      super(altura, largura);
      configPadrao();
      setLayout(null);

      initComps();

      add(txtUsuario);
      add(txtSenha);
      add(lbUsuario);
      add(lbSenha);
      add(botao);

      // redimensionar dinamicamente os componentes
      addComponentListener(new ComponentAdapter(){
         public void componentResized(ComponentEvent e){
            int centroX = getWidth()/2;
            int centroY = getHeight()/2;
            int padAlt = 20;
            int padLarg = 20;

            txtUsuario.setBounds(
               (centroX) - (txtUsuario.largura()/ 2),
               (centroY) - (txtUsuario.altura() / 2) - (botao.altura() + txtSenha.altura() + padAlt),
               txtUsuario.largura(), txtUsuario.altura()         
            );

            lbUsuario.setBounds(
               txtUsuario.getX() - lbUsuario.largura() - padLarg,
               txtUsuario.getY() - lbUsuario.altura()/3,
               lbUsuario.largura(), lbUsuario.altura()
            );

            txtSenha.setBounds(
               (centroX) - (txtSenha.largura()/ 2),
               (centroY) - (txtSenha.altura() / 2) - (botao.altura()),
               txtSenha.largura(), txtSenha.altura()         
            );

            lbSenha.setBounds(
               txtSenha.getX() - lbSenha.largura() - padLarg,
               txtSenha.getY() - (lbSenha.altura()/3),
               lbSenha.largura(), lbSenha.altura()
            );

            botao.setBounds(
               centroX - (botao.largura()/ 2),
               centroY + botao.altura(),
               botao.largura(), botao.altura()
            );
         }
      });
   }

   /**
    * Inicialização dos componentes do painel de login.
   */
   private void initComps(){
      botao = new BotaoBase(50, 100, "Entrar");
      botao.addActionListener(e -> {
         System.out.println(txtUsuario.getText());
      });

      txtUsuario = new TxtBase(20, 200, "");
      lbUsuario = new LbBase(40, 60, "Usuário");
      
      txtSenha  = new TxtBase(20, 200, "");
      lbSenha = new LbBase(40, 60, "Senha");

      try{
         imgFundo = ImageIO.read(getClass().getResourceAsStream(CAMINHO_IMG_FUNDO));
      }catch(Exception e){}
   }

   @Override
   protected void paintComponent(Graphics g){
      super.paintComponent(g);
      //não usar graphics2d para desenhar por inconsistências
      
      if(imgFundo != null){
         g.drawImage(imgFundo, 0, 0, getWidth(), getHeight(), this);
      }
   }

}
