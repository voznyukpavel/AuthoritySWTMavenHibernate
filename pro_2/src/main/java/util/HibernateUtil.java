package util;

import org.hibernate.HibernateException;
import org.hibernate.cfg.Configuration;
import org.hibernate.SessionFactory;

public class HibernateUtil {
	private static final SessionFactory sessionFactory;

	static {
		try {
			// Create the SessionFactory from standard (hibernate.cfg.xml)
			// config file.
			sessionFactory = new Configuration().configure().buildSessionFactory();
		} catch (HibernateException ex) {
			System.err.println("Initial SessionFactory creation failed." + ex);
			throw new ExceptionInInitializerError(ex);
		}
	}

	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}
}
