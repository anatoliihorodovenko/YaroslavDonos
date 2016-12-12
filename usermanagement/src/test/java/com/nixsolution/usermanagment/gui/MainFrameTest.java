package com.nixsolution.usermanagment.gui;

import java.awt.Component;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;

import com.mockobjects.dynamic.Mock;
import com.nixsolution.usermanagment.User;
import com.noxolution.usermanagment.db.DaoFactory;
import com.noxolution.usermanagment.db.DaoFactoryImpl;
import com.noxolution.usermanagment.gui.MainFrame;
import com.noxolution.usermanagment.util.Messages;
import com.vladbel.usermanagment.db.MockDaoFactory;
import com.vladbel.usermanagment.db.MockUserDao;

import junit.extensions.jfcunit.JFCTestCase;
import junit.extensions.jfcunit.JFCTestHelper;
import junit.extensions.jfcunit.eventdata.JTableMouseEventData;
import junit.extensions.jfcunit.eventdata.MouseEventData;
import junit.extensions.jfcunit.eventdata.StringEventData;
import junit.extensions.jfcunit.finder.NamedComponentFinder;


public class MainFrameTest extends JFCTestCase {

	
	private MainFrame mainFrame;

	private Mock mockUserDao;
	private ArrayList users;
	private User expectedUser;
	private User user;
	
	protected void setUp() throws Exception {
		super.setUp();
		
		Properties properties = new Properties();
		properties.setProperty("dao.factory",MockDaoFactory.class.getName());
		DaoFactory.getInstance().init(properties);
		
		mockUserDao = ((MockDaoFactory) DaoFactory.getInstance()).getMockUserDao();
		mockUserDao.expectAndReturn("findAll", new ArrayList());
		setHelper(new JFCTestHelper());
		mainFrame = new MainFrame();
		mainFrame.setVisible(true);
	}

	
	protected void tearDown() throws Exception {
		try {
			mockUserDao.verify();
			mainFrame.setVisible(false);
			getHelper().cleanUp(this);
			super.tearDown();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private Component find(Class componentClass, String name)
	{		NamedComponentFinder finder;
				finder = new NamedComponentFinder(componentClass, name);
				finder.setWait(0);
					Component component = finder.find(mainFrame,0);
					assertNotNull("Could not find component '" + name + "'", component);
	return component;
	}
	
	public void testBrowseControls() {
		
		find(JPanel.class, "browsePanel");
		JTable table = (JTable) find(JTable.class, "userTable");
		assertEquals(5, table.getColumnCount());
		assertEquals("ID", table.getColumnName(0));
		assertEquals("Имя", table.getColumnName(1));
		assertEquals("Фамилия", table.getColumnName(2));
		
		find(JButton.class, "addButton");
		find(JButton.class, "editButton");
		find(JButton.class, "deleteButton");
		find(JButton.class, "detailsButton");
	}
	
	public void testAddUser(){
		String firstName = "Vladka";
		String lastName = "Beliu";
		Date now = new Date();
		
		User user = new User(firstName, lastName, now);
	
		User expectedUser = new User (new Long(1), firstName, lastName, now);
		mockUserDao.expectAndReturn("create", user, expectedUser);
		ArrayList users = new ArrayList();
		users.add(expectedUser);
		mockUserDao.expectAndReturn("findAll", users);
		
		JTable table = (JTable) find(JTable.class, "userTable");
		assertEquals(0, table.getRowCount());
		
		JButton addButton = (JButton) find(JButton.class, "addButton");
		getHelper().enterClickAndLeave(new MouseEventData(this, addButton));
		
		find(JPanel.class, "addPanel");
		
		JTextField firstNameField = (JTextField) find(JTextField.class, "firstNameField");
		JTextField lastNameField = (JTextField) find(JTextField.class, "lastNameField");
		JTextField dateOfBirthField = (JTextField) find(JTextField.class, "dateOfBirthField");
		JButton okButton = (JButton) find(JButton.class, "okButton");
		 find(JButton.class, "cancelButton");
		
		getHelper().sendString(new StringEventData(this, firstNameField, firstName));
		getHelper().sendString(new StringEventData(this, lastNameField, lastName));
		DateFormat formatter = DateFormat.getDateInstance();
		String date = formatter.format(now);
		getHelper().sendString(new StringEventData(this, dateOfBirthField, date));
		
		getHelper().enterClickAndLeave(new MouseEventData(this, okButton));
		
		find(JPanel.class, "browsePanel");
		table = (JTable) find(JTable.class, "userTable");
		//assertEquals(1, table.getRowCount());
		
	}
	
	public void testDeleteUser() {
		try {
			
			mockUserDao.expectAndReturn("delete", expectedUser, expectedUser);
			mockUserDao.expectAndReturn("find", 20L, expectedUser);
			mockUserDao.expectAndReturn("find", 20L, expectedUser);
			mockUserDao.expectAndReturn("find", 20L, expectedUser);
			users.remove(expectedUser);
			mockUserDao.expectAndReturn("findAll", users);

			JTable table = (JTable) find(JTable.class, "userTable");
			assertEquals(1, table.getRowCount());
			JButton deleteButton = (JButton) find(JButton.class, "deleteButton");
			getHelper().enterClickAndLeave(new JTableMouseEventData(this, table, 0, 0, 1));
			getHelper().enterClickAndLeave(new MouseEventData(this, deleteButton));
			find(JPanel.class, "deletePanel");
			find(JLabel.class, "firstNameLabel");
			find(JLabel.class, "lastNameLabel");
			find(JLabel.class, "dateOfBirthLabel");

			JButton okButton = (JButton) find(JButton.class, "okButton");
			find(JButton.class, "cancelButton");
			getHelper().enterClickAndLeave(new MouseEventData(this, okButton));

			find(JPanel.class, "browsePanel");
			table = (JTable) find(JTable.class, "userTable");
			assertEquals(0, table.getRowCount());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

