package learningagent.database;

import java.sql.*;

/**
 * Explanation:
 *      This class allows you to establish a connection to your MySQL database and execute a query to retrieve the data.
 *      The key here is the setFetchSize(Integer.MIN_VALUE) statement in the getResultSet method.
 *      This tells the MySQL JDBC driver to stream the data as it is retrieved, rather than loading it into memory all at once.
 */

public class ChessDataRetriever {
    private Connection connection;
    private String query;
    private ResultSet resultSet;

    public ChessDataRetriever(String url, String username, String password) {
        try {
            // Connect to the MySQL database
            connection = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public ResultSet getResultSet() {
        try {
            // Execute the query and get the ResultSet
            Statement statement = connection.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            statement.setFetchSize(Integer.MIN_VALUE);
            resultSet = statement.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultSet;
    }

}

