package ca.ulaval.glo2003.controllers;

import ca.ulaval.glo2003.Main;
import ca.ulaval.glo2003.controllers.requests.ReservationRequest;
import ca.ulaval.glo2003.controllers.validators.CreateReservationValidator;
import ca.ulaval.glo2003.domain.exceptions.InvalidParameterException;
import ca.ulaval.glo2003.domain.exceptions.MissingParameterException;
import ca.ulaval.glo2003.service.ReservationService;
import jakarta.inject.Inject;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriBuilder;

import java.net.URI;

public class ReservationResource {

    private final ReservationService reservationService;
    private final CreateReservationValidator createReservationValidator;

    @Inject
    public ReservationResource(ReservationService reservationService,
                              CreateReservationValidator createReservationValidator) {

        this.reservationService = reservationService;
        this.createReservationValidator = createReservationValidator;
    }

    @POST
    @Path("restaurants/{id}/reservations")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createReservation(@PathParam("id") String restaurantId, ReservationRequest reservationRequest)
            throws InvalidParameterException, MissingParameterException {
        createReservationValidator.validateReservationRequest(reservationRequest);

        String createdReservationId = reservationService.createReservation(
                restaurantId,
                reservationRequest.date(),
                reservationRequest.startTime(),
                reservationRequest.groupSize(),
                reservationRequest.customer());

        URI newReservationURI = UriBuilder.fromPath(Main.BASE_URI)
                .path("reservations")
                .path(createdReservationId)
                .build();
        return Response.created(newReservationURI).build();
    }

}
