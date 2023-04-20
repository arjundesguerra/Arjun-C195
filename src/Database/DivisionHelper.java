package Database;
import Models.Division;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;

public class DivisionHelper {

    public static ObservableList<Division> fetchDivisions(int countryID) throws SQLException {
        ObservableList<Division> divisionList = FXCollections.observableArrayList();

        PreparedStatement statement = JDBC.getConnection().prepareStatement(
                "SELECT d.Division_ID, d.Division " +
                        "FROM first_level_divisions d " +
                        "JOIN countries c ON d.COUNTRY_ID = c.Country_ID " +
                        "WHERE c.Country_ID = ? "
        );
        statement.setInt(1, countryID);

        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
            int divisionID = resultSet.getInt("Division_ID");
            String divisionName = resultSet.getString("Division");

            Division d = new Division(divisionID, divisionName);
            divisionList.add(d);
        }

        return divisionList;
    }
}

