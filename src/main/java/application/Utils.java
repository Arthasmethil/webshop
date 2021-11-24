package application;

import constants.Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

public class Utils {
    public static String simpleStringConsoleInput() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }
    
    public static long parseToLong(String stringWithDigits) {
        boolean matcher = stringWithDigits.matches("\\d*");
        if (!matcher || stringWithDigits.equals("")) {
            System.out.println("Wrong value, put it only positive digits");
            return parseToLong(simpleStringConsoleInput());
        }
        long resultOfParsingString;
        try {
            resultOfParsingString = Long.parseLong(stringWithDigits);
        } catch (NumberFormatException e) {
            System.out.println("This value is too big, input value until 9,223,372,036,854,775,807");
            return parseToLong(simpleStringConsoleInput());
        }
        return resultOfParsingString;
    }
    
    public static Connection openConnection () {
        try {
            return DriverManager.getConnection(Database.URL, Database.USER_NAME, Database.PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public static Boolean closeConnection (Connection connection) {
        try {
            connection.close();
            return connection.isValid(0);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
}
