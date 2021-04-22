package ar.edu.unlp.info.bd2.model;

import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="reservations")
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="reservationId")
    private Long id;

    @Column(name = "date_from", nullable = false)
    private Date from;

    @Column(name = "date_to", nullable = false)
    private Date to;

    @Column(name="price", nullable = false)
    private double price;

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="apartmentId")
    private Property apartment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", nullable = false)
    private User user;

    @Column(name = "status", nullable = false)
    private String status;

    @OneToOne
    @JoinColumn(name="ratingId")
    private ReservationRating rating;

    public Reservation(){}

    public Reservation(Property apartment, User user, Date from, Date to ) {
        this.from = from;
        this.to = to;
        this.setApartment(apartment);
        this.setUser(user);
        this.status = ReservationStatus.UNCONFIRMED;
        this.price = this.nigths() * apartment.getPrice();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getFrom() {
        return from;
    }

    public void setFrom(Date from) {
        this.from = from;
    }

    public Date getTo() {
        return to;
    }

    public void setTo(Date to) {
        this.to = to;
    }

    public Double getPrice() {
        return price;
    }

    public Property getApartment() {
        return apartment;
    }

    public void setApartment(Property apartment) {
        this.apartment = apartment;
        apartment.addReservation(this);
    }

    public void setUser(User user) {
        this.user = user;
        user.addReservation(this);
    }

    public User getUser() {
        return user;
    }

    public int nigths(){
        final long MILLSECS_PER_DAY = 24 * 60 * 60 * 1000;
        return (int)((to.getTime() - from.getTime()) / MILLSECS_PER_DAY);
    }

    public Property getProperty(){
        return this.getApartment();
    }

    public String toString() {
        return "Reservation{" +
                "id='" + id + '\'' +
                ", from='" + from + '\'' +
                ", to=" + to +
                ", apartment=" + apartment +
                ", user=" + user +
                ", status=" + status +
                ", rating=" + rating +
                '}';
    }

    public String getStatus() {
        return status;
    }

    public Reservation cancelReservation() {
        this.status = ReservationStatus.CANCELED;
        return this;
    }

    public Reservation finishReservation() {
        this.status = ReservationStatus.FINISHED;
        return this;
    }

    public ReservationRating getRating() {
        return rating;
    }

    public void setRating(ReservationRating rating) {
        this.rating = rating;
    }
}
