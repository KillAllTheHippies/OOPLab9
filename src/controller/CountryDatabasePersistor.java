package controller;

import model.Country;
import model.Player;

import java.sql.*;
import java.util.ArrayList;

public class CountryDatabasePersistor implements IPersistor {

	private Connection dbConnection;


	public CountryDatabasePersistor() {

		//Start the driver for MYSQL
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		try {
			dbConnection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/oop2?" + "user=root&password=");
		} catch (SQLException e) {
			e.printStackTrace();
		}


	}

	@Override
	public void write(ArrayList<Player> players) {
		for (Player currPlayer: players) {
			PreparedStatement prepStmt = null;
			try {
				prepStmt = dbConnection.prepareStatement("INSERT into oop2.players VALUES (?,?,?)");
				prepStmt.setString(1, currPlayer.getName());
				prepStmt.setInt(2, currPlayer.getAge());
				prepStmt.setInt(3, currPlayer.getNoOfGoals());

				prepStmt.executeUpdate();
				prepStmt.close();

			} catch (SQLException e) {
				e.printStackTrace();
			}


		}
		// TODO Auto-generated method stub

	}

	@Override
	public ArrayList<Player> read() {

		//Create an empty list
		ArrayList<Player> playersList = new ArrayList<Player>();

		try {
			Statement getAllPlayersStatement = dbConnection.createStatement();

			ResultSet rs = getAllPlayersStatement.executeQuery("SELECT * from oop2.players");

			while (rs.next()) {
				// Give me the data in column "name" at the row at which the ResultSet is
				// Currently pointing at
				String currentName = rs.getString("name");
				int currAge = rs.getInt("age");
				int goalsScored = rs.getInt("goalsScored");
				// Re-create a Player object and initialise it with the raw data we have just extracted
				// from the row in the database.
				Player p = new Player(currentName, currAge, 0, goalsScored);
				playersList.add(p);
				//Close the connection to the database

			}
			getAllPlayersStatement.close();
			rs.close();

		} catch (SQLException sqlEx) {
			System.out.println(sqlEx.getMessage());
		}
		return playersList;

	}

}
