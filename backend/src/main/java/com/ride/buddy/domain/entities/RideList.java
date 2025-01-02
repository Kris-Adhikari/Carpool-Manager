package com.ride.buddy.domain.entities;

import jakarta.persistence.*;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "ride_lists")
public class RideList {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "community", nullable = false)
    private String community;

    @OneToMany(mappedBy = "rideList", cascade = {
            CascadeType.REMOVE, CascadeType.PERSIST
    })
    private List<Ride> rides;

    public RideList() {
    }

    public RideList(UUID id, String community, List<Ride> rides) {
        this.id = id;
        this.community = community;
        this.rides = rides;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getCommunity() {
        return community;
    }

    public void setCommunity(String community) {
        this.community = community;
    }

    public List<Ride> getRides() {
        return rides;
    }

    public void setRides(List<Ride> rides) {
        this.rides = rides;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        RideList rideList = (RideList) o;
        return Objects.equals(id, rideList.id) && Objects.equals(community, rideList.community) && Objects.equals(rides, rideList.rides);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, community, rides);
    }

    @Override
    public String toString() {
        return "RideList{" +
                "id=" + id +
                ", community='" + community + '\'' +
                ", rides=" + rides +
                '}';
    }
}