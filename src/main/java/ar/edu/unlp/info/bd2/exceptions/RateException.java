package ar.edu.unlp.info.bd2.exceptions;

public class RateException extends Exception {
    public RateException() { super("Reservation is not finished so it's not possible to rate it"); }
}
