package models;

import java.awt.Color;

import java.io.Serializable;

/**
 * Class Categoria Cria um objeto Categoria.
 *
 * @author Grupo
 * @version 1.0
 *
 */
public class Categoria implements Serializable {

    private static final long serialVersionUID = -8971302208136741254L;
    private String nomeDaCategoria;
    private Color corDaCategoria;
    private double orcamentoDaCategoria;
    private double saldo;

    /**
     * Cria uma categoria de transacao.
     *
     * @param nomeDaCategoria Nome da categoria.
     * @param corDaCategoria Cor da categoria.
     * @param orcamentoDaCategoria Orcamento da categoria.
     * @throws Exception Se algum parametro for invalido.
     */
    public Categoria(String nomeDaCategoria, Color corDaCategoria,
            double orcamentoDaCategoria) throws Exception {
        isNomeValido(nomeDaCategoria);
        isCorValida(corDaCategoria);
        isOrcamentoValido(orcamentoDaCategoria);
        this.saldo = orcamentoDaCategoria;
    }

    /**
     * @return Retorna o nome da categoria.
     */
    public String getNome() {
        return nomeDaCategoria;
    }

    /**
     * @return Retorna a cor da categoria.
     */
    public Color getCor() {
        return corDaCategoria;
    }

    /**
     * @return Retorna o orcamento da categoria.
     */
    public double getOrcamento() {
        return orcamentoDaCategoria;
    }

    /**
     *
     * @return Retorna o saldo da categoria.
     */
    public double getSaldo() {
        return saldo;
    }

    /**
     * Atualiza o saldo da categoria.
     *
     * @param quantidade A quantidade a ser adicionada ou subtraida do saldo.
     */
    public void setSaldo(double quantidade) {
        saldo += quantidade;
    }

    /**
     * Reseta o saldo da categoria.
     */
    public void resetaSaldo() {
        saldo = orcamentoDaCategoria;
    }

    /**
     * Define um novo nome para a categoria.
     *
     * @param novoNomeDaCategoria O novo nome da categoria.
     * @throws Exception Se o nome for invalido.
     */
    public void setNome(String novoNomeDaCategoria) throws Exception {
        isNomeValido(novoNomeDaCategoria);
    }

    /**
     * Define uma nova cor para a categoria.
     *
     * @param novaCorDaCategoria A nova cor da categoria.
     * @throws Exception Se a cor for invalida.
     */
    public void setCor(Color novaCorDaCategoria) throws Exception {
        isCorValida(novaCorDaCategoria);
    }

    /**
     * Define um novo orcamento para a categoria.
     *
     * @param novoOrcamentoDaCategoria Valor do orcamento.
     * @throws Exception
     */
    public void setOrcamento(double novoOrcamentoDaCategoria) throws Exception {
        isOrcamentoValido(novoOrcamentoDaCategoria);
        resetaSaldo();
    }

    private void isNomeValido(String novoNomeDaCategoria) throws Exception {
        if (novoNomeDaCategoria == null || novoNomeDaCategoria.trim().isEmpty()) {
            throw new Exception("Nome invalido.");
        }
        this.nomeDaCategoria = novoNomeDaCategoria;
    }

    private void isCorValida(Color novaCorDaCategoria) throws Exception {
        if (novaCorDaCategoria == null) {
            throw new Exception("Cor invalida.");
        }
        this.corDaCategoria = novaCorDaCategoria;
    }

    private void isOrcamentoValido(double novoOrcamentoDaCategoria) throws Exception {
        if (novoOrcamentoDaCategoria <= 0) {
            throw new Exception(
                    "Orcamento invalido, digite valores maiores que 0 (zero).");
        }
        this.orcamentoDaCategoria = novoOrcamentoDaCategoria;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof Categoria)) {
            return false;
        }
        Categoria outraCategoria = (Categoria) obj;
        if (!corDaCategoria.equals(outraCategoria.getCor())) {
            return false;
        }
        if (!nomeDaCategoria.equals(outraCategoria.getNome())) {
            return false;
        }

        if (orcamentoDaCategoria != outraCategoria.getOrcamento()) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Nome da categoria: " + getNome() + ", orcamento: " + getOrcamento();
    }

}
