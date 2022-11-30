package constants;

import data.Flight;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public class Messages {
    // MESSAGES USER
    //Messages SIGNUP
    public static String userAlreadyExists() {
        return "User already exists!";
    }

    public static String userWithEmailSuccessfullyAdded(String email) {
        return "User with email: <" + email + "> was successfully added!";
    }

    public static String cannotAddUserPasswordTooShort() {
        return "Cannot add user! Password too short!";
    }

    public static String cannotAddUserPasswordAreDifferent() {
        return "Cannot add user! The passwords are different!";
    }

    //Messages LOGIN
    public static String cannotFindUserWithEmail(String email) {
        return "Cannot find user with email: <" + email + ">.";
    }

    public static String incorrectPassword() {
        return "Incorrect password!";
    }

    public static String userWithEmailIsTheCurrentUser(String email, LocalDateTime currentDateAndTime) {
        return "User with email: <" + email + "> is the current user started from <" + currentDateAndTime + ">.";
    }

    public static String anotherUserIsAlreadyConnected() {
        return "Another user is already connected!";
    }

    //Messages LOGOUT
    public static String theUserWithEmailWasNotConnected(String email) {
        return "The user with email: <" + email + "> was not connected!";
    }

    public static String userWithEmailSuccessfullyDisconnected(String email, LocalDateTime currentDateAndTime) {
        return "The user with email: <" + email + "> successfully disconnected at <" + currentDateAndTime + ">!";
    }

    //Messages DISPLAY_MY_FLIGHTS
    public static String noConnectedUser() {
        return "There is no connected user!";
    }

    public static String flightFromToDateDuration(String from, String to, LocalDate date, int duration) {
        return "Flight from <" + from + "> to <" + to + ">, date <" + date + ">, duration <" + duration + ">.";
    }

    //Messages ADD_FLIGHT
    public static String theFlightWithIdDoesNotExists(String flight_id) {
        return "The flight with id <" + flight_id + "> does not exist!";
    }

    public static String theUserWithEmailAlreadyHaveAticket(String email, int flight_Id) {
        return "The user with email: <" + email + "> already have a ticket for flight with id <" + flight_Id + ">.";
    }

    public static String theFlightWithIdWasSuccessfullyAddedForUser(int flight_id, String curentUserEmail) {
        return "The flight with id <" + flight_id + "> was successfully added for user with email: <" + curentUserEmail + ">.";
    }

    //Messages CANCEL_FLIGHT
    public static String theUserDoesNotHaveAticket(String email, int flight_id) {
        return "The user with email: <" + email + "> does not have a ticket for the flight with id <" + flight_id + ">.";
    }

    public static String theUserWithEmailSuccessfullyCanceledTicketForFlight(String email, int flight_id) {
        return "The user with email: <" + email + "> has successfully canceled his ticket for flight with id <" + flight_id + ">.";
    }

    //MESSAGES FLIGHTS INSTRUCTIONS
    //Messages ADD_FLIGHT_DETAILS
    public static String cannotAddFlightIsAlreadyAflightWithId(int flight_id) {
        return "Cannot add flight! There is already a flight with id = <" + flight_id + ">.";
    }

    public static String flightFromToDateDurationSuccessfullyAdded(String from, String to, LocalDate date, int duration) {
        return "Flight from <" + from + "> to <" + to + ">, date <" + date + ">, duration <" + duration + "> successfully added!";
    }

    //Messages DELETE_FLIGHT
    public static String flightWithIdSuccessfullyDeleted(String flight_id) {
        return "The flights with id <" + flight_id + "> successfully deleted.";
    }

    public static String userWithEmailNotifiedTheFlightCanceled(String email, String flight_id) {
        return "The user with email: <" + email + "> was notified that the flight with id <" + flight_id + "> was canceled!";
    }

    //MESSAGES TABELES
    //Message PERSIST_FLIGHT
    public static String flightsSuccessfullySavedInDatabase(LocalTime current_time) {
        return "The flight was successfully saved in the database at <" + current_time + ">!";
    }

    //Message PERSIST_USERS
    public static String usersSuccessfullySavedInDatabase(LocalTime current_time) {
        return "The users was successfully saved in the database at <" + current_time + ">!";
    }

    //MESSAGES STATISTICS
    public static String theMostUsedCityAsdepartureForFlights(String city) {
        return "The most used city as departure for flights is: " + city;
    }

    public static String theShortestFlight(String city) {
        return "The shortest flight is :" + city;
    }

    public static String flightsBetwenDates(List<Flight> flights, LocalDate start, LocalDate end) {
        return "The flights between" + start + " and " + end + " are: " + flights;
    }

    public static String allUsersWhoTraveledToCity(List<String> users, String city) {
        return "Users who traveled to " + city + " are: " + users;
    }

    public static String allUsersWhoTraveledIn(List<String> users, LocalDate date) {
        return "Users who traveled in date of " + date + " are: " + users;
    }

    public static String userWhoTraveledTheMost(String user) {
        return "The user who traveled the most is: " + user;
    }

    public static String commandIsNotValid(){
        return "This command is not valid! Try again!";
    }
}
