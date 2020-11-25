package entitys;

public class Funcionario extends Pessoa {
	private String cargo;
	private int acesso;

	public Funcionario() {
		super();
	}

	public Funcionario(String cargo, int acesso, String nome, String documento, String telefone, String senha) {
		super(nome,documento, telefone, senha);
		this.cargo = cargo;
		this.acesso = acesso;
	}

	public Funcionario(String cargo, int acesso, int cod, String nome, String documento, String telefone, String senha) {
		super(cod, nome,documento, telefone, senha);
		this.cargo = cargo;
		this.acesso = acesso;
	}

	@Override
	public String toString() {
		return "Funcionario [cargo=" + cargo + ", acesso=" + acesso + "]";
	}
	public String getCargo() {
		return cargo;
	}
	public void setCargo(String cargo) {
		this.cargo = cargo;
	}
	public int getAcesso() {
		return acesso;
	}
	public void setAcesso(int acesso) {
		this.acesso = acesso;
	}
}
