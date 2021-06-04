package com.gmproxy.Entities;

public class HourMedicine {

    String hora;
    String nombre;

    public HourMedicine(String hora, String nombre){
        this.hora=hora;
        this.nombre=nombre;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return "HourMedicine{" +
                "hora='" + hora + '\'' +
                ", nombre='" + nombre + '\'' +
                '}';
    }
}
