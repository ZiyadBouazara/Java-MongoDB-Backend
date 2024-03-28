package ca.ulaval.glo2003.controllers;

import ca.ulaval.glo2003.controllers.responses.ReservationResponse;
import ca.ulaval.glo2003.service.ReservationService;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("reservations")
public class ReservationResource {
    private final ReservationService reservationService;

    @Inject
    public ReservationResource(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public ReservationResponse getReservation(@PathParam("id") String reservationId) throws NotFoundException {
        return reservationService.getReservation(reservationId);
    }
}
