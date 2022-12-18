package ru.azee.parser;

import ru.azee.parser.jpa.dto.AngleJpaRepository;
import ru.azee.parser.jpa.entity.Angle;
import ru.azee.parser.jpa.tools.PersistenceManager;

public class Parser 
{
	static final String path = "D:\\Geo";
	
public static void main(String[] args) {
		
    	Angle angle = AngleJpaRepository.create();
//    	eclipselink.logging		properties in persistence.xml
//    	System.out.println("main() > 1 " + angle);
    	AngleJpaRepository.deleteByEntity(angle);
    	
    	angle = AngleJpaRepository.create();
//    	System.out.println("main() > 2 " + angle);
    	AngleJpaRepository.deleteByIdViaPersistenceContext(angle.getId());
    	
    	angle = AngleJpaRepository.create();
//    	System.out.println("main() > 3 " + angle);
    	AngleJpaRepository.deleteByIdWithJPQL(angle.getId());
    	
    	angle = AngleJpaRepository.create();
//    	System.out.println("main() > 4 " + angle);
    	AngleJpaRepository.deleteByIdViaNamedQuery(angle.getId());
    	
//    	Batch writing :     https://www.eclipse.org/forums/index.php/t/832424/
//    	https://www.eclipse.org/eclipselink/documentation/4.0/jpa/extensions/persistenceproperties_ref.htm#CIHIAGAF    	
    	AngleJpaRepository.createBatch(10);

    	PersistenceManager.INSTANCE.close();
	}
}
