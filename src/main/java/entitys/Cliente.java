package entitys;

public class Cliente extends Pessoa {
	
	private int acesso;
	
	public Cliente() {
		super();
		this.acesso = 1;
	}

	public Cliente(String nome, String documento, String telefone, String senha) {
		super(nome,documento, telefone, senha);
		this.acesso = 1;
	}

	public Cliente(int cod, String nome, String documento, String telefone, String senha) {
		super(cod, nome,documento, telefone, senha);
		this.acesso = 1;
	}

	@Override
	public String toString() {
		return String.format("Cliente: nome= %s, documento= %s, telefone= %s, acesso= %s, senha= %s", getNome(), getDocumento(), getTelefone(), acesso, getSenha());
	}

	public int getAcesso() {
		return acesso;
	}

	public void setAcesso(int acesso) {
		this.acesso = acesso;
	}
}
