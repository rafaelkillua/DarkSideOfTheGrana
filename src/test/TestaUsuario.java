package test;

import java.awt.Color;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import models.Categoria;
import models.Conta;
import models.Despesa;
import models.Transacao;
import models.Usuario;

import org.junit.Assert;
import org.junit.Test;

public class TestaUsuario {
	private Usuario usuario;
	private Usuario usuario2;
	private Categoria cat;
	private Calendar Data;
	private Transacao transacao;
	private List<Categoria> listaDeCategoria;
	private List<Conta> listaDeContas;
	private Conta conta = null;
	
	public static String criptografar (String pass) throws NoSuchAlgorithmException {
        MessageDigest m = MessageDigest.getInstance("MD5");
        byte[] data = pass.getBytes();
        m.update(data, 0, data.length);
        BigInteger i = new BigInteger(1, m.digest());
        return String.format("%1$032X", i);
    }
	
	@Test
	public void TestaCriaUsuario(){
		try{
			usuario = new Usuario("usuario@usuario.com", "usuario", "senhaaa", "dicadesenha");
		}catch (Exception e){
			e.printStackTrace();
			Assert.fail();
		}
		
		try{
			usuario = new Usuario("usuario@usuario.com", "", "senhaaa", "dicadesenha");
			Assert.fail();
		}catch (Exception e){
			Assert.assertEquals("Nome invalido.", e.getMessage());
		}
		
		try{
			usuario = new Usuario("usuario@usuario.com", null, "senhaaa", "dicadesenha");
			Assert.fail();
		}catch (Exception e){
			Assert.assertEquals("Nome invalido.", e.getMessage());
		}
		
		try{
			usuario = new Usuario("usuario@usuario.com", " ", "senhaaa", "dicadesenha");
			Assert.fail();
		}catch (Exception e){
			Assert.assertEquals("Nome invalido.", e.getMessage());
		}
		
		try{
			usuario = new Usuario("usuario.usuario.com", "usuario", "senhaaa", "dicadesenha");
			Assert.fail();
		}catch (Exception e){
			Assert.assertEquals("Email invalido.", e.getMessage());
		}
		
		try{
			usuario = new Usuario("usuario@usuario.com", "usuario", "s", "dicadesenha");
			Assert.fail();
		}catch (Exception e){
			Assert.assertEquals("Digite uma senha valida. A senha deve ser entre 6 e 8 caracteres.", e.getMessage());
		}
		
		try{
			usuario = new Usuario("usuario@usuario.com", "usuario", "", "dicadesenha");
			Assert.fail();
		}catch (Exception e){
			Assert.assertEquals("Senha invalida.", e.getMessage());
		}
		
		try{
			usuario = new Usuario("usuario@usuario.com", "usuario", " ", "dicadesenha");
			Assert.fail();
		}catch (Exception e){
			Assert.assertEquals("Senha invalida.", e.getMessage());
		}
	}
	
	@Test
	public void TestaUsuariosIguais(){
		try{
			usuario = new Usuario("usuario@usuario.com", "usuario", "senhaaa", "dicadesenha");
		}catch (Exception e){
			Assert.fail();
			e.printStackTrace();
		}
		
		try{
			 usuario2 = new Usuario("usuario@usuario.com", "usuario2", "senhaaa2", "dicadesenha");
		}catch (Exception e){
			Assert.fail();
			e.printStackTrace();
		}
		
		
		Assert.assertTrue(usuario.equals(usuario2));
		
		try{
			usuario2 = new Usuario("usuario@usuario2.com", "usuario", "senhaaa", "dicadesenha");
		}catch (Exception e){
			Assert.fail();
			e.printStackTrace();
		}
		
		Assert.assertFalse(usuario.equals(usuario2));
		
		Data = Calendar.getInstance();
		try {
			transacao = new Despesa("comida", Data, new Categoria("sexo", Color.RED, 30.00), 2.50, 365);
		} catch (Exception e) {
			Assert.fail();
			e.printStackTrace();
		}
		
		Assert.assertFalse(usuario.equals(transacao));
	}
	
	@Test
	public void testaGetSetNome(){
		try{
			usuario = new Usuario("usuario@usuario.com", "usuario", "senhaaa", "dicadesenha");
		}catch (Exception e){
			Assert.fail();
			e.printStackTrace();
		}
		
		Assert.assertEquals("usuario", usuario.getNome());
		
		try {
			usuario.setNome("novoNome");
		} catch (Exception e) {
			Assert.fail();
			e.printStackTrace();
		}
		
		Assert.assertEquals("novoNome", usuario.getNome());
	}
	
