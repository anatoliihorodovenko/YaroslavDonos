package com.nixolution.usermanage.db;

import static org.junit.Assert.*;

import org.junit.Test;

public class DaoFactoryTest {

	
	public void testGetUserDao() {
		try {
			DaoFactory daoFactory = DaoFactory.getInstance();
			assertNotNull("DaoFactory instance is null", daoFactory);
			UserDao userDao = daoFactory.getUserDao();
			assertNotNull("UserDao instance is null", userDao);
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.toString());
		}
	}

}
