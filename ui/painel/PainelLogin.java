package ui.painel;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

import cont.ContPainelLogin;
import ui.Janela;
import ui.comps.BtBase;
import ui.comps.LbBase;
import ui.comps.TxtBase;
import ui.comps.TxtSenhaBase;

/**
 * Modelo de painel para o login do usuário na aplicação.
 */
public class PainelLogin extends PainelBase{
   
   /**
    * Janela para poder redirecionar os paineis
    */
   Janela janela;

   /**
    * Controlador responvável pela regra de negócio do painel.
    */
   private ContPainelLogin controlador = new ContPainelLogin(this);

   /**
    * Botão para fazer login
    */
   public BtBase botao;

   /**
    * Caixa de texto para pegar entrada do nome do usuário.
    */
   public TxtBase txtUsuario;

   /**
    * Caixa de texto para pegar a entrada da senha do usuário.
    */
   public TxtSenhaBase txtSenha;

   /**
    * Label identificador para o campo do nome do usuário.
    */
   public LbBase lbUsuario;

   /**
    * Label identificador para o campo da senha do usuário.
    */
   public LbBase lbSenha;
   
   /**
    * Cor padrão para as caixa de texto.
    */
   Color corCaixaTexto = new Color(245, 245, 245);

   /**
    * Caminho relativo onde deve estar a imagem usada para plano de fundo
    * do painel.
    */
   final String CAMINHO_IMG_FUNDO = "/img/img-login.jpg";
   
   /**
    * Imagem usada como plano de fundo do painel.
    */
   BufferedImage imgFundo;

   /**
    * Inicializa um novo painel de login.
    * @param janela janela em que o painel está adicionado.
    * @param altura altura do painel.
    * @param largura largura do painel.
    */
   public PainelLogin(Janela janela, int altura, int largura){
      super(altura, largura);
      this.janela = janela;

      configPadrao();
      setLayout(null);

      initComps();
      configComps();

      add(lbUsuario);
      add(txtUsuario);
      add(lbSenha);
      add(txtSenha);
      add(botao);
   }

   /**
    * Configura os componentes para adaptação dinâmica com o 
    * as dimensões da janela.
    */
   private void configComps(){
      addComponentListener(new ComponentAdapter(){
         public void componentResized(ComponentEvent e){
            int centroX = getWidth()/2;
            int centroY = getHeight()/2;
            int padAlt = 20;

            txtUsuario.setBounds(
               (centroX) - (txtUsuario.largura()/ 2),
               (centroY) - (txtUsuario.altura() / 2) - (txtSenha.altura() + padAlt),
               txtUsuario.largura(), txtUsuario.altura()         
            );

            lbUsuario.setBounds(
               txtUsuario.getX(),
               txtUsuario.getY() - lbUsuario.altura(),
               lbUsuario.largura(), lbUsuario.altura()
            );

            txtSenha.setBounds(
               (centroX) - (txtSenha.largura()/ 2),
               (centroY) - (txtSenha.altura() / 2),
               txtSenha.largura(), txtSenha.altura()         
            );

            lbSenha.setBounds(
               txtSenha.getX(),
               txtSenha.getY() - lbSenha.altura(),
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
      botao = new BtBase(30, 80, "Entrar");
      botao.addActionListener(e -> {
         String usuario = txtUsuario.getText().trim();
         String senha = new String(txtSenha.getPassword()).trim();

         if(usuario.isEmpty() || senha.isEmpty()){
            JOptionPane.showMessageDialog(
               this, 
               "Por favor, preencha todos os campos.", 
               "Campos incompletos", 
               JOptionPane.PLAIN_MESSAGE
            );
            
         }else{
            controlador.verificarLogin();
         }
      });

      txtUsuario = new TxtBase(20, 200, "");
      lbUsuario = new LbBase(20, 60, "Usuário");
      
      txtSenha  = new TxtSenhaBase(20, 200, "");
      lbSenha = new LbBase(20, 60, "Senha");

      try{
         imgFundo = ImageIO.read(getClass().getResourceAsStream(CAMINHO_IMG_FUNDO));
      }catch(Exception e){
         //sem imagem caso não encontre
      }
   }

   @Override
   protected void paintComponent(Graphics g){
      super.paintComponent(g);

      if(imgFundo != null){
         Graphics2D g2d = (Graphics2D) g.create();
         g2d.drawImage(imgFundo, 0, 0, getWidth(), getHeight(), null);
         g2d.dispose();
      }
   }

   @Override
   public void redirecionar(PainelBase base, String dest){
      janela.redirecionar(base, dest);
   }

}
