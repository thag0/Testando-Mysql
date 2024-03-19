package db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import modelo.Usuario;

/**
 * 
 */
public class DAOUsuario{
   Database db;

   /**
    * 
    */
   public DAOUsuario(){
      db = Database.getInstance();
   }

   /**
    * Adiciona um resgistro de usuário dentro do banco de dados.
    * @param usuario usuário usado para registrar na tabela.
    * @return {@code true} caso o usuário seja adicionado, {@code false}
    * caso contrário.
    */
   public boolean inserir(Usuario usuario){
      StringBuilder sb = new StringBuilder();
      
      sb.append("INSERT INTO usuarios (nome, cidade, senha, admin) VALUES (?, ?, ?, ?)");

      String[] params = {
         usuario.getNome(),
         usuario.getCidade(),
         usuario.getSenha(),
         "0"
      };

      return (db.update(sb.toString(), params) > 0) ? true : false;
   }

   /**
    * Deleta o resgistro de usuário dentro do banco de dados.
    * @param usuario usuário usado para buscar na tabela.
    * @return {@code true} caso o usuário seja removido, {@code false}
    * caso contrário.
    */
   public boolean deletar(Usuario usuario){
      StringBuilder sb = new StringBuilder();
      
      sb.append("DELETE FROM usuarios WHERE nome = ? AND senha = ?");

      String[] params = {
         usuario.getNome(),
         usuario.getSenha()
      };

      return (db.update(sb.toString(), params) > 0) ? true : false;
   }

   /**
    * Retorna o resultado de todos os usuários contidos no banco de dados.
    * @return todos os usuários
    */
   public ArrayList<Usuario> buscarTudo(){
      StringBuilder sb = new StringBuilder();
      
      sb.append("SELECT * FROM usuarios");
      
      ArrayList<Usuario> usuarios = new ArrayList<>();

      ResultSet res = db.query(sb.toString());

      try{
         while(res.next()){
            String nome = res.getString("nome");
            String cidade = res.getString("cidade");
            String senha = res.getString("senha");
            boolean admin = res.getBoolean("admin");
            
            Usuario usuario = new Usuario(nome, cidade, senha);
            usuario.setAdmin(admin);

            usuarios.add(usuario);
         }
      }catch(SQLException e){
         e.printStackTrace();
      }

      return usuarios;
   }
}
