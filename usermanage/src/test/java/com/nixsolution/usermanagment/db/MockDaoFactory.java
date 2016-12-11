package com.nixsolution.usermanagment.db;

import com.mockobjects.dynamic.Mock;
import com.nixsolution.usermanagment.db.DaoFactory;
import com.nixsolution.usermanagment.db.UserDao;

public class MockDaoFactory extends DaoFactory {
private Mock mockUserDao;
public MockDaoFactory()
{
mockUserDao=new Mock(UserDao.class);	
}

public Mock getMockUserDao()
{
return mockUserDao;	
}
	public UserDao getUserDao() {
		// TODO Auto-generated method stub
		return (UserDao) mockUserDao.proxy();
	}

}
