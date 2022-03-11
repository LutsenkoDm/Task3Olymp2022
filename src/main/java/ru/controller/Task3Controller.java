package ru.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.repository.ParticipantRepository;
import ru.model.Participant;
import ru.model.Prize;
import ru.model.Promo;
import ru.model.Raffle;
import ru.repository.PrizeRepository;
import ru.repository.PromoRepository;
import ru.repository.RaffleRepository;

import java.util.*;

@RestController
public class Task3Controller {

    @Autowired
    private PrizeRepository prizeRepository;

    @Autowired
    private ParticipantRepository participantRepository;

    @Autowired
    private PromoRepository promoRepository;

    @Autowired
    private RaffleRepository raffleRepository;


    @PostMapping(value = "/promo", consumes = "application/json", produces = "application/json")
    public Long addPromo(@RequestBody Promo promo) {
        if (promo.getName() == null || promo.getName().length() == 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Name is invalid");
        }
        return promoRepository.save(promo).getId();
    }

    @GetMapping("/promo")
    public ResponseEntity<List<Object[]>> promos() {
        return new ResponseEntity<>(promoRepository.promos(), HttpStatus.OK);
    }

    @GetMapping("/promo/{id}")
    public ResponseEntity<Promo> getPromoById(@PathVariable("id") long id) {
        Optional<Promo> optionalPromo = promoRepository.findById(id);
        if (optionalPromo.isPresent()) {
            return new ResponseEntity<>(optionalPromo.get(), HttpStatus.OK);
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Promo not found");
    }

    @PutMapping("/promo/{id}")
    public void putPromoById(@PathVariable("id") long id, @RequestBody Promo promo) {
        if (promo.getName() == null || promo.getName().length() == 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Name is invalid");
        }
        Optional<Promo> optionalPromo = promoRepository.findById(id);
        if (optionalPromo.isPresent()) {
            Promo updatingPromo = optionalPromo.get();
            updatingPromo.setName(promo.getName());
            updatingPromo.setDescription(promo.getDescription());
            promoRepository.save(updatingPromo);
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Promo not found");
    }

    @DeleteMapping("/promo/{id}")
    public void deletePromoById(@PathVariable("id") long id) {
        try {
            promoRepository.deleteById(id);
        } catch (Exception exception) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Promo not found");
        }
    }

    @PostMapping(value = "/promo/{id}/participant", consumes = "application/json", produces = "application/json")
    public Long addParticipant(@PathVariable("id") long id, @RequestBody Participant addingParticipant) {
        if (addingParticipant.getName() == null || addingParticipant.getName().length() == 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Name is invalid");
        }
        Optional<Promo> optionalPromo = promoRepository.findById(id);
        Optional<Participant> optionalParticipant = participantRepository.findByName(addingParticipant.getName());
        if (optionalPromo.isPresent()) {
            Participant participant;
            if (optionalParticipant.isEmpty()) {
                participant = new Participant(addingParticipant.getName());
                participantRepository.save(participant);
            } else {
                participant = optionalParticipant.get();
            }
            optionalPromo.get().getParticipants().add(participant);
            promoRepository.save(optionalPromo.get());
            return participant.getId();
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Promo not found");
    }

    @DeleteMapping(value = "/promo/{promoId}/participant/{participantId}")
    public void deleteParticipant(@PathVariable("promoId") long promoId, @PathVariable("participantId") long participantId) {
        Optional<Promo> optionalPromo = promoRepository.findById(promoId);
        Optional<Participant> optionalParticipant = participantRepository.findById(participantId);
        if (optionalPromo.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Promo not found");
        }
        if (optionalParticipant.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Participant not found");
        }
        Promo promo = optionalPromo.get();
        promo.getParticipants().remove(optionalParticipant.get());
        promoRepository.save(promo);
    }

    @PostMapping(value = "/promo/{id}/prize", consumes = "application/json", produces = "application/json")
    public Long addPrize(@PathVariable("id") long id, @RequestBody Prize addingPrize) {
        Optional<Promo> optionalPromo = promoRepository.findById(id);
        Optional<Prize> optionalPrize = prizeRepository.findByDescription(addingPrize.getDescription());
        if (optionalPromo.isPresent()) {
            Prize prize;
            if (optionalPrize.isEmpty()) {
                prize = new Prize(addingPrize.getDescription());
                prizeRepository.save(prize);
            } else {
                prize = optionalPrize.get();
            }
            optionalPromo.get().getPrizes().add(prize);
            promoRepository.save(optionalPromo.get());
            return prize.getId();
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Promo not found");
    }

    @DeleteMapping(value = "/promo/{promoId}/prize/{prizeId}")
    public void deletePrize(@PathVariable("promoId") long promoId, @PathVariable("prizeId") long prizeId) {
        Optional<Promo> optionalPromo = promoRepository.findById(promoId);
        Optional<Prize> optionalPrize = prizeRepository.findById(prizeId);
        if (optionalPromo.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Promo not found");
        }
        if (optionalPrize.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Prize not found");
        }
        Promo promo = optionalPromo.get();
        promo.getPrizes().remove(optionalPrize.get());
        promoRepository.save(promo);
    }

    @PostMapping(value = "/promo/{id}/raffle", produces = "application/json")
    public ResponseEntity<List<Raffle>> raffle(@PathVariable("id") long id) {
        Optional<Promo> optionalPromo = promoRepository.findById(id);
        if (optionalPromo.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Promo not found");
        }
        Promo promo = optionalPromo.get();
        Set<Participant> participants = promo.getParticipants();
        Set<Prize> prizes = promo.getPrizes();
        if (participants.size() != prizes.size()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Promo not found");
        }
        List<Raffle> result = new ArrayList<>();
        Iterator<Prize> prizeIterator = prizes.iterator();
        for (Participant participant : participants) {
            result.add(new Raffle(participant, prizeIterator.next()));
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
