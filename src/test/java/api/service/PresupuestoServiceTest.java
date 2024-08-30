package api.service;

import static org.hamcrest.Matchers.closeTo;
import api.entity.Presupuesto;
import api.entity.Gasto;
import api.repository.PresupuestoRepository;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import io.restassured.parsing.Parser;
import static io.restassured.RestAssured.defaultParser;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import io.quarkus.test.junit.mockito.InjectMock;

@QuarkusTest
public class PresupuestoServiceTest {

    @Inject
    PresupuestoService presupuestoService;

    @InjectMock
    PresupuestoRepository presupuestoRepository;

    @BeforeEach
    public void setup() {
        Presupuesto presupuesto = new Presupuesto();
        presupuesto.setId(1);
        // otros setters para configurar el presupuesto
    
        // Usar el método adecuado para agregar el presupuesto
        presupuestoRepository.agregarPresupuesto(presupuesto);
    
        // Si aún necesitas guardar el estado, llama a guardarDatos sin argumentos
        presupuestoRepository.guardarDatos(); // Llamada sin argumentos
    }
    

    @Test
    @Transactional
    public void testCrearPresupuesto() {
        Presupuesto presupuesto = new Presupuesto();
        presupuesto.setId(1);
        presupuesto.setFechaInicio("2024-06-06");
        presupuesto.setFechaFin("2024-06-06");
        presupuesto.setMontoPresupuestado(100000.0);

        // Configurar el mock para agregarPresupuesto
        doNothing().when(presupuestoRepository).agregarPresupuesto(any(Presupuesto.class));

        // Ejecutar el test
        given()
            .contentType("application/json")
            .body(presupuesto)
            .when().post("/presupuestos")
            .then()
            .statusCode(200)
            .body("id", notNullValue())
            .body("montoPresupuestado", is(100000.0F)); // Cambiar a float si es necesario
    }

    @Test
    public void testListarPresupuestos() {
        Presupuesto presupuesto = new Presupuesto();
        presupuesto.setId(1);
        presupuesto.setMontoPresupuestado(1000.0);
        presupuesto.setGastos(new ArrayList<>());

        // Configurar el mock para listar
        when(presupuestoRepository.listar()).thenReturn(List.of(presupuesto));

        // Ejecutar el test
        given()
            .when().get("/presupuestos")
            .then()
            .statusCode(200)
            .body("$.size()", is(1));
    }

    @Test
    public void testObtenerPresupuestoPorId() {
        Presupuesto presupuesto = new Presupuesto();
        presupuesto.setId(1);
        presupuesto.setMontoPresupuestado(1000.0);
        presupuesto.setGastos(new ArrayList<>());

        // Configurar el mock para obtenerById
        when(presupuestoRepository.obtenerById(1)).thenReturn(presupuesto);

        // Ejecutar el test
        given()
            .when().get("/presupuestos/1")
            .then()
            .statusCode(200)
            .body("id", is(1))
            .body("montoPresupuestado", is(1000.0F)); // Cambiar a float si es necesario
    }

    @Test
    void testAgregarGasto() {
        Presupuesto presupuesto = new Presupuesto();
        presupuesto.setId(1);
        presupuesto.setMontoPresupuestado(1000.0);
        presupuesto.setGastos(new ArrayList<>());
    
        Gasto gasto = new Gasto();
        gasto.setMonto(100.0); // Asegúrate de asignar un valor al monto
    
        // Configurar el mock para obtenerById y agregarGasto
        when(presupuestoRepository.obtenerById(1)).thenReturn(presupuesto);
        doNothing().when(presupuestoRepository).agregarGasto(eq(1), any(Gasto.class));
    
        // Ejecutar el método a probar
        presupuestoService.agregarGasto(1, gasto);
    
        // Verificar que agregarGasto fue llamado
        verify(presupuestoRepository).agregarGasto(eq(1), any(Gasto.class));
    }
    

    @Test
    public void testObtenerTotalGastos() {
        Presupuesto presupuesto = new Presupuesto();
        presupuesto.setId(1);
        presupuesto.setMontoPresupuestado(1000.0);
    
        Gasto gasto1 = new Gasto();
        gasto1.setMonto(200.0);
    
        Gasto gasto2 = new Gasto();
        gasto2.setMonto(300.0);
    
        presupuesto.setGastos(List.of(gasto1, gasto2));
    
        // Configurar el mock para obtenerById
        when(presupuestoRepository.obtenerById(1)).thenReturn(presupuesto);
    
        // Ejecutar el test
        given()
        .when().get("/presupuestos/1/totalGastos")
        .then()
        .statusCode(200)
        .body(is(closeTo(500.0, 0.001)));  // Comparación con tolerancia
    }
}
