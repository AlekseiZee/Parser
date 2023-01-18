package ru.azee.parser;

import java.sql.Timestamp;
import java.util.Date;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import ru.azee.parser.jpa.entity.Angle;
import ru.azee.parser.jpa.entity.Anglepair;
import ru.azee.parser.jpa.entity.Instance;
import ru.azee.parser.jpa.tools.PersistenceManager;

public class Parser 
{
	static final String PATH = "D:\\job3.txt";
	
public static void main(String[] args) throws Exception{
	
	InputData inputData = new InputData();
	inputData.readInputData(PATH);
	Instance instance = new Instance();
	inputData.getPoints().forEach(p -> instance.addPoint(p));
	
	instance.setCreationDate(new Timestamp(new Date().getTime()));
	
	EntityManager em = null;
	EntityTransaction transaction = null;
	try {
		em = PersistenceManager.INSTANCE.getEntityManager();
		
		transaction = em.getTransaction();
		transaction.begin();
		em.persist(instance);
		em.flush(); // отправляем в базу все что сделали
		transaction.commit(); // завершили транзакцию
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
		
//    	Angle angle = AngleJpaRepository.create();
////    	eclipselink.logging		properties in persistence.xml
////    	System.out.println("main() > 1 " + angle);
//    	AngleJpaRepository.deleteByEntity(angle);
//    	
//    	angle = AngleJpaRepository.create();
////    	System.out.println("main() > 2 " + angle);
//    	AngleJpaRepository.deleteByIdViaPersistenceContext(angle.getId());
//    	
//    	angle = AngleJpaRepository.create();
////    	System.out.println("main() > 3 " + angle);
//    	AngleJpaRepository.deleteByIdWithJPQL(angle.getId());
//    	
//    	angle = AngleJpaRepository.create();
////    	System.out.println("main() > 4 " + angle);
//    	AngleJpaRepository.deleteByIdViaNamedQuery(angle.getId());
//    	
////    	Batch writing :     https://www.eclipse.org/forums/index.php/t/832424/
////    	https://www.eclipse.org/eclipselink/documentation/4.0/jpa/extensions/persistenceproperties_ref.htm#CIHIAGAF    	
//    	AngleJpaRepository.createBatch(10);
//
//    	PersistenceManager.INSTANCE.close();
	}
}
