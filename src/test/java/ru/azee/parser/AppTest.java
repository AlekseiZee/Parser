package ru.azee.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.eclipse.persistence.jpa.jpql.Assert.AssertException;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

@TestMethodOrder(OrderAnnotation.class)
class AppTest {

//    @Test
//    @Order(3)
//    void addition1() {
//    	InputData imputdata = new InputData();
//    	assertTrue(imputdata.isAngle("09F1                             101                87.87944        14.06500     "));
//    }
//    
//    @Test
//    @Order(1)
//    void addition2() {
//    	InputData imputdata = new InputData();
//    	assertFalse(imputdata.isAngle("09F1                              A 41.720          86.47194        14.10472"));
//    }
//    @Test
//    @Order(2)
//    void addition3() {
//    	InputData imputdata = new InputData();
//    	assertFalse(imputdata.isAngle("09F1                              A 41.720          86.47194        14.10472"));
//    }
//    @Test
//    @Order(4)
//    void addition4() {
//    	InputData imputdata = new InputData();
//    	assertFalse(imputdata.isAngle("09F1                              A 41.720          86.47194        14.10472"));
//    }
    
	@Test
	void checkFirstLineException() {
		InputData inpData = new InputData();
		//Exception exception = 
		assertThrows(Exception.class, () ->
		inpData.readInputData("D:\\JOB32.txt"));
    //assertEquals("Первая строка должна быть Point", exception.getMessage());
	}
	
    @Test
	void chek_first_line_true() {
		InputData inpData = new InputData();
		try {
			inpData.readInputData("D:\\JOB31.txt");
		} catch (Exception e) {
			e.printStackTrace();
		}
		assertEquals(1, inpData.getPoints().size());
	}
}
