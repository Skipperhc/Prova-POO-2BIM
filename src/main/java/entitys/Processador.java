package entitys;

public class Processador extends Produto{

	private int nucleos;
	private int threads;

	public Processador() {
		super();
	}

	public Processador(int nucleos, int threads, Double peso, String nome, String descricao, Double preco) {
		super(peso, nome, descricao, preco);
		this.nucleos = nucleos;
		this.threads = threads;
	}
	
	public Processador(int nucleos, int threads, int cod, Double peso, String nome, String descricao, Double preco) {
		super(cod, peso, nome, descricao, preco);
		this.nucleos = nucleos;
		this.threads = threads;
	}
	
	@Override
	public String toString() {
		return "Processador [nucleos=" + nucleos + ", threads=" + threads + "]";
	}
	public int getNucleos() {
		return nucleos;
	}
	public void setNucleos(int nucleos) {
		this.nucleos = nucleos;
	}
	public int getThreads() {
		return threads;
	}
	public void setThreads(int threads) {
		this.threads = threads;
	}
}
