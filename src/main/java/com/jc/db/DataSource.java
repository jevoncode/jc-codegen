package com.jc.db;

import java.sql.Connection;
import java.sql.SQLException;

public interface DataSource {

   Connection getConnection() ;
}
