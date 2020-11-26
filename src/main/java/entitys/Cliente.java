package entitys;

public class Cliente extends Pessoa {
	
	private int acesso = 1;
	
	public Cliente() {
		super();
	}

	public Cliente(String nome, String documento, String telefone, String senha) {
		super(nome,documento, telefone, senha);
	}

	public Cliente(int cod, String nome, String documento, String telefone, String senha) {
		super(cod, nome,documento, telefone, senha);
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
