package controllers;

import play.db.*;
import java.sql.*;
/**
 * Created by Felix on 13.05.15.
 */
public class dbConnection {
    Connection connection = DB.getConnection();
}
