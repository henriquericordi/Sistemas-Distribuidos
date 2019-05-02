/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import classses.Cliente;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author rafael
 */
public class BD {

    private final Connection con;

    public BD() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/testdb?useSSL=false";
        String user = "testuser";
        String password = "test623";
        con = DriverManager.getConnection(url, user, password);
    }

    public Cliente buscarCliente(String CPF) {
        try {
            Statement st = con.createStatement();
            ResultSet resultados = st.executeQuery("SELECT * FROM cliente WHERE cpf like '" + CPF + "';");
            resultados.first();
            return new Cliente(CPF, resultados.getString("nome"));
        } catch (SQLException ex) {
            System.err.println("Erro de manipulação do Banco de Dados! " + ex.toString());
            return null;
        }
    }

    public boolean alterarCliente(Cliente cliente) {
        try {
            Statement st = con.createStatement();
            int resultado = st.executeUpdate("UPDATE cliente SET nome = '" + cliente.getNome() + "' WHERE cpf like '" + cliente.getCPF() + "';");
            return resultado == 1;
        } catch (SQLException ex) {
            System.err.println("Erro de manipulação do Banco de Dados! " + ex.toString());
            return false;
        }
    }

    public boolean alterarValorConta(int conta, double valor) {
        try {
            Statement st = con.createStatement();
            int resultado = st.executeUpdate("UPDATE conta SET saldo = saldo + " + valor + " WHERE numero = " + conta + ";");
            int tipo = 0;
            if (valor > 0.0) {
                tipo = 1;
            }
            return resultado == 1 && gerarMovimentacao(conta, tipo, valor, getDataHoraAtualMysql());
        } catch (SQLException ex) {
            System.err.println("Erro de manipulação do Banco de Dados! " + ex.toString());
            return false;
        }
    }

    private boolean gerarMovimentacao(int conta, int tipo, double valor, String data) {
        try {
            Statement st = con.createStatement();
            int resultado = st.executeUpdate("INSERT INTO movimentacao (numero, tipo, valor, data) VALUES (" + conta + ", " + tipo + ", " + valor + ", " + data + ")" + ";");
            return resultado == 1;
        } catch (SQLException ex) {
            System.err.println("Erro de manipulação do Banco de Dados! " + ex.toString());
            return false;
        }
    }

    public String getDataHoraAtualMysql() {
        java.util.Date dt = new java.util.Date();
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(dt);
    }

    private boolean gerarTransferencia(int contaOrigem, int contaDestino, double valor, String data) {
        try {
            Statement st = con.createStatement();
            int resultado = st.executeUpdate("INSERT INTO transferencia (contaorigem, contadestino, valor, data) VALUES (" + contaOrigem + ", " + contaDestino + ", " + valor + ", " + data + ")" + ";");
            return resultado == 1;
        } catch (SQLException ex) {
            System.err.println("Erro de manipulação do Banco de Dados! " + ex.toString());
            return false;
        }
    }

//    public boolean realizarTransferencia(int contaOrigem, int contaDestino, double valor) {
//        try {
//            Statement st = con.createStatement();
//            int resultado = st.executeUpdate("UPDATE conta SET saldo = saldo + " + valor + " WHERE numero = " + conta + ";");
//            int tipo = 0;
//            if (valor > 0.0) {
//                tipo = 1;
//            }
//            return resultado == 1 && gerarMovimentacao(conta, tipo, valor, getDataHoraAtualMysql());
//        } catch (SQLException ex) {
//            System.err.println("Erro de manipulação do Banco de Dados! " + ex.toString());
//            return false;
//        }
//    }
}
