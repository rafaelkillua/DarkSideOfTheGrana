package models;

import java.util.Calendar;

/**
 * Class Provento Cria um objeto do Tipo Provento, onde este herda os metodos da
 * classe Transacao.
 *
 * @author Grupo
 * @version 1.0
 *
 */
public class Provento extends Transacao {

    private static final long serialVersionUID = 8349578392162644503L;
	private double valorDoProvento;

    /**
     * Construtor da classe Provento
     *
     * @param descricao Descricao da transacao.
     * @param dataDeInsercao Data de insercao da transacao.
     * @param categoria Categoria da transacao.
     * @param valorDoProvento Valor do provento.
     * @param recorrencia Recorrencia da transacao.
     * @throws Exception 
     */
    public Provento(String descricao, Calendar dataDeInsercao,
            Categoria categoria, double valorDoProvento, int recorrencia) throws Exception {
    	
        super(descricao, dataDeInsercao, categoria, recorrencia);
        valorValido(valorDoProvento);
    }

    @Override
    public String toString() {
        return "Provento - " + super.toString();
    }

    @Override
    public String getTipoDaTransacao() {
        return "Provento";
    }

	@Override
	public double getValor() {
		return valorDoProvento;
	}

	@Override
	public void setValor(double valorDoProvento) throws Exception {
		valorValido(valorDoProvento);
	}

	private void valorValido(double valorDoProvento) throws Exception {
		if(valorDoProvento <= 0){
			throw new Exception("O valor do provento deve ser positivo e diferente de zero.");
		}
		this.valorDoProvento = valorDoProvento;
	}
}
