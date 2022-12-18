package ru.azee.parser.jpa.tools;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public enum PersistenceManager {

	INSTANCE;
	
	private EntityManagerFactory emfactory;
	
	private PersistenceManager() {
		emfactory = Persistence.createEntityManagerFactory("eclipselink");
	}
	
	public EntityManager getEntityManager() {
		return emfactory.createEntityManager();
	}

	public void close() {
		if (emfactory!=null) {
			emfactory.close();
		}
	}
}

