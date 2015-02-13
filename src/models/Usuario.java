package models;

import java.awt.Color;
import java.io.Serializable;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Classe que representa um usuario no sistema.
 *
 * @author Grupo
 * @version 1.0
 *
 */
public class Usuario implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 160773964573494224L;
    private String nomeDoUsuario;
    private String emailDoUsuario;
    private String senhaDoUsuario;
    private String dicaDeSenhaDoUsuario;
    private List<Conta> listaDeContas;
    private List<Categoria> listaDeCategorias;

    /**
     * Cria um usuario no sistema.
     *
     * @param emailDoUsuario E-mail do usuario.
     * @param nomeDoUsuario Nome do usuario.
     * @param senhaDoUsuario Senha do usuario.
     * @param dicaDoUsuario Dica de senha.
     *
     * @throws Exception Caso haja algum parametro invalido..
     */
    public Usuario(String emailDoUsuario, String nomeDoUsuario, String senhaDoUsuario, String dicaDoUsuario)
            throws Exception {
        isNomeValido(nomeDoUsuario);
        isSenhaValida(senhaDoUsuario);
        isEmailValido(emailDoUsuario);
        isDicaDeSenhaValida(dicaDoUsuario);

        listaDeContas = new ArrayList<>();
        listaDeCategorias = new ArrayList<>();
    }

    /**
     *
     * @return Nome do usuario.
     */
    public String getNome() {
        return nomeDoUsuario;
    }

    /**
     * Define o nome do usuario.
     *
     * @param novoNomeDoUsuario Novo nome do usuario.
     * @throws Exception Se o nome do usuario for invalido.
     */
    public void setNome(String novoNomeDoUsuario) throws Exception {
        isNomeValido(novoNomeDoUsuario);
    }

    private void isNomeValido(String nomeDoUsuario) throws Exception {
        if (nomeDoUsuario == null || nomeDoUsuario.trim().isEmpty()) {
            throw new Exception("Nome invalido.");
        }
        this.nomeDoUsuario = nomeDoUsuario;
    }

    /**
     * Retorna o e-mail do usuario.
     *
     * @return E-mail do usuario.
     */
    public String getEmail() {
        return emailDoUsuario;
    }

    /**
     * Define o e-mail do usuario.
     *
     * @param novoEmail
     * @throws Exception Se o e-mail for invalido.
     */
    public void setEmail(String novoEmail) throws Exception {
        isEmailValido(novoEmail);
    }

    private boolean isEmailValido(String email) throws Exception {
        Pattern p = Pattern
                .compile("^[\\w-]+(\\.[\\w-]+)*@([\\w-]+\\.)+[a-zA-Z]{2,7}$");
        Matcher m = p.matcher(email);
        if (m.find()) {
            this.emailDoUsuario = email;
            return true;
        }
        throw new Exception("Email invalido.");
    }

    /**
     *
     * @return Senha do usuario.
     */
    public String getSenha() {
        return senhaDoUsuario;
    }

    /**
     *
     * @return A dica de senha do usuario.
     */
    public String getDicaDeSenha() {
        return dicaDeSenhaDoUsuario;
    }

    /**
     * Define a senha do usuario.
     *
     * @param senha Senha do usuario.
     * @throws Exception Se a senha for invalida.
     */
    public void setSenha(String senha) throws Exception {
        isSenhaValida(senha);
    }

    private void isSenhaValida(String senha) throws Exception {
        if (senha == null || senha.trim().isEmpty()) {
            throw new Exception("Senha invalida.");
        }
        if (senha.trim().length() < 6 || senha.trim().length() > 8) {
            throw new Exception(
                    "Digite uma senha valida. A senha deve ser entre 6 e 8 caracteres.");
        }
        this.senhaDoUsuario = criptografar(senha);
    }

    /**
     * Define a dica de senha do usuario.
     *
     * @param dicaDeSenha Dica de senha.
     * @throws Exception Caso a dica de senha seja invalida.
     */
    public void setDicaDeSenha(String dicaDeSenha) throws Exception {
        isDicaDeSenhaValida(dicaDeSenha);
    }

    private void isDicaDeSenhaValida(String dicaDeSenha) throws Exception {
        if (dicaDeSenha == null) {
            throw new Exception("Dica de senha invalida.");
        }
        this.dicaDeSenhaDoUsuario = dicaDeSenha;
    }

    /**
     * Metodo que adiciona uma conta ao usuario.
     *
     * @param nomeDaConta Nome da conta.
     * @throws Exception
     */
    public void adicionaConta(String nomeDaConta) throws Exception {
        if (pesquisaConta(nomeDaConta) != null) {
            throw new Exception("A conta ja existe.");
        }

        listaDeContas.add(new Conta(nomeDaConta));
    }

    /**
     * Metodo que remove uma conta do usuario.
     *
     * @param nomeDaConta Nome da conta a ser removida.
     * @throws Exception Caso nao exista uma conta com esse nome.
     */
    public void removeConta(String nomeDaConta) throws Exception {
        Conta conta = pesquisaConta(nomeDaConta);
        if (conta == null) {
            throw new Exception("A conta nao existe.");
        }
        listaDeContas.remove(conta);
    }

    /**
     * Metodo que edita uma conta do usuario.
     *
     * @param nomeDaContaAtual Nome atual da conta.
     * @param novoNomeDaConta Novo nome da conta.
     * @throws Exception Caso ja exista uma conta com o novo nome.
     */
    public void editaConta(String nomeDaContaAtual, String novoNomeDaConta) throws Exception {
        Conta conta = pesquisaConta(nomeDaContaAtual);
        if (pesquisaConta(novoNomeDaConta) != null) {
            throw new Exception("A conta ja existe.");
        }
        conta.setNomeDaConta(novoNomeDaConta);
    }

    /**
     * Metodo que edita uma categoria do usuario.
     *
     * @param nomeDaCategoriaAtual Nome atual da categoria.
     * @param nomeDaCategoriaNovo Novo nome da categoria.
     * @param corDaCategoriaNova Nova cor da categoria.
     * @param orcamentoDaCategoriaNova Novo orcamento da categoria.
     * @throws Exception Caso nao exista uma categoria com o nome atual ou algum
     * parametro seja invalido.
     */
    public void editaCategoria(String nomeDaCategoriaAtual, String nomeDaCategoriaNovo, Color corDaCategoriaNova, double orcamentoDaCategoriaNova) throws Exception {
        Categoria categoria = pesquisaCategoria(nomeDaCategoriaAtual);
        if (categoria == null) {
            throw new Exception("A categoria nao existe.");
        }
        categoria.setNome(nomeDaCategoriaNovo);
        categoria.setCor(corDaCategoriaNova);
        categoria.setOrcamento(orcamentoDaCategoriaNova);
    }

    /**
     * Metodo que pesquisa uma conta pelo seu nome.
     *
     * @param nomeDaConta Nome da conta a ser pesquisada.
     * @return A conta ou null caso nao exista conta com tal nome.
     */
    public Conta pesquisaConta(String nomeDaConta) {
        if (listaDeContas.size() > 0) {
            for (Iterator<Conta> it = listaDeContas.iterator(); it.hasNext();) {
                Conta conta = it.next();
                if (conta.getNome().equals(nomeDaConta)) {
                    return conta;
                }
            }
        }
        return null;
    }

    /**
     *
     * @return A lista de contas do usuario.
     */
    public List<Conta> getListaDeContas() {
        return listaDeContas;
    }

    /**
     * Metodo que adiciona uma categoria ao usuario.
     *
     * @param nomeDaCategoria Nome da categoria.
     * @param corDaCategoria Cor da categoria.
     * @param orcamentoDaCategoria Orcamento da categoria.
     * @throws Exception Caso haja algum parametro invalido.
     */
    public void adicionaCategoria(String nomeDaCategoria, Color corDaCategoria, double orcamentoDaCategoria) throws Exception {
        if (nomeDaCategoria == null) {
            throw new Exception("Nome invalido.");
        }

        if (corDaCategoria == null) {
            throw new Exception("Cor invalida.");
        }

        if (pesquisaAdicionaCategoria(nomeDaCategoria) != null) {
            throw new Exception("A categoria ja existe.");
        }
            listaDeCategorias.add(new Categoria(nomeDaCategoria, corDaCategoria, orcamentoDaCategoria));
    }

    /**
     * Metodo que remove uma categoria do usuario.
     *
     * @param posicaoDaCategoria Posicao da categoria a ser removida.
     * @throws Exception Caso haja alguma transacao com essa categoria ou algum
     * parametro invalido.
     */
    public void removeCategoria(int posicaoDaCategoria) throws Exception {
        Categoria categoria = listaDeCategorias.get(posicaoDaCategoria);
        for (Iterator<Conta> it = listaDeContas.iterator(); it.hasNext();) {
            Conta conta = it.next();
            for (Iterator<Transacao> it2 = conta.getListaDeTransacoes().iterator(); it2.hasNext();) {
                Transacao transacao = it2.next();
                if (transacao.getCategoria().equals(categoria)) {
                    throw new Exception("Existem transacoes cadastradas nessa categoria, por favor, edite-as.");
                }
            }
        }
        listaDeCategorias.remove(categoria);

    }

    /**
     * @return Retorna a lista de categorias.
     */
    public List<Categoria> getListaDeCategorias() {
        return listaDeCategorias;
    }

    /**
     * Pesquisa uma categoria.
     *
     * @param nomeDaCategoria Nome da categoria a ser pesquisada.
     * @return A categoria.
     * @throws Exception Caso a categoria nao exista.
     */
    public Categoria pesquisaCategoria(String nomeDaCategoria) throws Exception {
        if (listaDeCategorias.size() > 0) {
            for (Iterator<Categoria> it = listaDeCategorias.iterator(); it.hasNext();) {
                Categoria categoriaSelecionada = it.next();
                if (categoriaSelecionada.getNome().equals(nomeDaCategoria)) {
                    return categoriaSelecionada;
                }
            }
        }
        throw new Exception("A categoria nao existe.");
    }

    private Categoria pesquisaAdicionaCategoria (String nomeDaCategoria) throws Exception {
        if (listaDeCategorias.size() > 0) {
            for (Iterator<Categoria> it = listaDeCategorias.iterator(); it.hasNext();) {
                Categoria categoriaSelecionada = it.next();
                if (categoriaSelecionada.getNome().equals(nomeDaCategoria)) {
                    return categoriaSelecionada;
                }
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return "Nome do Usuario: " + getNome() + ", E-mail: " + getEmail();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof Usuario)) {
            return false;
        }
        Usuario other = (Usuario) obj;
        if (getEmail() == null) {
            if (other.getEmail() != null) {
                return false;
            }
        } else if (!getEmail().equals(other.getEmail())) {
            return false;
        }
        return true;
    }

    /**
     * Metodo que criptografa uma string.
     * @param pass String a ser criptografada.
     * @return A string criptografada.
     * @throws NoSuchAlgorithmException 
     */
    public static String criptografar(String pass) throws NoSuchAlgorithmException {
        MessageDigest m = MessageDigest.getInstance("MD5");
        byte[] data = pass.getBytes();
        m.update(data, 0, data.length);
        BigInteger i = new BigInteger(1, m.digest());
        return String.format("%1$032X", i);
    }
}
