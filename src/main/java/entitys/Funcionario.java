package entitys;

public class Funcionario extends Pessoa {
	private Cargo cargo;

	public Funcionario() {
		super();
	}

	public Funcionario(String cargo, int acesso, String nome, String documento, String telefone, String senha) {
		super(nome,documento, telefone, senha);
		this.cargo = new Cargo(cargo, acesso);
	}

	public Funcionario(String cargo, int acesso, int cod, String nome, String documento, String telefone, String senha) {
		super(cod, nome,documento, telefone, senha);
		this.cargo = new Cargo(cargo, acesso);
	}

	@Override
	public String toString() {
		return "Funcionario [cargo=" + cargo.getNome() + ", acesso=" + cargo.getAcesso() + "]";
	}

	public Cargo getCargo() {
		return cargo;
	}

	public void setCargo(Cargo cargo) {
		this.cargo = cargo;
	}
}
