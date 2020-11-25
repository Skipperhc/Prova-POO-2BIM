package daos.interfaces;

import java.util.List;

public interface IPadraoDAO<T, S> {

	/*
	 * inserir
	 * editar
	 * deletar
	 * buscar
	 * listar
	 */
	
	public int inserir(T obj);
	
	public int editar(T obj);

	public int deletar(S cod);
	
	public List<T> listar();
	
	public T buscar(S cod);
	
}
