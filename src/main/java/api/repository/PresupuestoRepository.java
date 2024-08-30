package api.repository;

import api.entity.Presupuesto;
import api.entity.Gasto;
import java.util.List;

public interface PresupuestoRepository {
    void cargarDatos();
    void guardarDatos();
    Presupuesto obtenerById(Integer id);
    List<Presupuesto> listar();
    void agregarGasto(Integer presupuestoId, Gasto gasto);
    void agregarPresupuesto(Presupuesto presupuesto);  // Asegúrate de que este método esté definido aquí
}