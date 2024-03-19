package cont;

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
    * @param nome
    * @param senha
    */
   public void logar(String nome, String senha){
      Database db = Database.getInstance();
      String sql = "SELECT * FROM usuarios WHERE nome = ? AND senha = ? AND admin = ?";

      try{
         if(db.query(sql, nome, senha, "1").next()){
            System.out.println("Usuário encontrado.");
         }else{
            System.out.println("Usuário não encontrado.");
         }
      }catch(SQLException e){
         e.printStackTrace();
      }
   }
}
