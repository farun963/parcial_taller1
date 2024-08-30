package api.repository;

import jakarta.enterprise.context.ApplicationScoped;
import java.util.ArrayList;
import java.util.List;
import api.entity.Presupuesto;
import api.entity.Gasto;

@ApplicationScoped
public class PresupuestoRepositoryImpl implements PresupuestoRepository {

    private List<Presupuesto> presupuestos = new ArrayList<>();

    @Override
    public void cargarDatos() {
        // Cargar datos iniciales
    }

    @Override
    public void guardarDatos() {
        // Guardar datos
    }

    @Override
    public Presupuesto obtenerById(Integer id) {
        return presupuestos.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Presupuesto> listar() {
        return presupuestos;
    }

    @Override
    public void agregarGasto(Integer presupuestoId, Gasto gasto) {
        Presupuesto presupuesto = obtenerById(presupuestoId);
        if (presupuesto != null) {
            double sumaGastosActuales = presupuesto.getGastos().stream()
                    .mapToDouble(Gasto::getMonto)
                    .sum();
            if (sumaGastosActuales + gasto.getMonto() <= presupuesto.getMontoPresupuestado()) {
                presupuesto.getGastos().add(gasto);
                guardarDatos();
            } else {
                throw new IllegalArgumentException("El gasto excede el monto presupuestado.");
            }
        } else {
            throw new IllegalArgumentException("Presupuesto no encontrado.");
        }
    }
    
    

    @Override
    public void agregarPresupuesto(Presupuesto presupuesto) {
        presupuestos.add(presupuesto);
        guardarDatos();
    }
}