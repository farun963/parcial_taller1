package api.repository;

import api.entity.Presupuesto;
import api.entity.Gasto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class PresupuestoRepositoryTest {

    private PresupuestoRepository presupuestoRepository;

    @BeforeEach
    public void setUp() {
        presupuestoRepository = new PresupuestoRepositoryImpl();
    }

    @Test
    public void testAgregarGasto() {
        // Crear un presupuesto de ejemplo
        Presupuesto presupuesto = new Presupuesto();
        presupuesto.setId(1);  
        presupuesto.setMontoPresupuestado(1000.00);
        presupuesto.setGastos(new ArrayList<>()); // Inicializa la lista de gastos

        // Crear un gasto de ejemplo
        Gasto gasto = new Gasto();
        gasto.setId(1);
        gasto.setMonto(100.00);
        gasto.setDescripcion("Compra de materiales");

        // Agregar presupuesto al repositorio
        presupuestoRepository.agregarPresupuesto(presupuesto);

        // Simula agregar el gasto al presupuesto
        presupuestoRepository.agregarGasto(presupuesto.getId(), gasto);

        // Validar que el gasto fue agregado correctamente
        Presupuesto presupuestoActualizado = presupuestoRepository.obtenerById(presupuesto.getId());
        assertNotNull(presupuestoActualizado);
        assertEquals(1, presupuestoActualizado.getGastos().size());
        assertEquals(gasto.getMonto(), presupuestoActualizado.getGastos().get(0).getMonto());
    }
}
