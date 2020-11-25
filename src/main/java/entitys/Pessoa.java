package entitys;

public abstract class Pessoa {

	private int cod;
	private String nome;
	private String documento;
	private String telefone;
	private String senha;

	public Pessoa() {
		super();
	}
	public Pessoa(String noome, String documento, String telefone, String senha) {
		super();
		this.nome = noome;
		this.documento = documento;
		this.telefone = telefone;
		this.senha = senha;
	}
	public Pessoa(int cod, String noome, String documento, String telefone, String senha) {
		super();
		this.cod = cod;
		this.nome = noome;
		this.documento = documento;
		this.telefone = telefone;
		this.senha = senha;
	}
	
	@Override
	public String toString() {
		return "Pessoa [cod=" + cod + ", noome=" + nome + ", documento=" + documento + ", telefone=" + telefone
				+ ", senha=" + senha + "]";
	}
	public int getCod() {
		return cod;
	}
	public void setCod(int cod) {
		this.cod = cod;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String noome) {
		this.nome = noome;
	}
	public String getDocumento() {
		return documento;
	}
	public void setDocumento(String documento) {
		this.documento = documento;
	}
	public String getTelefone() {
		return telefone;
	}
	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
}
