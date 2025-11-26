package com.despesas.model;

import javafx.beans.property.*;

public class Despesa {
    private final IntegerProperty id = new SimpleIntegerProperty();
    private final StringProperty descricao = new SimpleStringProperty();
    private final StringProperty categoria = new SimpleStringProperty();
    private final DoubleProperty valor = new SimpleDoubleProperty();
    private final StringProperty data = new SimpleStringProperty();

    public Despesa(){}

    public Despesa(String descricao, String categoria, double valor, String data){
        this.descricao.set(descricao);
        this.categoria.set(categoria);
        this.valor.set(valor);
        this.data.set(data);
    }

    public Despesa(int id, String descricao, String categoria, double valor, String data) {
        this.id.set(id);
        this.descricao.set(descricao);
        this.categoria.set(categoria);
        this.valor.set(valor);
        this.data.set(data);
    }

    public int getId(){ return id.get(); }
    public void setId(int i){ id.set(i); }
    public IntegerProperty idProperty(){ return id; }

    public String getDescricao(){ return descricao.get(); }
    public void setDescricao(String d){ descricao.set(d); }
    public StringProperty descricaoProperty(){ return descricao; }

    public String getCategoria(){ return categoria.get(); }
    public void setCategoria(String c){ categoria.set(c); }
    public StringProperty categoriaProperty(){ return categoria; }

    public double getValor(){ return valor.get(); }
    public void setValor(double v){ valor.set(v); }
    public DoubleProperty valorProperty(){ return valor; }

    public String getData(){ return data.get(); }
    public void setData(String d){ data.set(d); }
    public StringProperty dataProperty(){ return data; }
}
