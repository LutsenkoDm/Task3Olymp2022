package ru.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
public class Raffle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    long id;
    @ManyToOne
    private Participant winner;
    @ManyToOne
    private Prize prize;

    public Raffle(Participant winner, Prize prize) {
        this.winner = winner;
        this.prize = prize;
    }

    public Raffle() {

    }

    public long getId() {
        return id;
    }

    public Participant getWinner() {
        return winner;
    }

    public void setWinner(Participant participant) {
        this.winner = participant;
    }

    public Prize getPrize() {
        return prize;
    }

    public void setPrize(Prize prize) {
        this.prize = prize;
    }
}
