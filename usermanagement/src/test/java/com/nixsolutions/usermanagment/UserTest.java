package com.nixsolutions.usermanagment;

import java.util.Calendar;
import java.util.Date;

import com.nixsolutions.usermanagment.User;

import junit.framework.TestCase;

public class UserTest extends TestCase {

    private User user;
	private Date dateOfBirthday;

    
    protected void setUp() throws Exception {
        super.setUp();
        user = new User();
        
       Calendar calendar = Calendar.getInstance();
       calendar.set(1996, Calendar.OCTOBER, 29);
       dateOfBirthday = calendar.getTime();
        
    }

    
    public void testGetFullName() {
        user.setFirstName("Ярослав");
        user.setLastName("Донос");
        assertEquals("Донос, Ярослав", user.getFullName());
    }
    
    
    public void testGetAge() {
       
        user.setDateOfBirthday(dateOfBirthday);
        assertEquals(2016 - 1997, user.getAge());
        
    }

}
