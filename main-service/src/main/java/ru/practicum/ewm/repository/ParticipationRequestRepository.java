package ru.practicum.ewm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.ewm.model.ParticipationRequest;

import java.util.List;

public interface ParticipationRequestRepository extends JpaRepository<ParticipationRequest, Long> {

    //ParticipationRequest findByEventIdAndRequesterId(Long eventId, Long userId);

    int countAllByEventId(Long eventId);

    List<ParticipationRequest> findAllByRequesterId(Long userId);
}
