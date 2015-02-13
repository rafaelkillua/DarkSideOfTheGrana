package dao;

import core.Sistema;
import java.io.File;
import java.math.BigInteger;
import java.net.URLDecoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import models.Usuario;
import projetop2.utils.ArquivoDeDados;
import projetop2.utils.ProjetoHelperExceptions;

/**
 *
 * @author Grupo
 * @version 1.0
 *
 */
public class GerenteDeUsuarios {

    private final ArrayList<Usuario> listaDeUsuarios;
    private final ArquivoDeDados<ArrayList<Usuario>> arquivo;

    /**
     * Classe que cria uma lista de usuarios do sistema, onde somente com ela
     * poderam ser adicionados, pesquisados e feito o login no sistema.
     *
     * @throws projetop2.utils.ProjetoHelperExceptions Caso haja problema com o
     * arquivo de dados.
     */
    public GerenteDeUsuarios() throws Exception {

        String caminhoDoArquivo = getPath();
        File arquivoDeTeste = new File(caminhoDoArquivo + "data.dat");
        arquivo = new ArquivoDeDados<>(caminhoDoArquivo + "data.dat"); 

        if (arquivoDeTeste.exists()) {
            listaDeUsuarios = arquivo.carregaObjetos();
        } else {
            listaDeUsuarios = new ArrayList<>();
        }

    }

    private String getPath() throws Exception {
        String path = Sistema.class.getProtectionDomain().getCodeSource().getLocation().getPath();
        String decodedPath = path;
        decodedPath = URLDecoder.decode(path, "UTF-8");

        String absolutePath = decodedPath.substring(0, decodedPath.lastIndexOf("/")) + "/";
        return absolutePath;
    }

    /**
     * Metodo que adiciona um novo usuario.
     *
     * @param email E-mail do usuario.
     * @param nome Nome do usuario.
     * @param senha Senha do usuario.
     * @param dica Dica do usuario.
     * @throws Exception Se o usuario ja existir dentro da lista.
     */
    public void adicionaUsuario(String email, String nome, String senha,
            String dica) throws Exception {

        if (pesquisaUsuario(email) != null) {
            throw new Exception("Usuario ja existe.");
        }

        listaDeUsuarios.add(new Usuario(email, nome, senha, dica));
        arquivo.salvaObjeto(listaDeUsuarios);
    }

    /**
     * Metodo que ira realizar o login do usuario no sistema.
     *
     * @param emailDoUsuario Email do usuario.
     * @param senha Senha do usuario.
     * @return O usuario a ser logado
     * @throws Exception Caso usuario nao exista ou sua senha seja incorreta.
     */
    public Usuario loginDoUsuario(String emailDoUsuario, String senha)
            throws Exception {
        if (pesquisaUsuario(emailDoUsuario) == null) {
            throw new Exception("Usuario nao existe.");
        } else if (!pesquisaUsuario(emailDoUsuario).getSenha().equals(criptografar(senha))) {
            throw new Exception("Senha incorreta.\nSua dica de senha é:\n" + pesquisaUsuario(emailDoUsuario).getDicaDeSenha());
        }

        return pesquisaUsuario(emailDoUsuario);
    }

    private Usuario pesquisaUsuario(String emailDoUsuario) {

        for (Iterator<Usuario> it = listaDeUsuarios.iterator(); it.hasNext();) {
            Usuario usuario = it.next();
            if (usuario.getEmail().equals(emailDoUsuario)) {
                return usuario;
            }
        }
        return null;
    }

    /**
     * Metodo que edita um usuario sem editar seu email.
     *
     * @param emailDoUsuario E-mail do usuario a ser editado.
     * @param novoNomeDoUsuario Novo nome do usuario a ser editado.
     * @param novaSenhaDoUsuario Nova senha do usuario a ser editado.
     * @param novaDicaDoUsuario Nova dica do usuario a ser editado.
     * @throws Exception Caso os parametros sejam invalidos.
     */
    public void editaUsuarioSemEmail(String emailDoUsuario, String novoNomeDoUsuario, String novaSenhaDoUsuario, String novaDicaDoUsuario) throws Exception {
        Usuario usuarioAtual = pesquisaUsuario(emailDoUsuario);
        if (!usuarioAtual.getNome().equals(novoNomeDoUsuario)) {
            usuarioAtual.setNome(novoNomeDoUsuario);
        }
        if (novaSenhaDoUsuario != null) {
            usuarioAtual.setSenha(novaSenhaDoUsuario);
        }
        if (!usuarioAtual.getDicaDeSenha().equals(novaDicaDoUsuario)) {
            usuarioAtual.setDicaDeSenha(novaDicaDoUsuario);
        }
    }

