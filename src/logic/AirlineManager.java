package logic;
import data.Flight;
import data.User;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

import static constants.Messages.*;
import static logic.SQLClass.*;

public class AirlineManager {
    LocalDateTime currentDateAndTime = LocalDateTime.now();
    private WriterManager writerManager = new WriterManager();
    private List<User> allUsers = new ArrayList<>();
    private User currentUser;
    private List<Flight> allFlights = new LinkedList<>();
    private List<Flight> allUsersFlights = new ArrayList<>();

    public List<Flight> getAllUsersFlights() {
        return allUsersFlights;
    }

    public List<Flight> getAllFlights() {
        return allFlights;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AirlineManager that = (AirlineManager) o;
        return Objects.equals(currentDateAndTime, that.currentDateAndTime) && Objects.equals(writerManager, that.writerManager) && Objects.equals(allUsers, that.allUsers) && Objects.equals(currentUser, that.currentUser) && Objects.equals(allFlights, that.allFlights) && Objects.equals(allUsersFlights, that.allUsersFlights);
    }

    @Override
    public int hashCode() {
        return Objects.hash(currentDateAndTime, writerManager, allUsers, currentUser, allFlights, allUsersFlights);
    }

    public WriterManager getWriterManager() {
        return writerManager;
    }

    // USER
    public void signUp(String[] arguments) {
        String email = arguments[1];
        String name = arguments[2];
        String password = arguments[3];
        String password2 = arguments[4];

        Optional<User> optionalUser = allUsers.stream()
                .filter(user -> user.getEmail().equals(email))
                .findFirst();

        if (optionalUser.isPresent()) {
            writerManager.write(userAlreadyExists());
            return;
        }
        if (!password.equals(password2)) {
            writerManager.write(cannotAddUserPasswordAreDifferent());
        } else if (password.length() < 8) {
            writerManager.write(cannotAddUserPasswordTooShort());
        } else {
            User user = new User(email, name, password);
            allUsers.add(user);
            writerManager.write(userWithEmailSuccessfullyAdded(user.getEmail()));
        }
    }

    public void login(String[] arguments) {
        String email = arguments[1];
        String password = arguments[2];

        Optional<User> optionalUser = allUsers.stream()
                .filter(user -> user.getEmail().equals(email))
                .findFirst();

        if (optionalUser.isEmpty()) {
            writerManager.write(cannotFindUserWithEmail(email));
            return;
        }
        User user = optionalUser.get();
        if (!user.getPassword().equals(password)) {
            writerManager.write(incorrectPassword());
            return;
        }
        if (currentUser != null) {
            writerManager.write(anotherUserIsAlreadyConnected());
            return;
        }
        currentUser = user;
        writerManager.write(userWithEmailIsTheCurrentUser(email, currentDateAndTime));
    }

    public void logout(String[] arguments) {
        String email = arguments[1];

        if (currentUser == null || (!currentUser.getEmail().equals(email))) {
            writerManager.write(theUserWithEmailWasNotConnected(email));
        } else if (currentUser.getEmail().equals(email)) {
            currentUser = null;
            writerManager.write(userWithEmailSuccessfullyDisconnected(email, currentDateAndTime));
        }
    }

    public void addFlight(String[] arguments) {
        if (currentUser == null) {
            writerManager.write(noConnectedUser());
            return;
        }
        Optional<Flight> optionalFlight = allFlights.stream()
                .filter(flight -> flight.getFlight_id() == Integer.parseInt(arguments[1]))
                .findFirst();
        Optional<Flight> optionalUserFlights = currentUser.getUserFlights().stream()
                .filter(flight -> flight.getFlight_id() == Integer.parseInt(arguments[1]))
                .findFirst();
        Optional<User> optionalUser = allUsers.stream()
                .filter(user -> user.getEmail().equals(currentUser.getEmail()))
                .findFirst();

        if (optionalUser.isEmpty()) {
            writerManager.write(noConnectedUser());
        } else if (optionalFlight.isEmpty()) {
            writerManager.write(theFlightWithIdDoesNotExists(arguments[1]));
        } else if (optionalUserFlights.isPresent()) {
            writerManager.write(theUserWithEmailAlreadyHaveAticket(currentUser.getEmail(), Integer.parseInt(arguments[1])));
        } else {
            Flight flight = optionalFlight.get();
            currentUser.addFlight(flight);
            allUsersFlights.add(flight);
            writerManager.write(theFlightWithIdWasSuccessfullyAddedForUser(Integer.parseInt(arguments[1]), currentUser.getEmail()));
        }
    }

