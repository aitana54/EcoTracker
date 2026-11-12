package com.asix.ecotracker;
import javafx.beans.property.*;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;

public class Sostenibilitat {
    private final IntegerProperty id = new SimpleIntegerProperty();
    private final StringProperty nombre = new SimpleStringProperty();
    private final StringProperty fecha = new SimpleStringProperty();
    private final StringProperty categoria = new SimpleStringProperty();
    private final DoubleProperty km = new SimpleDoubleProperty();
    private final DoubleProperty kg = new SimpleDoubleProperty();
    private final DoubleProperty llumsHoraEstimada = new SimpleDoubleProperty();
    private final IntegerProperty teletreball = new SimpleIntegerProperty();
    private final StringProperty descripcio = new SimpleStringProperty();
    private final DoubleProperty co2Total = new SimpleDoubleProperty();




    public Sostenibilitat(int id, String nombre, Timestamp fecha, String nombreCategoria, double km, double kg, double llumsHoraEstimada, int teletreball, String descripcio, double co2Total) {
        this.id.set(id);
        this.nombre.set(nombre);
        this.fecha.set(String.valueOf(fecha));
        this.categoria.set(nombreCategoria);
        this.km.set(km);
        this.kg.set(kg);
        this.llumsHoraEstimada.set(llumsHoraEstimada);
        this.teletreball.set(teletreball);
        this.descripcio.set(descripcio);
        this.co2Total.set(co2Total);
    }


    public Sostenibilitat(String nombre, Timestamp fecha, String nombreCategoria, double km, double kg, double llumsHoraEstimada, int teletreball, String descripcio, double co2Total) {
        this.nombre.set(nombre);
        this.fecha.set(String.valueOf(fecha));
        this.categoria.set(nombreCategoria);
        this.km.set(km);
        this.kg.set(kg);
        this.llumsHoraEstimada.set(llumsHoraEstimada);
        this.teletreball.set(teletreball);
        this.descripcio.set(descripcio);
        this.co2Total.set(co2Total);
    }

    public int getId() {
        return id.get();
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public String getNombre() {
        return nombre.get();
    }
    public StringProperty nombreProperty() {
        return nombre;
    }

    public String getFecha() {
        return fecha.get();
    }

    public StringProperty fechaProperty() {
        return fecha;
    }

    public String getCategoria() {
        return categoria.get();
    }

    public StringProperty categoriaProperty() {
        return categoria;
    }

    public double getKm() {
        return km.get();
    }

    public DoubleProperty kmProperty() {
        return km;
    }

    public double getKg() {
        return kg.get();
    }

    public DoubleProperty kgProperty() {
        return kg;
    }

    public double getLlumsHoraEstimada() {
        return llumsHoraEstimada.get();
    }

    public DoubleProperty llumsHoraEstimadaProperty() {
        return llumsHoraEstimada;
    }

    public int getTeletreball() {
        return teletreball.get();
    }


    public IntegerProperty teletreballProperty() {
        return teletreball;
    }

    public String getDescripcio() {
        return descripcio.get();
    }
    public StringProperty descripcioProperty() {
        return descripcio;
    }

    public double getCo2Total() {
        return co2Total.get();
    }

    public DoubleProperty co2TotalProperty() {
        return co2Total;
    }
}
