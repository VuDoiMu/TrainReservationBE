package com.example.train_ticket_management.testing_drafting;

import com.example.train_ticket_management.model.Ticket;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Repository
public class TicketCriteriaRepository {

    private final EntityManager entityManager;
    private final CriteriaBuilder criteriaBuilder;

    public TicketCriteriaRepository(EntityManager entityManager, CriteriaBuilder criteriaBuilder) {
        this.entityManager = entityManager;
        this.criteriaBuilder = criteriaBuilder;
    }

    public Page<Ticket> findAllWithFilters(TicketPage ticketPage,
                                           TicketSearchCriteria ticketSearchCriteria){
        CriteriaQuery<Ticket> criteriaQuery = criteriaBuilder.createQuery(Ticket.class);
        Root<Ticket> ticketRoot = criteriaQuery.from(Ticket.class);
        Predicate predicate = getPredicate(ticketSearchCriteria, ticketRoot);
        criteriaQuery.where(predicate);
        setOrder(ticketPage, criteriaQuery, ticketRoot);

        TypedQuery<Ticket> typedQuery = entityManager.createQuery(criteriaQuery);
        typedQuery.setFirstResult(ticketPage.getPageNumber() * ticketPage.getPageSide());
        typedQuery.setMaxResults(ticketPage.getPageSide());

        Pageable pageable = getPageable(ticketPage);

        long ticketCount = getTicketCount(predicate);

        return new PageImpl<>(typedQuery.getResultList(), pageable, ticketCount);
    }

    private long getTicketCount(Predicate predicate) {
        CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
        Root<Ticket> countRoot = countQuery.from(Ticket.class);
        countQuery.select(criteriaBuilder.count(countRoot)).where(predicate);
        return entityManager.createQuery(countQuery).getSingleResult();
    }

    private Pageable getPageable(TicketPage ticketPage) {
        Sort sort = Sort.by(ticketPage.getSortDir(), ticketPage.getSortBy());
        return PageRequest.of(ticketPage.getPageNumber(), ticketPage.getPageSide(), sort);
    }

    private void setOrder(TicketPage ticketPage, CriteriaQuery<Ticket> criteriaQuery, Root<Ticket> ticketRoot) {
        if(ticketPage.getSortDir().equals(Sort.Direction.ASC)){
            criteriaQuery.orderBy(criteriaBuilder.asc(ticketRoot.get(ticketPage.getSortBy())));
        } else {
            criteriaQuery.orderBy(criteriaBuilder.desc(ticketRoot.get(ticketPage.getSortBy())));
        }
    }

    private Predicate getPredicate(TicketSearchCriteria ticketSearchCriteria,
                                   Root<Ticket> ticketRoot) {
        List<Predicate> predicates = new ArrayList<>();

        if(Objects.nonNull(ticketSearchCriteria.getTicketDeparture())){
            predicates.add(
                    criteriaBuilder.like(ticketRoot.get("ticketDeparture"),
                            "%" + ticketSearchCriteria.getTicketDeparture() + "%")
            );
        }

        if(Objects.nonNull(ticketSearchCriteria.getTicketDestination())){
            predicates.add(
                    criteriaBuilder.like(ticketRoot.get("ticketDestination"),
                            "%" + ticketSearchCriteria.getTicketDestination() + "%")
            );
        }

        if(Objects.nonNull(ticketSearchCriteria.getArrivalDate())){
            predicates.add(
                    criteriaBuilder.like(ticketRoot.get("arrivalDate"),
                            "%" + ticketSearchCriteria.getArrivalDate() + "%")
            );
        }

        if(Objects.nonNull(ticketSearchCriteria.getDepatureDate())){
            predicates.add(
                    criteriaBuilder.like(ticketRoot.get("departureDate"),
                            "%" + ticketSearchCriteria.getDepatureDate()+ "%")
            );
        }

        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));

    }

}
