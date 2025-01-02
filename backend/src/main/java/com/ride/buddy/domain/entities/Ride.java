package com.ride.buddy.domain.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "rides")
public class Ride {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "car", nullable = false)
    private String car;

    @Column(name = "departure")
    private LocalDateTime departure;

    @Column(name = "status", nullable = false)
    private RideStatus status;

    @Column(name = "time", nullable = false)
    private RideTime time;

    @Column(name = "contact", nullable = false)
    private String contact;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ride_list_id")
    private RideList rideList;

    @Column(name = "created", nullable = false)
    private LocalDateTime created;

    @Column(name = "updated", nullable = false)
    private LocalDateTime updated;

    public Ride() {
    }

    public Ride(UUID id, String car, LocalDateTime departure, RideStatus status, RideTime time, String contact, RideList rideList, LocalDateTime created, LocalDateTime updated) {
        this.id = id;
        this.car = car;
        this.departure = departure;
        this.status = status;
        this.time = time;
        this.contact = contact;
        this.rideList = rideList;
        this.created = created;
        this.updated = updated;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getCar() {
        return car;
    }

    public void setCar(String car) {
        this.car = car;
    }

    public LocalDateTime getDeparture() {
        return departure;
    }

    public void setDeparture(LocalDateTime departure) {
        this.departure = departure;
    }

    public RideStatus getStatus() {
        return status;
    }

    public void setStatus(RideStatus status) {
        this.status = status;
    }

    public RideTime getTime() {
        return time;
    }

    public void setTime(RideTime time) {
        this.time = time;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public RideList getRideList() {
        return rideList;
    }

    public void setRideList(RideList rideList) {
        this.rideList = rideList;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public LocalDateTime getUpdated() {
        return updated;
    }

    public void setUpdated(LocalDateTime updated) {
        this.updated = updated;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Ride ride = (Ride) o;
        return Objects.equals(id, ride.id) && Objects.equals(car, ride.car) && Objects.equals(departure, ride.departure) && status == ride.status && time == ride.time && Objects.equals(contact, ride.contact) && Objects.equals(rideList, ride.rideList) && Objects.equals(created, ride.created) && Objects.equals(updated, ride.updated);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, car, departure, status, time, contact, rideList, created, updated);
    }

    @Override
    public String toString() {
        return "Ride{" +
                "id=" + id +
                ", car='" + car + '\'' +
                ", departure=" + departure +
                ", status=" + status +
                ", time=" + time +
                ", contact='" + contact + '\'' +
                ", rideList=" + rideList +
                ", created=" + created +
                ", updated=" + updated +
                '}';
    }
}
