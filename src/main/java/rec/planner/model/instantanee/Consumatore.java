package rec.planner.model.instantanee;

import java.io.Serializable;

public class Consumatore implements Serializable {

    private String smartMeter;
    private String homeController;
    private String nome;
    private double consumoMedio;
    private double consumoNominale;

    public Consumatore(String smartMeter, String homeController, String nome, double consumoMedio, double consumoNominale) {
        this.smartMeter = smartMeter;
        this.homeController = homeController;
        this.nome = nome;
        this.consumoMedio = consumoMedio;
        this.consumoNominale = consumoNominale;
    }

    public Consumatore() {
    }

    public String getSmartMeter() {
        return smartMeter;
    }

    public void setSmartMeter(String smartMeter) {
        this.smartMeter = smartMeter;
    }

    public double getConsumoMedio() {
        return consumoMedio;
    }

    public void setConsumoMedio(double consumoMedio) {
        this.consumoMedio = consumoMedio;
    }

    public double getConsumoNominale() {
        return consumoNominale;
    }

    public void setConsumoNominale(double consumoNominale) {
        this.consumoNominale = consumoNominale;
    }

    public String getHomeController() {
        return homeController;
    }

    public void setHomeController(String homeController) {
        this.homeController = homeController;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
