package co.com.sofka;

import co.com.sofka.model.AceptacionTyC;
import co.com.sofka.model.TerminosYCondiciones;
import co.com.sofka.services.AceptacionTyCService;
import co.com.sofka.services.TyCService;
import io.smallrye.mutiny.Uni;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.Response.Status.NOT_ACCEPTABLE;

@Path("/api/TyC")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class controller {

    @Inject
    TyCService tYCService;

    @Inject
    AceptacionTyCService aceptacionTyCService;

    @POST
    @Path("/cargarTyC")
    @Consumes(APPLICATION_JSON)
    public Uni<Response> createTermsConditions(TerminosYCondiciones terminosYCondiciones) {
        return tYCService.agregarTyC(terminosYCondiciones)
                .map(termsConditions ->Response.ok(termsConditions).build());
    }

    @GET
    @Path("/consultar")
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<Response> obtenerTerminosyCondiciones(){
        return tYCService.obtenerElUltimo()
                .map(termsConditions -> Response.ok(termsConditions).build());
    }

    @POST
    @Path("/agregarAceptacion")
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<Response> cargarAceptacion(AceptacionTyC aceptacionTyC) {
        if(aceptacionTyC.getTipoDocumento().equalsIgnoreCase("Cedula")||
                aceptacionTyC.getTipoDocumento().equalsIgnoreCase("Pasaporte")){

            return aceptacionTyCService.agregarAceptacion(aceptacionTyC)
                    .map(acepTermsC -> Response.ok(acepTermsC).build())
                    .onFailure().
                    recoverWithItem(() -> Response.status(NOT_ACCEPTABLE).build());
        }
        return Uni.createFrom().item(Response.status(NOT_ACCEPTABLE).build());
    }

}