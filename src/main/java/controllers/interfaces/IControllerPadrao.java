package controllers.interfaces;

import daos.interfaces.IPadraoDAO;

public abstract class IControllerPadrao<T, S> {

	public IPadraoDAO<T, S> dao;
	
}
