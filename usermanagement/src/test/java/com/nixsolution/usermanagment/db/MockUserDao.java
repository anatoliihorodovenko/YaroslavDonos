package com.nixsolution.usermanagment.db;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.nixsolution.usermanagment.User;
import com.noxolution.usermanagment.db.ConnectionFactory;
import com.noxolution.usermanagment.db.DatabaseException;
import com.noxolution.usermanagment.db.UserDao;

public class MockUserDao implements UserDao {
	private long id = 0;
    private Map users = new HashMap();

    public User create(User user) throws DatabaseException {
        Long currentId = new Long(++id);
        user.setId(currentId);
        users.put(currentId, user);
        return user;
    }

    public void update(User user) throws DatabaseException {
        Long currentId = user.getId();
        users.remove(currentId);
        users.put(currentId, user);
    }

    public void delete(User user) throws DatabaseException {
        Long currentId = user.getId();
        users.remove(currentId);
    }

    public User find(Long id) throws DatabaseException {
        
        return (User) users.get(id);
    }

    public Collection findAll() throws DatabaseException {
        return users.values();
    }

	@Override
	public void setConnectionFactory(ConnectionFactory connectionFactory) {
		// TODO Auto-generated method stub

	}

	@Override
	public Collection find(String firstName, String lastName) throws DatabaseException {
		throw new UnsupportedOperationException();
	}

}