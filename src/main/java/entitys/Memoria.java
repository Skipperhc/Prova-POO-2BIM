package entitys;

public class Memoria extends Produto {

	private int ram;
	private int frequencia;

	public Memoria() {
		super();
	}
	
	public Memoria(int ram, int frequencia, Double peso, String nome, String descricao, Double preco) {
		super(peso, nome, descricao, preco);
		this.ram = ram;
		this.frequencia = frequencia;
	}
	
	public Memoria(int ram, int frequencia, int cod, Double peso, String nome, String descricao, Double preco) {
		super(cod, peso, nome, descricao, preco);
		this.ram = ram;
		this.frequencia = frequencia;
	}
	@Override
	public String toString() {
		return "Memoria [ram=" + ram + ", frequencia=" + frequencia + "]";
	}
	public int getRam() {
		return ram;
	}
	public void setRam(int ram) {
		this.ram = ram;
	}
	public int getFrequencia() {
		return frequencia;
	}
	public void setFrequencia(int frequencia) {
		this.frequencia = frequencia;
	}
}
