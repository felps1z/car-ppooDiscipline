import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

abstract class ComponenteCarro {
    protected String nome;
    protected double peso;

    public ComponenteCarro(String nome, double peso) {
        this.nome = nome;
        this.peso = peso;
    }

    public abstract double calcularPeso(double totalParcial);
}

class ParteFolha extends ComponenteCarro {
    public ParteFolha(String nome, double peso) {
        super(nome, peso);
    }

    @Override
    public double calcularPeso(double totalParcial) {
        double novoTotal = totalParcial + peso;

        System.out.printf(
            Locale.US,
            "Somando agora o peso de %s: %.1f. Total parcial: %.1f%n",
            nome, peso, novoTotal
        );

        return novoTotal;
    }
}

class ParteComposta extends ComponenteCarro {
    private List<ComponenteCarro> filhos = new ArrayList<>();

    public ParteComposta(String nome) {
        super(nome, 0.0);
    }

    public void adicionar(ComponenteCarro parte) {
        filhos.add(parte);
    }

    @Override
    public double calcularPeso(double totalParcial) {
        double total = totalParcial;

        for (ComponenteCarro filho : filhos) {
            total = filho.calcularPeso(total);
        }

        return total;
    }
}

public class Main {
    public static void main(String[] args) {
        ParteComposta carro = new ParteComposta("Carro");

        ParteComposta carroceria = new ParteComposta("Carroceria");
        carroceria.adicionar(new ParteFolha("Para-lamas", 50.0));
        carroceria.adicionar(new ParteFolha("Portas", 80.0));
        carroceria.adicionar(new ParteFolha("Painéis", 40.0));
        carroceria.adicionar(new ParteFolha("Porta-malas", 30.0));
        carroceria.adicionar(new ParteFolha("Capô", 35.0));

        ParteComposta chassi = new ParteComposta("Chassi");

        ParteComposta tremDeForca = new ParteComposta("Trem de força");
        tremDeForca.adicionar(new ParteFolha("Motor", 200.0));
        tremDeForca.adicionar(new ParteFolha("Transmissão", 90.0));
        tremDeForca.adicionar(new ParteFolha("Diferencial", 60.0));
        tremDeForca.adicionar(new ParteFolha("Rodas", 100.0));

        chassi.adicionar(tremDeForca);
        chassi.adicionar(new ParteFolha("Suspensão", 120.0));

        carro.adicionar(carroceria);
        carro.adicionar(chassi);

        double total = carro.calcularPeso(0.0);

        System.out.printf(Locale.US, "Peso total do carro: %.1f%n", total);
    }
}