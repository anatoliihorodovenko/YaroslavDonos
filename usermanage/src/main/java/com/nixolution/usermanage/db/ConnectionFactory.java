package com.nixolution.usermanage.db;

import java.sql.Connection;

public interface ConnectionFactory {

	Connection createConnection() throws DatabaseException;
	
}
