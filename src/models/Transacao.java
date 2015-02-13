package models;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Class Transacao Cria um objeto do tipo transacao.
 *
 * @author Grupo
 * @version 1.0
 *
 */
public abstract class Transacao implements Serializable, Comparable<Transacao> {

    private static final long serialVersionUID = 5656761834817850263L;
    private String descricao;
    private Calendar dataDeInsercao;
    private Categoria categoria;
    private int recorrencia;

    /**
     * Construtor da classe Transacao.
     *
     * @param descricao Descricao da transacao.
     * @param dataDeInsercao Data de insercao da transacao.
     * @param categoria Categoria da transacao.
     * @param recorrencia Recorrencia da transacao.
     */
    public Transacao(String descricao, Calendar dataDeInsercao,
            Categoria categoria, int recorrencia) {
        this.descricao = descricao;
        this.dataDeInsercao = dataDeInsercao;
        this.categoria = categoria;
        this.recorrencia = recorrencia;
    }

    /**
     * @return Descricao da transacao.
     */
    public String getDescricao() {
        return descricao;
    }

    /**
     * @return A data de insercao da transacao.
     */
    public Calendar getDataDeInsercao() {
        return dataDeInsercao;
    }

    /**
     * @return A categoria da transacao.
     */
    public Categoria getCategoria() {
        return categoria;
    }

    /**
     * @return O valor da transacao.
     */
    public abstract double getValor();

    /**
     * @return A recorrencia da transacao.
     */
    public int getRecorrencia() {
        return recorrencia;
    }

    /**
     * @return O tipo da transacao.
     */
    public abstract String getTipoDaTransacao();

    /**
     * @param descricao A nova descricao.
     */
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    /**
     * @param dataDeInsercao A nova data de insercao da transacao.
     */
    public void setDataDeInsercao(Calendar dataDeInsercao) {
        this.dataDeInsercao = dataDeInsercao;
    }

    /**
     * @param categoria A nova categoria da transacao.
     */
    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    /**
     * @param valor O novo valor da transacao.
     * @throws Exception Se os valores forem negativos.
     */
    public abstract void setValor(double valor) throws Exception;

    /**
     * @param recorrencia A nova recorrencia da transacao.
     */
    public void setRecorrencia(int recorrencia) {
        this.recorrencia = recorrencia;
    }

    @Override
    public String toString() {
        SimpleDateFormat formatador = new SimpleDateFormat("dd/MM/yyyy");
        return "Descricao: " + getDescricao() + ", Data: "
                + formatador.format(getDataDeInsercao().getTime()) + ", Valor: " + getValor()
                + ", Categoria: " + getCategoria().getNome()
                + ", Recorrencia: " + getRecorrencia();
    }

    @Override
    public int compareTo(Transacao outraTransacao) {
        return this.getDataDeInsercao().compareTo(outraTransacao.getDataDeInsercao());
    }

}
