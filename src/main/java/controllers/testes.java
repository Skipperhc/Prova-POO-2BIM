package controllers;

import java.sql.SQLException;

import daos.ClienteDAO;
import entitys.Cliente;
import utils.ConexaoMySql;

public class testes {

	public static void main(String[] args) {
		try {
			ClienteDAO dao = new ClienteDAO(ConexaoMySql.getInstance().getConnection());
			
			dao.inserir(new Cliente(1, "Cleber", "123", "321", "1"));
			dao.inserir(new Cliente(1, "Jorjin", "123", "321", "1"));
			
			dao.listar().forEach(item -> {System.out.println(item);});
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}
}