    public void displayMyFlights(String[] arguments) {
        if (currentUser == null) {
            writerManager.write(noConnectedUser());
            return;
        }
        Optional<User> optionalUser = allUsers.stream()
                .filter(user -> user.getEmail().equals(currentUser.getEmail()))
                .findFirst();

        if (optionalUser.isEmpty()) {
            writerManager.write(noConnectedUser());
        } else {
            for (Flight flight : currentUser.getUserFlights()) {
                writerManager.write(flightFromToDateDuration(flight.getFromCity(), flight.getToCity(), flight.getDate(), flight.getDuration()));
            }
        }
    }

    public void cancelFlight(String[] arguments) {
        if (currentUser == null) {
            writerManager.write(noConnectedUser());
            return;
        }
        Optional<Flight> optionalFlight = allFlights.stream()
                .filter(flight -> flight.getFlight_id() == Integer.parseInt(arguments[1]))
                .findFirst();

        Optional<Flight> optionalUserFlights = currentUser.getUserFlights().stream()
                .filter(flight -> flight.getFlight_id() == Integer.parseInt(arguments[1]))
                .findFirst();

        Optional<User> optionalUser = allUsers.stream()
                .filter(user -> user.getEmail().equals(currentUser.getEmail()))
                .findFirst();

        if (optionalUser.isEmpty()) {
            writerManager.write(noConnectedUser());
        } else if (optionalFlight.isEmpty()) {
            writerManager.write(theFlightWithIdDoesNotExists(arguments[1]));
        } else if (optionalUserFlights.isEmpty()) {
            writerManager.write(theUserDoesNotHaveAticket(currentUser.getEmail(), Integer.parseInt(arguments[1])));
        } else {
            Flight flight = optionalFlight.get();
            currentUser.deleteFlight(flight);
            allUsersFlights.remove(flight);
            writerManager.write(theUserWithEmailSuccessfullyCanceledTicketForFlight(currentUser.getEmail(), Integer.parseInt(arguments[1])));
        }
    }

    // FLIGHTS
    public void addFlightDetails(String[] arguments) {
        int flight_id = Integer.parseInt(arguments[1]);
        String fromCity = arguments[2];
        String toCity = arguments[3];
        LocalDate date = LocalDate.parse((arguments[4]));
        int duration = Integer.parseInt(arguments[5]);

        Optional<Flight> optionalFlight = allFlights.stream()
                .filter(flight -> flight.getFlight_id() == Integer.parseInt(arguments[1]))
                .findFirst();

        Optional<Flight> optionalFlightByDate = allFlights.stream()
                .filter(flight -> flight.getDate().equals(date))
                .findFirst();

        if (optionalFlight.isPresent()) {
            writerManager.write(cannotAddFlightIsAlreadyAflightWithId(flight_id));
            return;
        }

        if (optionalFlightByDate.isEmpty()) {
            allFlights.add(new Flight(flight_id, fromCity, toCity, date, duration));
            writerManager.write(flightFromToDateDurationSuccessfullyAdded(fromCity, toCity, date, duration));
        }
    }

    public void deleteFlight(String[] arguments) {
        Optional<Flight> optionalFlight = allFlights.stream()
                .filter(flight -> flight.getFlight_id() == Integer.parseInt(arguments[1]))
                .findFirst();

        if (optionalFlight.isEmpty()) {
            writerManager.write(theFlightWithIdDoesNotExists(arguments[1]));
            return;
        }
        Flight flight = optionalFlight.get();
        allFlights.remove(flight);
        writerManager.write(flightWithIdSuccessfullyDeleted(arguments[1]));

        for (User user : allUsers) {
            if (user.getUserFlights().contains(flight)) {
                user.deleteFlight(flight);
                allUsersFlights.remove(flight);
                writerManager.write(userWithEmailNotifiedTheFlightCanceled(user.getEmail(), arguments[1]));
            }
        }
    }

    public void displayFlights() {
        if (!allFlights.isEmpty()) {
            for (Flight flight : getAllFlights()) {
                writerManager.write(flightFromToDateDuration(flight.getFromCity(), flight.getToCity(),
                        flight.getDate(), flight.getDuration()));
            }
        }
    }

