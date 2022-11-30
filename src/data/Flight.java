package data;

import java.time.LocalDate;
import java.util.Objects;

public class Flight {
    private int flight_id;
    private String fromCity;
    private String toCity;
    private LocalDate date;
    private int duration;

    public Flight(int flight_id, String fromCity, String toCity, LocalDate date, int duration) {
        this.flight_id = flight_id;
        this.fromCity = fromCity;
        this.toCity = toCity;
        this.date = date;
        this.duration = duration;
    }

    public int getFlight_id() {
        return flight_id;
    }

    public String getFromCity() {
        return fromCity;
    }

    public String getToCity() {
        return toCity;
    }

    public LocalDate getDate() {
        return date;
    }

    public int getDuration() {
        return duration;
    }

    @Override
    public String toString() {
        return "Flight{" +
                "flight_id=" + flight_id +
                ", fromCity='" + fromCity + '\'' +
                ", toCity='" + toCity + '\'' +
                ", date=" + date +
                ", duration='" + duration + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Flight flight = (Flight) o;
        return flight_id == flight.flight_id && duration == flight.duration && Objects.equals(fromCity, flight.fromCity) && Objects.equals(toCity, flight.toCity) && Objects.equals(date, flight.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(flight_id, fromCity, toCity, date, duration);
    }
}
