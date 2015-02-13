package core;

import dao.*;
import java.awt.Color;
import java.util.*;
import models.*;
import projetop2.utils.ProjetoHelperExceptions;
import view.Login;

/**
 * Classe base do programa, que junta a interface com as classes e que contem um
 * main.
 *
 * @author Grupo
 * @version 1.0
 */
public class Sistema {

    private GerenteDeUsuarios gerenteDeUsuarios;
    private Usuario usuarioLogado;
    private int mesAtualizacaoCategoria = 8;
    public static Sistema instance = new Sistema();

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Login().setVisible(true);
            }
        });
    }

    private Sistema() {
        try {
            gerenteDeUsuarios = new GerenteDeUsuarios();
        } catch (Exception e) {
        }
    }

    /**
     * Metodo que faz login no sistema.
     *
     * @param email E-mail do usuario a ser logado.
     * @param senha Senha do usuario a ser logado.
     * @throws Exception Caso nao exista tal usuario.
     */
    public void login(String email, String senha) throws Exception {
        this.usuarioLogado = gerenteDeUsuarios.loginDoUsuario(email, senha);
    }

    /**
     * Metodo para deslogar o usuario do sistema.
     */
    public void deslogar() {
        this.usuarioLogado = null;
    }

    /**
     * Metodo que chama o gerente de usuarios para adicionar um novo usuario.
     *
     * @param nome Nome do novo usuario.
     * @param senha Senha do novo usuario.
     * @param email E-mail do novo usuario.
     * @param dica Dica de senha do novo usuario.
     * @throws Exception Caso haja algum parametro invalido.
     */
    public void adicionaUsuario(String nome, String senha, String email,
            String dica) throws Exception {
        gerenteDeUsuarios.adicionaUsuario(email, nome, senha, dica);
    }

    /**
     * Metodo que chama o gerente de usuarios para editar um usuario existente.
     *
     * @param novoNome Novo nome do usuario.
     * @param novaSenha Nova senha do usuario.
     * @param novaDica Nova dica de senha do usuario.
     * @throws Exception Caso haja algum parametro invalido.
     */
    public void editaUsuario(String novoNome, String novaSenha, String novaDica)
            throws Exception {
        gerenteDeUsuarios.editaUsuarioSemEmail(getUsuarioLogado().getEmail(), novoNome, novaSenha, novaDica);
        atualizaUsuario();
    }

    /**
     * Metodo que chama o gerente de usuarios para editar o e-mail do usuario.
     *
     * @param novoEmailDoUsuario Novo e-mail do usuario.
     * @throws Exception Caso haja algum parametro invalido.
     */
    public void editaEmailDoUsuario(String novoEmailDoUsuario) throws Exception {
        gerenteDeUsuarios.editaEmailUsuario(getUsuarioLogado().getEmail(), novoEmailDoUsuario);
        atualizaUsuario();
    }

    /**
     *
     * @return Retorna o usuario atualmente logado.
     */
    public Usuario getUsuarioLogado() {
        return usuarioLogado;
    }

    private void atualizaUsuario() throws ProjetoHelperExceptions {
        gerenteDeUsuarios.atualizaUsuario(getUsuarioLogado());
    }

    /**
     * Metodo que adiciona uma transacao a uma conta do usuario.
     *
     * @param descricao Descricao da transacao.
     * @param dataDeInsercao Data de insercao da transacao.
     * @param categoria Categoria da transacao.
     * @param valor Valor da transacao.
     * @param recorrencia Recorrencia da transacao.
     * @param tipo Tipo da transacao.
     * @param nomeDaConta Nome da conta a ser adicionada a transacao.
     * @throws Exception Caso haja algum parametro invalido.
     */
    public void adicionaTransacao(String descricao, Calendar dataDeInsercao,
            Categoria categoria, double valor, int recorrencia, String tipo, String nomeDaConta)
            throws Exception {
        if (pesquisaConta(nomeDaConta) != null) {
            pesquisaConta(nomeDaConta).adicionaTransacao(descricao, dataDeInsercao, categoria,
                    valor, recorrencia, tipo);
            atualizaUsuario();
        }
    }

    /**
     * Metodo que edita uma transacao de uma conta pela sua posicao, ja que na
     * interface sera escolhida por sua posicao.
     *
     * @param posicaoDaTransacao Posicao da transacao.
     * @param nomeDaConta Nome da conta a ser adicionada a transacao.
     * @param novaDescricao Nova descricao da transacao.
     * @param novaData Nova data da transacao.
     * @param novaCategoria Nova categoria da transacao.
     * @param novoValor Novo valor da transacao.
     * @param novaRecorrencia Nova recorrencia da transacao.
     * @param novoTipo Novo tipo da transacao.
     * @throws Exception Caso haja algum parametro invalido.
     */
    public void editaTransacao(int posicaoDaTransacao, String nomeDaConta, String novaDescricao, Calendar novaData,
            Categoria novaCategoria, double novoValor, int novaRecorrencia, String novoTipo) throws Exception {
        pesquisaConta(nomeDaConta).editaTransacao(posicaoDaTransacao, novaDescricao,
                novaData, novaCategoria, novoValor, novaRecorrencia, novoTipo);
        atualizaUsuario();
    }

    /**
     * Metodo que remove uma transacao de uma conta por sua posicao, ja que na
     * interface sera escolhida por sua posicao.
     *
     * @param posicaoDaTransacao Posicao da transacao.
     * @param nomeDaConta Nome da conta da transacao.
     * @throws Exception Caso haja algum parametro invalido.
     */
    public void removeTransacao(int posicaoDaTransacao, String nomeDaConta)
            throws Exception {
        pesquisaConta(nomeDaConta).removeTransacao(posicaoDaTransacao);
        atualizaUsuario();
    }

    /**
     * Metodo que pesquisa uma transacao por sua posicao, ja que na interface
     * sera escolhida por sua posicao.
     *
     * @param posicao Posicao da transacao.
     * @param nomeDaConta Nome da conta da transacao.
     * @return A transacao.
     * @throws Exception Caso haja algum parametro invalido.
     */
    public Transacao pesquisaTransacao(int posicao, String nomeDaConta) throws Exception {
        return pesquisaConta(nomeDaConta).pesquisaTransacao(posicao);
    }

    /**
     * Metodo que retorna a lista de transacoes da conta.
     *
     * @param nomeDaConta A conta que contem as transacoes.
     * @return A lista de transacoes.
     * @throws Exception
     */
    public List<Transacao> getListaDeTransacoes(String nomeDaConta) throws Exception {
        return pesquisaConta(nomeDaConta).getListaDeTransacoes();
    }

    /**
     * Metodo que adiciona uma categoria a lista de categorias do usuario.
     *
     * @param nome Nome da categoria.
     * @param cor Cor da categoria.
     * @param orcamento Orcamento da categoria.
     * @throws Exception Caso haja algum parametro invalido.
     */
    public void adicionaCategoria(String nome, Color cor, double orcamento) throws Exception {
        getUsuarioLogado().adicionaCategoria(nome, cor, orcamento);
        atualizaUsuario();
    }

    /**
     * Metodo que edita uma categoria.
     *
     * @param nomeDaCategoriaAtual Nome atual da categoria.
     * @param nomeDaCategoriaNova Novo nome da categoria.
     * @param novaCorDaCategoria Nova cor da categoria.
     * @param orcamentoDaCategoria Novo orcamento da categoria.
     * @throws Exception Caso haja algum parametro invalido.
     */
    public void editaCategoria(String nomeDaCategoriaAtual, String nomeDaCategoriaNova,
            Color novaCorDaCategoria, double orcamentoDaCategoria) throws Exception {
        getUsuarioLogado().editaCategoria(nomeDaCategoriaAtual,
                nomeDaCategoriaNova, novaCorDaCategoria, orcamentoDaCategoria);
        atualizaUsuario();
    }

    /**
     * Metodo que remove uma categoria da lista de categorias do usuario por sua
     * posicao.
     *
     * @param posicaoDaCategoria Posicao da categoria na lista.
     * @throws Exception Caso haja algum parametro invalido.
     */
    public void removeCategoria(int posicaoDaCategoria) throws Exception {
        getUsuarioLogado().removeCategoria(posicaoDaCategoria);
        atualizaUsuario();
    }

    /**
     *
     * @return Lista de categorias do usuario.
     */
    public List<Categoria> getCategorias() {
        return getUsuarioLogado().getListaDeCategorias();
    }

    /**
     * Metodo que pesquisa uma categoria do usuario.
     * @param nomeDaCategoria Nome da categoria a ser pesquisada.
     * @return A categoria.
     * @throws Exception
     */
    public Categoria pesquisaCategoria(String nomeDaCategoria) throws Exception {
        return getUsuarioLogado().pesquisaCategoria(nomeDaCategoria);
    }

    private void resetaSaldoCategorias() {
        for (Iterator<Categoria> it = getCategorias().iterator(); it.hasNext();) {
            Categoria categoria = it.next();
            categoria.resetaSaldo();
        }
    }

    /**
     * Metodo que reseta o saldo da categoria todo mesa seu valor de orcamento.
     * @throws projetop2.utils.ProjetoHelperExceptions Caso haja algum problema com o arquivo de dados.
     */
    public void atualizaSaldoCategorias() throws ProjetoHelperExceptions {
        Calendar dataDeHoje = Calendar.getInstance();
        if (dataDeHoje.get(Calendar.MONTH) != mesAtualizacaoCategoria) {
            resetaSaldoCategorias();
            mesAtualizacaoCategoria = dataDeHoje.get(Calendar.MONTH);
            atualizaUsuario();
        }
    }

    /**
     * Metodo que adiciona uma conta a lista de contas do usuario.
     * @param nomeDaConta Nome da conta.
     * @throws Exception Caso haja algum parametro invalido.
     */
    public void adicionaConta(String nomeDaConta) throws Exception {
        getUsuarioLogado().adicionaConta(nomeDaConta);
        atualizaUsuario();
    }

    /**
     * Metodo que edita uma conta do usuario.
     * @param nomeDaContaAtual Nome atual da conta.
     * @param novoNomeDaConta Novo nome da conta,
     * @throws Exception Caso haja algum parametro invalido.
     */
    public void editaConta(String nomeDaContaAtual, String novoNomeDaConta) throws Exception {
        getUsuarioLogado().editaConta(nomeDaContaAtual, novoNomeDaConta);
        atualizaUsuario();
    }

    /**
     * Metodo que remove uma conta da lista de contas do usuario.
     * @param nomeDaConta Nome da conta a ser removida.
     * @throws Exception Caso haja algum parametro invalido.
     */
    public void removeConta(String nomeDaConta) throws Exception {
        getUsuarioLogado().removeConta(nomeDaConta);
        atualizaUsuario();
    }

    /**
     * Metodo que pesquisa uma conta.
     * @param nomeDaConta Nome da conta a ser pesquisada.
     * @return A conta.
     */
    public Conta pesquisaConta(String nomeDaConta) {
        return getUsuarioLogado().pesquisaConta(nomeDaConta);
    }

    /**
     * 
     * @return Lista de contas do usuario.
     */
    public List<Conta> getContas() {
        return getUsuarioLogado().getListaDeContas();
    }

    /**
     * Metodo que chama o extrato da conta.
     * @param nomeDaConta Nome da conta.
     * @param mes Mes do extrato.
     * @param ano Ano do extrato.
     * @return Lista de transacoes nesse mes/ano.
     * @throws Exception Caso haja algum parametro invalido.
     */
    public List<Transacao> getExtrato(String nomeDaConta, int mes, int ano) throws Exception {
        return pesquisaConta(nomeDaConta).getExtrato(mes, ano);
    }

    /**
     * Metodo que gera uma nova senha e a envia pro usuario.
     * @param emailDoUsuario E-mail do usuario.
     * @throws Exception Caso o usuario nao exista.
     */
    public void enviaSenha(String emailDoUsuario) throws Exception {
        String novaSenha = gerarNovaSenha();
        gerenteDeUsuarios.enviaSenha(emailDoUsuario, novaSenha);
    }

    private String gerarNovaSenha() {
        String caracteres = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        Random rnd = new Random();
        StringBuilder sb = new StringBuilder(7);
        for (int i = 0; i < 7; i++) {
            sb.append(caracteres.charAt(rnd.nextInt(caracteres.length())));
        }
        return sb.toString();
    }

}
