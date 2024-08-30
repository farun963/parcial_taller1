package api.entity;

import java.util.List;

public class Presupuesto {
    private Integer id;
    private String fechaInicio;
    private String fechaFin;
    private Double montoPresupuestado;
    private List<Gasto> gastos;

    // Getters y setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public String getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(String fechaFin) {
        this.fechaFin = fechaFin;
    }

    public Double getMontoPresupuestado() {
        return montoPresupuestado;
    }

    public void setMontoPresupuestado(Double montoPresupuestado) {
        this.montoPresupuestado = montoPresupuestado;
    }

    public List<Gasto> getGastos() {
        return gastos;
    }

    public void setGastos(List<Gasto> gastos) {
        this.gastos = gastos;
    }
}