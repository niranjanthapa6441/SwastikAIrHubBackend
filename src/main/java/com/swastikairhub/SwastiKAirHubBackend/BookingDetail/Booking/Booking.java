package com.swastikairhub.SwastiKAirHubBackend.BookingDetail.Booking;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.swastikairhub.SwastiKAirHubBackend.Customer.Customer;
import com.swastikairhub.SwastiKAirHubBackend.FlightDetail.FlightTicket.FlightTicket;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Data
@Table(name = "booking")
public class Booking {
    @Id
    @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    @Column(name = "booking_id")
    private String id;

    @ManyToOne
    @JoinColumn(name = "flight_ticket_id")
    private FlightTicket flightTicket;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(name = "booking_date",nullable = false)
    private LocalDate bookingDate;

    @JsonFormat(pattern = "HH:mm:ss")
    @Column(name = "booking_time",nullable = false)
    private LocalTime bookingTime;

    @Column(name = "number_of_traveller",nullable = false)
    private int numberOfTraveller;

    @Column(name = "total_ticket_price",nullable = false)
    private int totalTicketPrice;

    private String status;
}
