package entitys;

public abstract class Produto {

	private int cod;
	private Double peso;
	private String nome;
	private String descricao;
	private Double preco;

	public Produto() {
		super();
	}

	public Produto(Double peso, String nome, String descricao, Double preco) {
		super();
		this.peso = peso;
		this.nome = nome;
		this.descricao = descricao;
		this.preco = preco;
	}
	
	public Produto(int cod, Double peso, String nome, String descricao, Double preco) {
		super();
		this.cod = cod;
		this.peso = peso;
		this.nome = nome;
		this.descricao = descricao;
		this.preco = preco;
	}
	
	public abstract Double calcularImposto();
	
	@Override
	public String toString() {
		return "Produto [cod=" + cod + ", peso=" + peso + ", nome=" + nome + ", descricao=" + descricao + ", preco="
				+ preco + "]";
	}
	public int getCod() {
		return cod;
	}
	public void setCod(int cod) {
		this.cod = cod;
	}
	public Double getPeso() {
		return peso;
	}
	public void setPeso(Double peso) {
		this.peso = peso;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public Double getPreco() {
		return preco;
	}
	public void setPreco(Double preco) {
		this.preco = preco;
	}
}