    //TABELS
    public void persistFlights() {
        try (
                Connection connection = DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
                Statement statement = connection.createStatement();
        ) {
            for (Flight flight : allFlights) {
                String insertFlights = "INSERT INTO flights(id , fromCity , toCity , date , duration) VALUES" +
                        "('" + flight.getFlight_id() + "','" + flight.getFromCity() + "','" + flight.getToCity() + "','"
                        + flight.getDate() + "','" + flight.getDuration() + "')";

                statement.execute(insertFlights);
            }
        } catch (
                SQLException throwables) {
            throwables.printStackTrace();
        }
        writerManager.write(flightsSuccessfullySavedInDatabase(LocalTime.from(currentDateAndTime)));
    }

    public void persistUser() {
        try (
                Connection connection = DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
                Statement statement = connection.createStatement();
        ) {
            for (User user : allUsers) {
                String insertUsers = "INSERT INTO users(email , name , password) VALUES" +
                        "('" + user.getEmail() + "','" + user.getName() + "','"
                        + user.getPassword() + "')";

                statement.execute(insertUsers);
            }
        } catch (
                SQLException throwables) {
            throwables.printStackTrace();
        }
        writerManager.write(usersSuccessfullySavedInDatabase(LocalTime.from(currentDateAndTime)));
    }

    //STATISTICS
    public void findMostUsedCityAsDepartureForFlights() {
        Map<String, Integer> map = new HashMap<>();
        List<String> cityList = new ArrayList<>();
        for (Flight flight : getAllUsersFlights()) {
            cityList.add(flight.getFromCity());
        }
        for (String cit : cityList) {
            if (map.containsKey(cit)) {
                Integer currentValue = map.get(cit);
                map.put(cit, currentValue + 1);
            } else {
                map.put(cit, 1);
            }
        }
        Set<Integer> set = new TreeSet<>(map.values());
        Optional<Integer> optionalInteger = set.stream()
                .min(Comparator.comparingInt(a -> -a));
        int value = optionalInteger.get();
        String fromCity = getKey(map, value);

        writerManager.write(theMostUsedCityAsdepartureForFlights(fromCity));
    }

    public void findUserWhoTravelTheMost() {
        int duration = 0;
        User userMost = null;

        for (User user : allUsers) {
            int sum = 0;
            for (Flight flight : user.getUserFlights()) {
                sum += flight.getDuration();
            }
            if (duration < sum) {
                duration = sum;
                userMost = user;
            }
        }
        writerManager.write(userWhoTraveledTheMost(String.valueOf(userMost.getEmail())));
    }

    public void findAllUsersWhoTraveledToCity(String[] arguments) {
        String city = arguments[1];

        List<String> users = new ArrayList<>();
        for (User user : allUsers) {
            if (!user.getUserFlights().stream().filter(flight -> flight.getToCity().equals(city)).findFirst().isEmpty()) {
                users.add(user.getEmail());
            }
        }
        writerManager.write(allUsersWhoTraveledToCity(users, city));
    }

    public void findAllFlightsBetweenDates(String[] arguments) {
        LocalDate date1 = LocalDate.parse(arguments[1]);
        LocalDate date2 = LocalDate.parse(arguments[2]);
        List<Flight> flights = new ArrayList<>();
        for (Flight flight : allFlights) {
            if ((flight.getDate().isAfter(date1)) && (flight.getDate().isBefore(date2))) {
                flights.add(flight);
            }
        }
        writerManager.write(flightsBetwenDates(flights, date1, date2));
    }

    public void findShortestFlight() {
        boolean seen = false;
        Flight best = null;
        for (Flight flight : allFlights) {
            if (!seen || flight.getDuration() - best.getDuration() < 0) {
                seen = true;
                best = flight;
            }
        }
        Optional<Flight> shortestFlightDuration = seen ? Optional.of(best) : Optional.empty();
        writerManager.write(theShortestFlight(String.valueOf(shortestFlightDuration)));
    }

    public void findAllUsersWhoTraveledIn(String[] arguments) {
        LocalDate date = LocalDate.parse(arguments[1]);
        List<String> users = new ArrayList<>();
        for (User user : allUsers) {
            if (!user.getUserFlights().stream().filter(flight -> flight.getDate().equals(date)).findFirst().isEmpty()) {
                users.add(user.getEmail());
            }
        }
        writerManager.write(allUsersWhoTraveledIn(users, date));
    }

    public void defaultCommand(String [] arguments){
        writerManager.write(commandIsNotValid());
    }

    public static <K, V> K getKey(Map<K, V> map, V value) {
        for (Map.Entry<K, V> entry : map.entrySet()) {
            if (value.equals(entry.getValue())) {
                return entry.getKey();
            }
        }
        return null;
    }
}