	@Test
	public void testaGetSetEmail(){
		try{
			usuario = new Usuario("usuario@usuario.com", "usuario", "senhaaa", "dicadesenha");
		}catch (Exception e){
			Assert.fail();
			e.printStackTrace();
		}
		
		Assert.assertEquals("usuario@usuario.com", usuario.getEmail());
		
		try {
			usuario.setEmail("usuarionovo@usuario.com");
		} catch (Exception e) {
			Assert.fail();
			e.printStackTrace();
		}
		
		Assert.assertEquals("usuarionovo@usuario.com", usuario.getEmail());
	}
	
	@Test
	public void testaGetSetSenhaDicaDeSenha(){
		try{
			usuario = new Usuario("usuario@usuario.com", "usuario", "senhaaa", "dicadesenha");
		}catch (Exception e){
			Assert.fail();
			e.printStackTrace();
		}
		
		try {
			Assert.assertEquals(criptografar("senhaaa"), usuario.getSenha());
		} catch (NoSuchAlgorithmException e1) {
			Assert.fail();
			e1.printStackTrace();
		}
		
		Assert.assertEquals("dicadesenha", usuario.getDicaDeSenha());
		
		try{
			usuario.setSenha("novaSe");
		}catch (Exception e){
			Assert.fail();
			e.printStackTrace();
		}
		
		try {
			Assert.assertEquals(criptografar("novaSe"), usuario.getSenha());
		} catch (NoSuchAlgorithmException e1) {
			Assert.fail();
			e1.printStackTrace();
		}
		
		try {
			usuario.setDicaDeSenha(null);
			Assert.fail();
		} catch (Exception e) {
			Assert.assertEquals("Dica de senha invalida.", e.getMessage());
		}
		
		
		try {
			usuario.setDicaDeSenha("novaDica");
		} catch (Exception e) {
			Assert.fail();
			e.printStackTrace();
		}
		
		Assert.assertEquals("novaDica", usuario.getDicaDeSenha());
	}
	
	@Test
	public void testaToString(){
		try{
			usuario = new Usuario("usuario@usuario.com", "usuario", "senhaaa", "dicadesenha");
		}catch (Exception e){
			e.printStackTrace();
			Assert.fail();
		}
		
		Assert.assertEquals("Nome do Usuario: usuario, E-mail: usuario@usuario.com", usuario.toString());
	}
	
	@Test
	public void testaEquals(){
		try {
			usuario = new Usuario("usuario@usuario.com", "usuario", "senhaaa", "dicadesenha");
		} catch (Exception e) {
			Assert.fail();
			e.printStackTrace();
		}
		
		try {
			usuario2 = new Usuario("usuario@usuario2.com", "usuario", "senhaaa", "dicadesenha");
		} catch (Exception e) {
			Assert.fail();
			e.printStackTrace();
		}
		
		Assert.assertTrue(usuario.equals(usuario));
		Assert.assertFalse(usuario.equals(null));
		Assert.assertFalse(usuario.equals(usuario2));
	}
	
