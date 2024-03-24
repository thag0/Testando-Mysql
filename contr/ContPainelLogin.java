package contr;

import java.sql.ResultSet;
import java.sql.SQLException;

import db.Database;
import modelo.DadosSessao;
import ui.painel.PainelLogin;

/**
 * Controlador para as funcionalidades do painel de login.
 */
public class ContPainelLogin{
   PainelLogin painel;
   DadosSessao sessao = DadosSessao.getInstance();

   /**
    * Opções personalizadas para o controlador de login.
    */
   public enum LOGIN_OPS{
      LOGIN_ADM,
      LOGIN_USUARIO,
      LOGIN_NAO_ENCONTRADO
   };

   /**
    * Instancia um controlador responável pela lógica do painel de login.
    * @param pl painel de login.
    */
   public ContPainelLogin(PainelLogin pl){
      this.painel = pl;
   }

   /**
    * Retorna os dados da sessão atual.
    * @return dados da sessão atual.
    */
   public DadosSessao getSessao(){
      return sessao;
   }

   /**
    * ...
    */
   public LOGIN_OPS verificarLogin(){
      Database db = Database.getInstance();
      String sql = "SELECT * FROM usuarios WHERE nome = ? AND senha = ?";
      LOGIN_OPS ops = LOGIN_OPS.LOGIN_NAO_ENCONTRADO;

      try{
         String nome = painel.txtUsuario.getText();
         String senha = new String(painel.txtSenha.getPassword()).trim();

         ResultSet res = db.query(sql, nome, senha);
         if(res.next()){
            if(res.getBoolean("admin")){
               sessao.setUsuarioLogado(nome);
               ops = LOGIN_OPS.LOGIN_ADM;
            
            }else{
               ops = LOGIN_OPS.LOGIN_USUARIO;
            }

         }else{
            ops = LOGIN_OPS.LOGIN_NAO_ENCONTRADO;
         }
      }catch(SQLException e){
         ops = LOGIN_OPS.LOGIN_NAO_ENCONTRADO;
         e.printStackTrace();
      }

      return ops;
   }
}
