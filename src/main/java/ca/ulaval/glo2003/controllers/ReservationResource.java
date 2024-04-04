package ca.ulaval.glo2003.controllers;

import ca.ulaval.glo2003.Main;
import ca.ulaval.glo2003.controllers.requests.ReservationRequest;
import ca.ulaval.glo2003.controllers.responses.ReservationResponse;
import ca.ulaval.glo2003.domain.exceptions.InvalidParameterException;
import ca.ulaval.glo2003.domain.exceptions.MissingParameterException;
import ca.ulaval.glo2003.service.ReservationService;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriBuilder;

import java.net.URI;

@Path("/")
public class ReservationResource {
    private final ReservationService reservationService;

    @Inject
    public ReservationResource(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @POST
    @Path("restaurants/{id}/reservations")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createReservation(@PathParam("id") String restaurantId, ReservationRequest reservationRequest)
        throws InvalidParameterException, MissingParameterException {

        String createdReservationId = reservationService.createReservation(
            restaurantId,
            reservationRequest);

        URI newReservationURI = UriBuilder.fromPath(Main.BASE_URI)
            .path("reservations")
            .path(createdReservationId)
            .build();
        return Response.created(newReservationURI).build();
    }

    @GET
    @Path("reservations/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public ReservationResponse getReservation(@PathParam("id") String reservationId) throws NotFoundException {
        return reservationService.getReservation(reservationId);
    }
}
