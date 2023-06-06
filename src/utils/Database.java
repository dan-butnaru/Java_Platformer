package utils;

import java.sql.*;

public class Database {
    private Connection connection;
    public Database()
    {
        connection = null;
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:database.db");
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        System.out.println("Opened database successfully");
    }




    public void saveScoreToDatabase(String playerName, int score) {
        try {
            Statement statement = connection.createStatement();
            String query = "SELECT Score FROM LEADERBOARD WHERE Name= '"+playerName+"'";
            ResultSet resultSet = statement.executeQuery(query);

            if (resultSet.next()) {
                int existingScore = resultSet.getInt("Score");

                if (score < existingScore) {
                    String updateQuery = "UPDATE LEADERBOARD SET Score = " + score + " WHERE Name = '" + playerName + "'";
                    statement.executeUpdate(updateQuery);
                }
            } else {
                String insertQuery = "INSERT INTO LEADERBOARD (Name, Score) VALUES ('" + playerName + "', " + score + ")";
                statement.executeUpdate(insertQuery);
            }

            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void close(){
        try {
            if (connection != null) {
                connection.close();
                System.out.println("Database connection closed.");
            }
        } catch (SQLException e) {
            System.err.println("Failed to close the database connection.");
            e.printStackTrace();
        }
    }

    public String[] bestScore() {
        String[] bestScores = new String[3];
        try {
            Statement statement = connection.createStatement();
            String query = "SELECT Name, Score FROM LEADERBOARD ORDER BY Score ASC LIMIT 3";
            ResultSet resultSet = statement.executeQuery(query);

            int index = 0;
            while (resultSet.next() && index < 3) {
                String playerName = resultSet.getString("Name");
                int score = resultSet.getInt("Score");
                String scoreEntry = playerName + ": " + score;
                bestScores[index] = scoreEntry;
                index++;
            }

            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bestScores;
    }


}