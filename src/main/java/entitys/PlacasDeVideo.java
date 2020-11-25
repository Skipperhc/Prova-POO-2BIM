package entitys;

public class PlacasDeVideo extends Produto{

	private int vram;
	private String ddr;

	public PlacasDeVideo() {
		super();
	}

	public PlacasDeVideo(int vram, String ddr, Double peso, String nome, String descricao, Double preco) {
		super(peso, nome, descricao, preco);
		this.vram = vram;
		this.ddr = ddr;
	}
	
	public PlacasDeVideo(int vram, String ddr, int cod, Double peso, String nome, String descricao, Double preco) {
		super(cod, peso, nome, descricao, preco);
		this.vram = vram;
		this.ddr = ddr;
	}
	
	@Override
	public String toString() {
		return "PlacasDeVideo [vram=" + vram + ", ddr=" + ddr + "]";
	}
	public int getVram() {
		return vram;
	}
	public void setVram(int vram) {
		this.vram = vram;
	}
	public String getDdr() {
		return ddr;
	}
	public void setDdr(String ddr) {
		this.ddr = ddr;
	}
}
