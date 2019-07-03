package util;

import pDAO.DAOClass;
import pDAO.DAOInterface;

public class Factory {
	private static DAOInterface daointerface = null;
	private static Factory instance = null;

	public static synchronized Factory getInstance() {
		if (instance == null) {
			instance = new Factory();
		}
		return instance;
	}

	public DAOInterface getPersonDAO() {
		if (daointerface == null) {
			daointerface = new DAOClass();
		}
		return daointerface;
	}
}