    /**
     * Metodo que edita o email do usuario.
     *
     * @param emailAtualDoUsuario E-mail atual do usuario.
     * @param novoEmailDoUsuario Novo e-mail do usuario.
     * @throws Exception Caso o novo e-mail seja invalido.
     */
    public void editaEmailUsuario(String emailAtualDoUsuario, String novoEmailDoUsuario) throws Exception {
        Usuario usuario = pesquisaUsuario(emailAtualDoUsuario);
        usuario.setEmail(novoEmailDoUsuario);
    }

    /**
     * Metodo que atualiza um usuario no arquivo de dados.
     *
     * @param usuario O usuario a ser atualizado.
     * @throws ProjetoHelperExceptions Caso haja algum problema no arquivo de
     * dados.
     */
    public void atualizaUsuario(Usuario usuario)
            throws ProjetoHelperExceptions {
        int posicao = 0;
        if (!listaDeUsuarios.isEmpty()) {
            for (int i = 0; i < listaDeUsuarios.size(); i++) {
                if (listaDeUsuarios.get(i).getEmail().equals(usuario.getEmail())) {
                    posicao = i;
                    break;
                }
            }
            listaDeUsuarios.remove(posicao);
            listaDeUsuarios.add(posicao, usuario);
        }
        arquivo.salvaObjeto(listaDeUsuarios);
    }

    /**
     * Metodo que envia a senha de um usuario pro seu e-mail.
     *
     * @param emailDoUsuario E-mail do usuario a ter sua senha recuperada.
     * @param novaSenha Nova senha gerada pelo sistema.
     * @throws Exception Caso o usuario nao exista.
     */
    public void enviaSenha(String emailDoUsuario, String novaSenha) throws Exception {
        isEmailValido(emailDoUsuario);
        if (pesquisaUsuario(emailDoUsuario) == null) {
            throw new Exception("Usuario inexistente.");
        }
        Properties properties = System.getProperties();
        properties.setProperty("mail.smtp.host", "smtp.gmail.com");
        properties.setProperty("mail.smtp.port", "587");
        properties.setProperty("mail.smtp.auth", "true");
        properties.setProperty("mail.smtp.starttls.enable", "true");
        Session session = Session.getInstance(properties,
                new javax.mail.Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication("gerenciadorfinanceiroufcg@gmail.com", "gerenciador123");
                    }
                });
        MimeMessage mensagem = new MimeMessage(session);
        mensagem.setFrom(new InternetAddress("gerenciadorfinanceiroufcg@gmail.com"));
        mensagem.addRecipient(Message.RecipientType.TO, new InternetAddress(emailDoUsuario));
        mensagem.setSubject("Recuperação de senha - Dark Side of the Grana");
        mensagem.setText("Sua nova senha é: " + novaSenha + "\n Faça login e mude sua senha assim que possível.");

        Transport.send(mensagem);
        editaUsuarioSemEmail(emailDoUsuario, pesquisaUsuario(emailDoUsuario).getNome(), novaSenha, pesquisaUsuario(emailDoUsuario).getDicaDeSenha());
    }

    private boolean isEmailValido(String email) throws Exception {
        Pattern p = Pattern
                .compile("^[\\w-]+(\\.[\\w-]+)*@([\\w-]+\\.)+[a-zA-Z]{2,7}$");
        Matcher m = p.matcher(email);
        if (!m.find()) {
            throw new Exception("E-mail invalido.");
        }
        return true;
    }

    private static String criptografar(String pass) throws NoSuchAlgorithmException {
        MessageDigest m = MessageDigest.getInstance("MD5");
        byte[] data = pass.getBytes();
        m.update(data, 0, data.length);
        BigInteger i = new BigInteger(1, m.digest());
        return String.format("%1$032X", i);
    }

}
