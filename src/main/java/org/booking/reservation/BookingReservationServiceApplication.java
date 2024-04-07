package org.booking.reservation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class BookingReservationServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(BookingReservationServiceApplication.class, args);
    }

}
