package utils.constants;

import java.util.ArrayList;

import entitys.Cargo;

public class ListaCargos {
	
	public ArrayList<Cargo> getLista() {
		return new ArrayList<Cargo>() {
			{
				add(new Cargo("Vendedor", 2));
				add(new Cargo("Estoquista", 3));
				add(new Cargo("Administrador", 4));
				add(new Cargo("Nível Martin", 5));
			}
		};
	}
}
