package com.example.springboot.email;

public class EmailDone {

    private String traslado;
    private String origen;
    private String destino;
    private String cantidad;
    private String sku;
    private String descripcion;
    private String status;
    private String fecha;
    private String consecutivo;

    public EmailDone() {
    }

    public EmailDone(String traslado, String origen, String destino, String cantidad, String sku, String descripcion, String status, String fecha) {
        this.traslado = traslado;
        this.origen = origen;
        this.destino = destino;
        this.cantidad = cantidad;
        this.sku = sku;
        this.descripcion = descripcion;
        this.status = status;
        this.fecha = fecha;
    }

    public String getConsecutivo() {
        return consecutivo;
    }

    public void setConsecutivo(String consecutivo) {
        this.consecutivo = consecutivo;
    }

    public String getTraslado() {
        return traslado;
    }

    public void setTraslado(String traslado) {
        this.traslado = traslado;
    }

    public String getOrigen() {
        return origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
}