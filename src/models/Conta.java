package models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * Class Conta Cria um objeto do tipo Conta.
 *
 * @author Grupo
 * @version 1.0
 *
 */
public class Conta implements Serializable {

    private static final long serialVersionUID = 112389178293L;
    private String nomeDaConta;
    private final List<Transacao> listaDeTransacoes;

    /**
     * Construtor da classe Conta.
     *
     * @param nomeDaConta Nome da conta.
     * @throws Exception Nome invalido.
     */
    public Conta(String nomeDaConta) throws Exception {
        isNomeValido(nomeDaConta);
        listaDeTransacoes = new ArrayList<>();
    }

    /**
     * @return Retorna o nome do usuario.
     */
    public String getNome() {
        return nomeDaConta;
    }

    /**
     * Define o nome do usuario.
     *
     * @param nomeDaConta Nome do usuario.
     * @throws Exception Se o nome do usuario for invalido.
     */
    public void setNomeDaConta(String nomeDaConta) throws Exception {
        isNomeValido(nomeDaConta);
    }

    private void isNomeValido(String nome) throws Exception {
        if (nome == null || nome.trim().equals("")) {
            throw new Exception("Nome da conta invalido.");
        }
        this.nomeDaConta = nome;

    }

    /**
     * @return Retorna a lista de transacoes da conta.
     */
    public List<Transacao> getListaDeTransacoes() {
        return listaDeTransacoes;
    }

    /**
     * @param mes Mes do extrato.
     * @param ano Ano do extrato.
     * @return Retorna uma lista de transacoes que representa o extrato mensal
     * da conta.
     * @throws Exception Lanca excecao se o mes for invalido ou a lista de
     * transacoes estiver vazia.
     */
    public List<Transacao> getExtrato(int mes, int ano) throws Exception {

        if (mes < 0 || mes > 11) {
            throw new Exception("Mes invalido.");
        }

        if (listaDeTransacoes.isEmpty()) {
            throw new Exception("Nao foi feita nenhuma transacao.");
        }

        List<Transacao> extratoDoMes = new ArrayList<>();
        for (Iterator<Transacao> it = listaDeTransacoes.iterator(); it.hasNext();) {
            Transacao transacao = it.next();
            if (transacao.getDataDeInsercao().get(Calendar.MONTH) == mes
                    && transacao.getDataDeInsercao().get(Calendar.YEAR) == ano) {
                extratoDoMes.add(transacao);
            }
        }
        return extratoDoMes;
    }

    /**
     * Metodo que adiciona uma nova transacao a conta.
     *
     * @param descricao Descricao da transacao.
     * @param dataDeInsercao Data que foi efetuada a transacao.
     * @param categoria Categoria da transacao.
     * @param valorDaTransacao Valor da transacao.
     * @param recorrencia Recorrencia da transacao.
     * @param tipo Tipo da transacao.
     * @throws Exception Caso haja algum parametro invalido.
     *
     */
    public void adicionaTransacao(String descricao, Calendar dataDeInsercao,
            Categoria categoria, double valorDaTransacao, int recorrencia, String tipo)
            throws Exception {
        Transacao transacao;
        if (tipo.equals("Despesa")) {
            transacao = new Despesa(descricao, dataDeInsercao, categoria,
                    valorDaTransacao, recorrencia);
            categoria.setSaldo(valorDaTransacao);
        } else {
            transacao = new Provento(descricao, dataDeInsercao, categoria,
                    valorDaTransacao, recorrencia);
            categoria.setSaldo(valorDaTransacao);
        }
        listaDeTransacoes.add(transacao);
        Collections.sort(listaDeTransacoes);
    }

    /**
     * Remove uma transacao da lista de transacoes.
     *
     * @param posicaoDaTransacao Posicao da transacao na lista.
     * @throws Exception Lanca excecao se a lista se a conta nao tiver nenhuma
     * transacao.
     */
    public void removeTransacao(int posicaoDaTransacao) throws Exception {
        if (listaDeTransacoes.isEmpty()) {
            throw new Exception("Nao existem nenhuma transacao na conta.");
        }
        listaDeTransacoes.remove(posicaoDaTransacao);
    }

    /**
     * Edita uma transacao da conta.
     *
     * @param posicaoDaTransacao Posicao da transacao.
     * @param novaDescricao Nova descricao da transacao.
     * @param novaData Nova data da transacao.
     * @param novaCategoria Nova categoria da transacao.
     * @param novoValor Novo valor da transacao.
     * @param novaRecorrencia Nova recorrencia da transacao.
     * @param novoTipo Novo tipo da transacao.
     * @throws Exception Caso haja algum parametro invalido.
     */
    public void editaTransacao(int posicaoDaTransacao, String novaDescricao,
            Calendar novaData, Categoria novaCategoria, double novoValor,
            int novaRecorrencia, String novoTipo) throws Exception {
        Transacao transacao = pesquisaTransacao(posicaoDaTransacao);

        if (novoTipo.equals(transacao.getTipoDaTransacao())) {
            transacao.setDescricao(novaDescricao);
            transacao.setDataDeInsercao(novaData);
            transacao.setValor(novoValor);
            transacao.setCategoria(novaCategoria);
            transacao.setRecorrencia(novaRecorrencia);
        } else {
            removeTransacao(posicaoDaTransacao);
            adicionaTransacao(novaDescricao, novaData, novaCategoria, novoValor, novaRecorrencia, novoTipo);
        }
    }

    /**
     * @param posicaoDaTransacao Posicao da transacao na lista de transacaoes da
     * conta.
     * @return Retorna uma transacao dada a sua posicao.
     * @throws Exception Se a conta nao tiver nenhuma transacao.
     */
    public Transacao pesquisaTransacao(int posicaoDaTransacao) throws Exception {
        if (listaDeTransacoes.isEmpty()) {
            throw new Exception(
                    "Nao existe nenhuma transacao adicionada nesta conta.");
        }
        return listaDeTransacoes.get(posicaoDaTransacao);
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Conta)) {
            return false;
        }
        Conta outraConta = (Conta) obj;
        return getNome().equals(outraConta.getNome());

    }

    @Override
    public String toString() {
        return "Nome da conta: " + getNome();
    }

}
