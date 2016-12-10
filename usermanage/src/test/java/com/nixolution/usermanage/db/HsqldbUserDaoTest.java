package com.nixolution.usermanage.db; 

import java.util.Date; 

import org.dbunit.DatabaseTestCase;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.XmlDataSet;
import org.hsqldb.lib.Collection;

import com.nixolution.usermanage.User; 

public class HsqldbUserDaoTest extends DatabaseTestCase { 

private HsqldbUserDao dao; 
private ConnectionFactory connectionFactory; 



@Override 
protected void setUp() throws Exception { 

super.setUp(); 

dao=new HsqldbUserDao(connectionFactory); 
} 




public void testCreate() { 
try { 
User user= new User(); 
user.setFirstName("Yaroslav"); 
user.setLastName("Donos"); 
user.setDateOfBirthd(new Date()); 
assertNull(user.getId()); 
user=dao.create(user); 
assertNotNull(user); 
assertNotNull(user.getId()); 
} catch (DatabaseException e) { 

e.printStackTrace(); 
fail(e.toString()); 
} 
}




@Override
protected IDatabaseConnection getConnection() throws Exception {
	connectionFactory= new ConnectionFactoryImpl(); 
	return new DatabaseConnection(connectionFactory.createConnection());
	
}

public void testFindAll(){
	try {
		java.util.Collection collection = dao.findAll();
		assertNotNull("Collection is null", collection);
		assertEquals("Collection size", 2, collection.size());
	
	} catch (DatabaseException e) {

		e.printStackTrace();
		fail(e.toString());
	}
}


@Override
protected IDataSet getDataSet() throws Exception {

		IDataSet dataSet = new XmlDataSet(getClass().getClassLoader()
				.getResourceAsStream("usersDataSet.xml"));
	return dataSet;
} 

}
