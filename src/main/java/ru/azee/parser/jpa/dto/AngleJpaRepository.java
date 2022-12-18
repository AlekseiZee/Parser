package ru.azee.parser.jpa.dto;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;
import ru.azee.parser.jpa.entity.Angle;
import ru.azee.parser.jpa.entity.Anglepair;
import ru.azee.parser.jpa.tools.PersistenceManager;
/**
 * Клас для запросов SQL (в котором каждый метод равен одной команде SQL)
 * @author Home
 *
 */
public class AngleJpaRepository {
// Считываем entity по ID. В EntityManager заложен код поиска ID
	public static Angle read(Long id) {
		EntityManager em = null;
		try {
			em = PersistenceManager.INSTANCE.getEntityManager();
			return em.find(Angle.class, id);
		} catch (Exception e) {
			e.printStackTrace(System.out);
			return null;
		} finally {
			if (em != null) {
				em.close();
			}
		}
		
//		EntityTransaction transaction = em.getTransaction();
//		transaction.begin();
//		
//		transaction.commit(); // em.flush()
//		em.close();
	}
	/**
	 *  Записывает entity. Делается через транзакцию.
	 * @return
	 */
	public static Angle create() {
		EntityManager em = null;
		EntityTransaction transaction = null;
		try {
			em = PersistenceManager.INSTANCE.getEntityManager();
			
			Anglepair anglepair = em.find(Anglepair.class, 12L);
			Angle angle = new Angle();
			angle.setAnglepair(anglepair);
			angle.setHangle(1.001);
			angle.setVangle(2.002);
			
			transaction = em.getTransaction();
			transaction.begin();
			em.persist(angle);
			em.flush(); // отправляем в базу все что сделали
			transaction.commit(); // завершили транзакцию
			return angle;
		} catch (Exception e) {
			try{
				if (transaction!=null) {
					transaction.rollback();
				}
			} catch (Exception e1) {
				e1.printStackTrace(System.out);
			}
			e.printStackTrace(System.out);
			return null;
		} finally {
			if (em != null) {
				em.close();
			}
		}		
	}
/**
 * Создаем сразу партию значений Angle. Список значений типа <Angle> 
 * @param quantity
 */
	public static void createBatch(int quantity) {
		EntityManager em = null;
		EntityTransaction transaction = null;
		try {
			em = PersistenceManager.INSTANCE.getEntityManager();
			Anglepair anglepair = em.find(Anglepair.class, 12L);
			transaction = em.getTransaction();
			transaction.begin();
			for (int i=0; i<quantity; i++) {
				Angle angle = new Angle();
				angle.setAnglepair(anglepair);
				angle.setHangle(1.001);
				angle.setVangle(2.002);
				em.persist(angle);
			}
			em.flush();
			transaction.commit();
			
		} catch (Exception e) {
			try{
				if (transaction!=null) {
					transaction.rollback();
				}
			} catch (Exception e1) {
				e1.printStackTrace(System.out);
			}
			e.printStackTrace(System.out);
		} finally {
			if (em != null) {
				em.close();
			}
		}		
	}
	/**
	 * Удаляет сущность из базы 
	 * @param angle
	 */
	public static void deleteByEntity(Angle angle) {
		EntityManager em = null;
		EntityTransaction transaction = null;
		try {
			em = PersistenceManager.INSTANCE.getEntityManager();
//			System.out.println("deleteByEntity() > angle entity is managed before merging - " + em.contains(angle));
			angle = em.merge(angle); //добавляем отделенную сущность в контекст 
//			System.out.println("deleteByEntity() > angle entity is managed after merging - " + em.contains(angle));
			transaction = em.getTransaction();
			transaction.begin();
			em.remove(angle); // помечаем сущность, как удаленную из базы данных (уще не удалена)
			em.flush(); // удаляется из базы
			transaction.commit();

		} catch (Exception e) {
			try{
				if (transaction!=null) {
					transaction.rollback();
				}
			} catch (Exception e1) {
				e1.printStackTrace(System.out);
			}
			e.printStackTrace(System.out);
		} finally {
			if (em != null) {
				em.close();
			}
		}
	}
	/**
	 * Удалить сущность с помощью запроса SQL(расширенного JPA. JPQL)
	 * @param id
	 */
	public static void deleteByIdWithJPQL(Long id) {
		EntityManager em = null;
		EntityTransaction transaction = null;
		try {
			em = PersistenceManager.INSTANCE.getEntityManager();
			transaction = em.getTransaction();
			transaction.begin();
			Query q = em.createQuery("DELETE FROM Angle a WHERE a.id = :id");
				q.setParameter("id", id);
				q.executeUpdate();
			transaction.commit();

		} catch (Exception e) {
			try{
				if (transaction!=null) {
					transaction.rollback();
				}
			} catch (Exception e1) {
				e1.printStackTrace(System.out);
			}
			e.printStackTrace(System.out);
		} finally {
			if (em != null) {
				em.close();
			}
		}
	}
	/**
	 * Метод удаления с помощью операции из EntityManager
	 * @param id
	 */
	public static void deleteByIdViaPersistenceContext(Long id) {
		EntityManager em = null;
		EntityTransaction transaction = null;
		try {
			em = PersistenceManager.INSTANCE.getEntityManager();
			Angle angle = em.find(Angle.class, id);
			transaction = em.getTransaction();
			transaction.begin();
			em.remove(angle);
			em.flush();
			transaction.commit();

		} catch (Exception e) {
			try{
				if (transaction!=null) {
					transaction.rollback();
				}
			} catch (Exception e1) {
				e1.printStackTrace(System.out);
			}
			e.printStackTrace(System.out);
		} finally {
			if (em != null) {
				em.close();
			}
		}
	}
	
	public static void deleteByIdViaNamedQuery(Long id) {
		EntityManager em = null;
		EntityTransaction transaction = null;
		try {
			em = PersistenceManager.INSTANCE.getEntityManager();
			transaction = em.getTransaction();
			transaction.begin();
			em.createNamedQuery("Angle.deleteById").setParameter("id", id).executeUpdate();
			em.flush();
			transaction.commit();

		} catch (Exception e) {
			try{
				if (transaction!=null) {
					transaction.rollback();
				}
			} catch (Exception e1) {
				e1.printStackTrace(System.out);
			}
			e.printStackTrace(System.out);
		} finally {
			if (em != null) {
				em.close();
			}
		}
	}
	
}
