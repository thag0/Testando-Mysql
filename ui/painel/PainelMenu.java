package ui.painel;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import contr.ContPainelMenu;
import ui.Janela;
import ui.comps.BtBase;
import ui.comps.LbBase;

public class PainelMenu extends PainelBase{
   Janela janela;
   ContPainelMenu controlador = new ContPainelMenu(this);

   LbBase lbTitulo;
   LbBase lbUsuario;
   BtBase botaoVoltar;
   LbBase lbCadastrar;

   /**
    * Caminho relativo onde deve estar a imagem usada para plano de fundo
    * do painel.
    */
   final String CAMINHO_IMG_FUNDO = "/img/img-menu.jpg";

   /**
    * Imagem usada como plano de fundo do painel.
    */
   BufferedImage imgFundo;

   /**
    * Inicializa um novo painel de menu.
    * @param janela janela em que o painel está adicionado.
    * @param altura altura do painel.
    * @param largura largura do painel.
    */
   public PainelMenu(Janela janela, int altura, int largura){
      super(altura, largura);
      configPadrao();
      setLayout(null);

      this.janela = janela;
      initComps();

      add(lbTitulo);
      add(botaoVoltar);
      add(lbCadastrar);
      add(lbUsuario);

      // redimensionar dinamicamente os componentes
      addComponentListener(new ComponentAdapter(){
         public void componentResized(ComponentEvent e){
            botaoVoltar.setBounds(
               -2,
               getHeight() - botaoVoltar.altura() + 2,
               botaoVoltar.largura(), botaoVoltar.altura()         
            );

            lbTitulo.setBounds(
               0, 0,
               lbTitulo.largura(), lbTitulo.altura()         
            );

            lbCadastrar.setBounds(
               0,
               lbTitulo.altura() + 10, 
               lbCadastrar.largura(), lbCadastrar.altura()
            );

            lbUsuario.setBounds(
               getWidth()-lbUsuario.largura(),
               0,
               lbUsuario.largura(), lbUsuario.altura()
            );
         }
      });
   }

   /**
    * Inicialização dos componentes do painel de menu.
    */
   private void initComps(){
      lbTitulo = new LbBase(40, 100, "Menu");
      lbTitulo.setFont(getFont().deriveFont((float)lbTitulo.largura() * 0.35f));
      lbTitulo.setForeground(Color.black);

      lbUsuario = new LbBase(40, 80, "");
      lbUsuario.setFont(getFont().deriveFont((float)lbUsuario.largura() * 0.18f));

      lbCadastrar = new LbBase(20, 100, "Cadastrar");
      lbCadastrar.setFont(getFont().deriveFont((float)lbCadastrar.largura() * 0.2f));

      botaoVoltar = new BtBase(30, 80, "Voltar");
      botaoVoltar.addActionListener(e -> {
         controlador.voltarLogin();
      });

      try{
         imgFundo = ImageIO.read(getClass().getResourceAsStream(CAMINHO_IMG_FUNDO));
      }catch(Exception e){
         //sem imagem caso não encontre
      }
   }

   @Override
   protected void paintComponent(Graphics g){
      super.paintComponent(g);
      Graphics2D g2 = (Graphics2D) g.create();

      if(imgFundo != null){
         g2.drawImage(imgFundo, 0, 0, getWidth(), getHeight(), null);
      }

      lbUsuario.setText("Olá, " + controlador.getSessao().getUsuarioLogado() + "!");

      g2.setColor(Color.white);
      int arco = 10;
      g2.fillRoundRect(-arco, 0, lbTitulo.largura()+arco, lbTitulo.altura(), arco, arco);

      g2.dispose();
   }

   @Override
   public void redirecionar(PainelBase base, String dest){
      janela.redirecionar(base, dest);
   }
}
