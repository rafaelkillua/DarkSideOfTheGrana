package models;

import java.io.Serializable;
import java.util.Calendar;

/**
 * Class Despesa Cria um objeto do Tipo Despesa, onde este herda os metodos da
 * classe Transacao.
 *
 * @author Grupo
 * @version 1.0
 *
 */
public class Despesa extends Transacao implements Serializable {

    private static final long serialVersionUID = 119827389127381L;
	private double valorDaDespesa;

    /**
     * Construtor da classe Despesa
     *
     * @param descricao Descricao da transacao.
     * @param dataDeInsercao Data de insercao da transacao.
     * @param categoria Categoria da transacao.
     * @param valorDaDespesa Valor da despesa.
     * @param recorrencia Recorrencia da transacao.
     * @throws Exception Valor da despesa igual a zero.
     */
    public Despesa(String descricao, Calendar dataDeInsercao,
            Categoria categoria, double valorDaDespesa, int recorrencia) throws Exception {
    	
        super(descricao, dataDeInsercao, categoria, recorrencia);
        valorValido(valorDaDespesa);
    }

    @Override
    public String toString() {
        return "Despesa - " + super.toString();
    }

    @Override
    public String getTipoDaTransacao() {
        return "Despesa";
    }

	@Override
	public double getValor() {
		return (-1)*(Math.abs(valorDaDespesa));
	}

	@Override
	public void setValor(double valorDaDespesa)throws Exception {
		valorValido(valorDaDespesa);
	}
	
	private void valorValido(double valor) throws Exception{
		if(valor == 0){
			throw new Exception("O valor da despesa deve ser diferente de zero.");
		}
		this.valorDaDespesa = valor;
	}

}
