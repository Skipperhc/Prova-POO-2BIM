package utils.constants;

import java.util.ArrayList;

public class ListaCargos {
	
	public ArrayList<Cargo> getLista() {
		return new ArrayList<Cargo>() {
			{
				add(new Cargo("Vendedor", 2));
				add(new Cargo("Estoquista", 3));
				add(new Cargo("Administrador", 4));
				add(new Cargo("Nível Martin", 5));
			}
		};
	}

	public class Cargo {
		private String nome;
		private int acesso;
		public Cargo(String nome, int acesso) {
			super();
			this.nome = nome;
			this.acesso = acesso;
		}
		public String getNome() {
			return nome;
		}
		public void setNome(String nome) {
			this.nome = nome;
		}
		public int getAcesso() {
			return acesso;
		}
		public void setAcesso(int acesso) {
			this.acesso = acesso;
		}
		@Override
		public String toString() {
			return getNome();
		}
		
	}
	
}
