/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.projetoaula.controller;

import br.com.projetoaula.model.Usuario;
import br.com.projetoaula.view.JFLogin;
import br.com.projetoaula.view.JFPrincipal;
import java.sql.*;
import javax.swing.*;
import net.proteanit.sql.DbUtils;
/**
 *
 * @author Eduardo
 */
public class UsuarioDAO {
    
    Connection conexao;
    PreparedStatement pst;
    ResultSet rs;
    
    //String sql = "select * from usuario where login_usuario = ? and senha_usuario=?";
    
    Conexao con = new Conexao();
    Usuario usu = new Usuario();
    
    /**
     *
     * @param login
     * @param passwd
     */
    public boolean Access(JTextField login, JTextField passwd){
        
        usu.setLogin_usuario(login.getText());
        usu.setSenha_usuario(passwd.getText());
        JFPrincipal prin = new JFPrincipal();
        String sql = "select * from usuario where login_usuario = ? and senha_usuario=?";
        try{
            conexao = con.conector();
            pst = conexao.prepareStatement(sql);
            pst.setString(1, usu.getLogin_usuario());
            pst.setString(2, usu.getSenha_usuario()); 
            
            rs = pst.executeQuery();
            
            if(rs.next()){
                JOptionPane.showMessageDialog(null, "Acesso Permitido");
                con.desconector(conexao);
                prin.setVisible(true);
                return true;
                
            }else{
                JOptionPane.showMessageDialog(null, "Acesso Negado");
                con.desconector(conexao);
                return false;
            }
            
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "Ocorreu um erro: "+e);
            return false;
        }
        
    }
    
    public void InserirUsuario(JTextField nome, JTextField login, JPasswordField senha, JComboBox tipo){
        
        String sql = "insert into usuario (nome_usuario,login_usuario,senha_usuario,tipo_usuario) "
                + "values (?,?,?,?)";
        
        int i = tipo.getSelectedIndex();
        
        
        try{
            conexao = con.conector();
            pst = conexao.prepareStatement(sql);
            pst.setString(1, nome.getText());
            pst.setString(2, login.getText());
            pst.setString(3, senha.getText());
            if(tipo.getSelectedIndex()==0){
                pst.setString(4, "adm");
            }else{
                pst.setString(4, "usu");
            }
            //pst.setString(4, tipo.getItemAt(i).toString());
            pst.execute();
            
            JOptionPane.showMessageDialog(null, "Inserido com sucesso");
            con.desconector(conexao);
            
            
            
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "Erro: "+e);
        }
        
        
    }
    
    public void ConsultaUsuario(JTextField consulta, JTable tabela){
        String sql = "select id_usuario as ID, nome_usuario as Nome, login_usuario as Login, "
                + "tipo_usuario as Tipo from usuario where id_usuario like ?";
        
        try{
            conexao = con.conector();
            pst = conexao.prepareStatement(sql);
            pst.setString(1, consulta.getText());
            rs = pst.executeQuery();
            
            tabela.setModel(DbUtils.resultSetToTableModel(rs));
            
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "Erro: "+e);
        }
    }
    
    
}
