package cont;

import java.sql.ResultSet;
import java.sql.SQLException;

import db.Database;
import ui.painel.PainelLogin;

/**
 * Controlador para as funcionalidades do painel de login.
 */
public class ContPainelLogin{
   PainelLogin painel;

   /**
    * Instancia um controlador responável pela lógica do painel de login.
    * @param pl painel de login.
    */
   public ContPainelLogin(PainelLogin pl){
      this.painel = pl;
   }

   /**
    * 
    */
   public void verificarLogin(){
      Database db = Database.getInstance();
      String sql = "SELECT * FROM usuarios WHERE nome = ? AND senha = ?";

      try{
         String nome = painel.txtUsuario.getText();
         String senha = new String(painel.txtSenha.getPassword()).trim();

         ResultSet res = db.query(sql, nome, senha);
         if(res.next()){
            if(res.getBoolean("admin")){
               System.out.println("Usuário encontrado (admin).");
               painel.txtSenha.setText("");
               painel.txtUsuario.setText("");
               painel.redirecionar(painel, "menu");
            
            }else{
               System.out.println("Usuário encontrado.");
            }

         }else{
            System.out.println("Usuário não encontrado.");
         }
      }catch(SQLException e){
         e.printStackTrace();
      }
   }
}