	@Test
	public void testaCategoria(){
		try {
			usuario = new Usuario("usuario@usuario.com", "usuario", "senhaaa", "dicadesenha");
		} catch (Exception e) {
			Assert.fail();
			e.printStackTrace();
		}
		
		try {
			cat = new Categoria("categoria", Color.BLACK, 2.50);
		} catch (Exception e1) {
			Assert.fail();
			e1.printStackTrace();
		}
		
		try {
			usuario.adicionaCategoria("categoria", Color.BLACK, 2.50);
		} catch (Exception e) {
			Assert.fail();
			e.printStackTrace();
		}
		
		try {
			usuario.adicionaCategoria("categoria", Color.BLACK, 2.50);
			Assert.fail();
		} catch (Exception e) {
			Assert.assertEquals("A categoria ja existe.", e.getMessage());
		}
		
		try {
			usuario.adicionaCategoria(null, Color.BLACK, 2.50);
			Assert.fail();
		} catch (Exception e) {
			Assert.assertEquals("Nome invalido.", e.getMessage());
		}
		
		try {
			usuario.adicionaCategoria("categoria2", null, 2.50);
			Assert.fail();
		} catch (Exception e) {
			Assert.assertEquals("Cor invalida.", e.getMessage());
		}
		
		try {
			Assert.assertTrue(usuario.pesquisaCategoria("categoria").equals(cat));
		} catch (Exception e1) {
			Assert.fail();
			e1.printStackTrace();
		}
		
		listaDeCategoria = new ArrayList<>();
		
		Assert.assertFalse(usuario.getListaDeCategorias().equals(listaDeCategoria));
		listaDeCategoria.add(cat);
		
		Assert.assertTrue(usuario.getListaDeCategorias().equals(listaDeCategoria));
		try {
			usuario.removeCategoria(0);
		} catch (Exception e) {
			Assert.fail();
			e.printStackTrace();
		}
		
		Assert.assertFalse(usuario.getListaDeCategorias().equals(listaDeCategoria));
		listaDeCategoria.remove(cat);
		Assert.assertTrue(usuario.getListaDeCategorias().equals(listaDeCategoria));
		
		
		try {
			usuario.adicionaCategoria("categoria", Color.BLACK, 2.50);
		} catch (Exception e) {
			Assert.fail();
			e.printStackTrace();
		}
		
		try {
			usuario.editaCategoria("categoria", "categoriaQualquer", Color.GREEN, 3.50);
		} catch (Exception e) {
			Assert.fail();
			e.printStackTrace();
		}
		
		try {
			usuario.editaCategoria("categoria2", "categoriaQualquer", Color.GREEN, 3.50);
			Assert.fail("Esperava excecao.");
		} catch (Exception e) {
			Assert.assertEquals("A categoria nao existe.", e.getMessage());
		}
		
		try {
			usuario.editaCategoria("categoria", "outraCategoria", Color.GREEN, 3.50);
			Assert.fail();
		} catch (Exception e) {
			Assert.assertEquals("A categoria nao existe.", e.getMessage());
		}
		
		try {
			cat = new Categoria("categoriaQualquer", Color.GREEN, 3.50);
		} catch (Exception e) {
			Assert.fail();
			e.printStackTrace();
		}
		
		try {
			usuario.pesquisaCategoria("amizade");
			Assert.fail();
		} catch (Exception e) {
			Assert.assertEquals("A categoria nao existe.", e.getMessage());
		}
		
	}
	
	@Test
	public void testaConta(){
		
		listaDeContas = new ArrayList<>();
		
		try{
			usuario = new Usuario("usuario@usuario.com", "usuario", "senhaaa", "dicadesenha");
		}catch (Exception e){
			e.printStackTrace();
			Assert.fail();
		}
		
		
		try {
			conta = new Conta("Conta");
		} catch (Exception e) {
			Assert.fail();
			e.printStackTrace();
		}
		
		try {
			usuario.adicionaConta("Conta");
		} catch (Exception e) {
			Assert.fail();
			e.printStackTrace();
		}
		
		Assert.assertFalse(usuario.getListaDeContas().equals(listaDeContas));
		
		try {
			usuario.removeConta("Conta");
		} catch (Exception e) {
			Assert.fail();
			e.printStackTrace();
		}
		
		Assert.assertTrue(usuario.getListaDeContas().equals(listaDeContas));
		
		try {
			usuario.adicionaConta("Conta");
		} catch (Exception e) {
			Assert.fail();
			e.printStackTrace();
		}
		
		try {
			usuario.adicionaConta("Conta2");
		} catch (Exception e) {
			Assert.fail();
			e.printStackTrace();
		}
		
		try {
			usuario.adicionaConta("Conta");
			Assert.fail();
		} catch (Exception e) {
			Assert.assertEquals("A conta ja existe.", e.getMessage());
		}
		
		try {
			usuario.removeConta("QualquerConta");
			Assert.fail();
		} catch (Exception e) {
			Assert.assertEquals("A conta nao existe.", e.getMessage());
		}
		
		try {
			usuario.editaConta("Conta", "NovaConta");
		} catch (Exception e) {
			Assert.fail();
			e.printStackTrace();
		}
		
		try {
			usuario.editaConta("NovaConta", "Conta2");
			Assert.fail();
		} catch (Exception e) {
			Assert.assertEquals("A conta ja existe.", e.getMessage());
		}
	}
}
