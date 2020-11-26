package entitys;

public class Cargo {
	
	private String nome;
	private int acesso;
	
	public Cargo(String nome, int acesso) {
		super();
		this.nome = nome;
		this.acesso = acesso;
	}
	
	
	@Override
	public String toString() {
		return nome;
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
}
