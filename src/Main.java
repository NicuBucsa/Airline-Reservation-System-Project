import constants.Commands;
import data.FileInfo;
import logic.AirlineManager;
import logic.ReaderManager;
import java.io.File;
import java.sql.*;

public class Main {

    public static void main(String[] args) throws SQLException {
     /*   FileInfo.createResources(new File("resources"));
        FileInfo.createInput(new File("resources/input.txt"));
        FileInfo.createOutput(new File("resources/output.txt"));*/

        AirlineManager airlineManager = new AirlineManager();
        ReaderManager readerManager = new ReaderManager();

        String line = readerManager.readLine();
        while (line != null) {
            String[] arguments = line.split(" ");
            Commands command;
            try {
                command = Commands.valueOf(arguments[0]);
            } catch (IllegalArgumentException e) {
                command = Commands.DEFAULT_COMMAND;
            }
            switch (command) {
                case SIGNUP: {
                    airlineManager.signUp(arguments);
                    break;
                }
                case LOGIN: {
                    airlineManager.login(arguments);
                    break;
                }
                case LOGOUT: {
                    airlineManager.logout(arguments);
                    break;
                }
                case ADD_FLIGHT: {
                    airlineManager.addFlight(arguments);
                    break;
                }
                case CANCEL_FLIGHT: {
                    airlineManager.cancelFlight(arguments);
                    break;
                }
                case DISPLAY_MY_FLIGHTS: {
                    airlineManager.displayMyFlights(arguments);
                    break;
                }
                case ADD_FLIGHT_DETAILS: {
                    airlineManager.addFlightDetails(arguments);
                    break;
                }
                case DELETE_FLIGHT: {
                    airlineManager.deleteFlight(arguments);
                    break;
                }
                case DISPLAY_FLIGHTS: {
                    airlineManager.displayFlights();
                    break;
                }
                case PERSIST_FLIGHTS: {
                    airlineManager.persistFlights();
                    break;
                }
                case PERSIST_USERS: {
                    airlineManager.persistUser();
                    break;
                }
                case STATISTICS: {
                    airlineManager.findMostUsedCityAsDepartureForFlights();
                    airlineManager.findShortestFlight();
                    airlineManager.findUserWhoTravelTheMost();
                    break;
                }
                case SCITY: {
                    airlineManager.findAllUsersWhoTraveledToCity(arguments);
                    break;
                }
                case SDATES: {
                    airlineManager.findAllFlightsBetweenDates(arguments);
                    break;
                }
                case SDATE: {
                    airlineManager.findAllUsersWhoTraveledIn(arguments);
                    break;
                }
                case DEFAULT_COMMAND:{
                    airlineManager.defaultCommand(arguments);
                }
            }
            line = readerManager.readLine();
        }
        airlineManager.getWriterManager().flush();
    }
}
