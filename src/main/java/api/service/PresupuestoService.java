package api.service;


import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import api.entity.Presupuesto;
import api.entity.Gasto;
import api.repository.PresupuestoRepository;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import java.util.List;

@Path("/presupuestos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PresupuestoService {

    @Inject
    PresupuestoRepository presupuestoRepository;

    @POST
    @Consumes("application/json")
    @Produces("application/json") // Asegurar que produce JSON
    public Response crearPresupuesto(Presupuesto presupuesto) {
        presupuestoRepository.agregarPresupuesto(presupuesto);
        // presupuestoRepository.guardarDatos(); // Si es necesario
        return Response.ok(presupuesto).type(MediaType.APPLICATION_JSON).build();
    }
    

    @GET
    @Produces("application/json")
    public Response listarPresupuestos() {
        List<Presupuesto> presupuestos = presupuestoRepository.listar();
        return Response.ok(presupuestos).build();
    }

    @GET
    @Path("/{presupuestoId}")
    @Produces("application/json")
    public Response obtenerPresupuesto(@PathParam("presupuestoId") Integer presupuestoId) {
        Presupuesto presupuesto = presupuestoRepository.obtenerById(presupuestoId);
        if (presupuesto != null) {
            return Response.ok(presupuesto).type(MediaType.APPLICATION_JSON).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @GET
    @Path("/{id}/totalGastos")
    @Produces("application/json")
    public Response obtenerTotalGastos(@PathParam("id") Integer id) {
        Presupuesto presupuesto = presupuestoRepository.obtenerById(id);
        if (presupuesto != null) {
            double totalGastos = presupuesto.getGastos().stream()
                .mapToDouble(Gasto::getMonto)
                .sum();
            return Response.ok(totalGastos).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
    


    @POST
    @Path("/agregar-gasto/{presupuestoId}")
    @Consumes("application/json")
    public Response agregarGasto(@PathParam("presupuestoId") Integer presupuestoId, Gasto gasto) {
        Presupuesto presupuesto = presupuestoRepository.obtenerById(presupuestoId);
        if (presupuesto != null) {
            double sumaGastos = presupuesto.getGastos().stream()
                .mapToDouble(Gasto::getMonto)
                .sum();
            if (sumaGastos + gasto.getMonto() > presupuesto.getMontoPresupuestado()) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("El gasto supera el monto presupuestado")
                        .build();
            }
            presupuestoRepository.agregarGasto(presupuestoId, gasto);
            return Response.ok().build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
}
