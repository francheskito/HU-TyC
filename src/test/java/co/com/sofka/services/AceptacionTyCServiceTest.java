package co.com.sofka.services;

import co.com.sofka.model.AceptacionTyC;
import co.com.sofka.repository.AceptacionRepository;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import io.smallrye.mutiny.Uni;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.Instant;

@QuarkusTest
class AceptacionTyCServiceTest {

    @InjectMock
    AceptacionTyCService service;

    @InjectMock
    AceptacionRepository repository;

    private AceptacionTyC aceptacionTyC;
    private AceptacionTyC aceptacionTyC2;

    private final Instant date= Instant.now();

    private final ObjectId id = new ObjectId("62a0c1034a8d71536568fdf4");

    private final String v = "8";

    private final String tipodoc = "Cedula";
    private final String tipodoc2 = "Pasaporte";
    private final String regexCedula="[0-9]{2}-PN-[0-9]{3}-[0-9]{4}";
    private String regexPasaporte="[a-zA-Z0-9-]{5,16}";
    private final String documento = "02-PN-010-1234";
    private final String documento2 = "dadasda111-";

    @BeforeEach
    void setUp() {
    aceptacionTyC= new AceptacionTyC(id,tipodoc,documento,date,v);
    aceptacionTyC2= new AceptacionTyC(id,tipodoc2,documento2,date,v);

    }

    @Test
    void agregarAceptacion() {

        Mockito.when(repository.persist(aceptacionTyC)).thenReturn(Uni.createFrom().item(aceptacionTyC));

        service.agregarAceptacion(aceptacionTyC).subscribe().with(dx -> {
            Assertions.assertNotNull(dx);
        });

    }

    @Test
    void agregarAceptacionConCedula() {
        Mockito.when(repository.persist(aceptacionTyC)).thenReturn(Uni.createFrom().item(aceptacionTyC));

        service.agregarAceptacionConPasaporte(aceptacionTyC).subscribe().with(dx -> {
            Assertions.assertNotNull(dx);
        });
    }

    @Test
    void agregarAceptacionConPasaporte() {
        Mockito.when(repository.persist(aceptacionTyC2)).thenReturn(Uni.createFrom().item(aceptacionTyC2));

        service.agregarAceptacionConPasaporte(aceptacionTyC2).subscribe().with(dx -> {
            Assertions.assertNotNull(dx);
        });
    }
}