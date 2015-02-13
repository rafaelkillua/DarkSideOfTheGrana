package test;

import java.awt.Color;
import java.text.DateFormat;
import java.util.Calendar;

import models.Categoria;
import models.Conta;
import models.Recorrencia;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ContaTest {
	private String nomeDaConta = "Nome Teste 1", nomeDaConta2 = "Nome Teste 2";
	private Conta contaTeste1, contaTeste2;
	private Categoria categoria;
	private String descricaoDaTransacao = "Descricao Teste 1",
			tipo1 = "Despesa", tipo2 = "Provento",
			descricaoDaTransacao2 = "Descricao Teste 2";
	private double valorDaTransacao = 100.00;
	private int recorrencia1 = Recorrencia.ANUAL.getValor(),
			recorrencia2 = Recorrencia.TRIMESTRAL.getValor();
	private Calendar dataDaTransacao = Calendar.getInstance(),
			dataDaTransacao2 = Calendar.getInstance();

	@Before
	public void criaObj() {
		try {
			contaTeste1 = new Conta(nomeDaConta);
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail("Mensagem de erro");
		}

		try {
			contaTeste2 = new Conta(nomeDaConta2);
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail("Mensagem de erro");
		}

		try {
			categoria = new Categoria("Categoria Teste", Color.black, 100.00);
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail("Mensagem de erro");
		}
	}

	@Test
	public void testConta() {
		try {
			new Conta(null);
		} catch (Exception e) {
			Assert.assertEquals("Mensagem de erro",
					"Nome da conta invalido.", e.getMessage());
		}

		try {
			new Conta("");
		} catch (Exception e) {
			Assert.assertEquals("Mensagem de erro",
					"Nome da conta invalido.", e.getMessage());
		}

		try {
			new Conta("  ");
		} catch (Exception e) {
			Assert.assertEquals("Mensagem de erro",
					"Nome da conta invalido.", e.getMessage());
		}
	}

	@Test
	public void testGetNome() {
		Assert.assertEquals("Mensagem de erro", contaTeste1.getNome(),
				nomeDaConta);
		Assert.assertNotEquals("Mensagem de erro", contaTeste1.getNome(),
				nomeDaConta2);
	}

	@Test
	public void testSetNomeDaConta() {
		try {
			contaTeste1.setNomeDaConta(" ");
		} catch (Exception e) {
			Assert.assertEquals("Mensagem de erro",
					"Nome da conta invalido.", e.getMessage());
		}

		try {
			contaTeste1.setNomeDaConta(null);
		} catch (Exception e) {

			Assert.assertEquals("Mensagem de erro",
					"Nome da conta invalido.", e.getMessage());
		}

		try {
			contaTeste1.setNomeDaConta(nomeDaConta2);
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail("Mensagem de erro");
		}

		Assert.assertNotEquals("Mensagem de erro", nomeDaConta,
				contaTeste1.getNome());
		Assert.assertEquals("Mensagem de erro", nomeDaConta2,
				contaTeste1.getNome());
	}

	@Test
	public void testGetListaDeTransacoes() {
		Assert.assertEquals("Mensagem de erro", contaTeste1
				.getListaDeTransacoes().size(), 0);
		Assert.assertNotEquals("Mensagem de erro", contaTeste1
				.getListaDeTransacoes().size(), 1);
	}

	@Test
	public void testGetExtrato() {

		try {
			contaTeste1.getExtrato(1, 2014);
		} catch (Exception e) {
			Assert.assertEquals("21 - Mensagem de erro",
					"Nao foi feita nenhuma transacao.", e.getMessage());
		}

		try {
			contaTeste1.adicionaTransacao(descricaoDaTransacao,
					dataDaTransacao, categoria, valorDaTransacao, recorrencia1,
					tipo2);
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail("Mensagem de erro");
		}

		try {
			contaTeste1.getExtrato(1, 2014);
		} catch (Exception e) {
			Assert.assertEquals("22 - Mensagem de erro", "Mes invalido.",
					e.getMessage());
		}
		try {
			contaTeste1.getExtrato(12, 2014);
		} catch (Exception e) {
			Assert.assertEquals("23 - Mensagem de erro", "Mes invalido.",
					e.getMessage());
		}
		DateFormat dataDeFormatacao1 = DateFormat
				.getDateInstance(DateFormat.MEDIUM);
		try {
			Assert.assertEquals(
					"Mensagem de erro",
					contaTeste1.getExtrato(8, 2014).toString(),
					"[Provento - Descricao: Descricao Teste 1, Data: "
							+ dataDeFormatacao1.format(dataDaTransacao
									.getTime())
							+ ", Valor: 100.0, Categoria: Categoria Teste, Recorrencia: 365]");
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail();
		}

		dataDaTransacao2.add(Calendar.YEAR, 1);
		try {
			contaTeste1.adicionaTransacao(descricaoDaTransacao,
					dataDaTransacao2, categoria, valorDaTransacao,
					recorrencia1, tipo2);
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail("Mensagem de erro");
		}

		try {
			Assert.assertEquals(
					"Mensagem de erro",
					contaTeste1.getExtrato(8, 2014).toString(),
					"[Provento - Descricao: Descricao Teste 1, Data: "
							+ dataDeFormatacao1.format(dataDaTransacao
									.getTime())
							+ ", Valor: 100.0, Categoria: Categoria Teste, Recorrencia: 365]");
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail();
		}

	}

	@Test
	public void testEditaTransacao() {
		try {
			contaTeste1.editaTransacao(0, descricaoDaTransacao,
					dataDaTransacao, categoria, valorDaTransacao, recorrencia2,
					tipo2);
		} catch (Exception e) {
			Assert.assertEquals("19 - Mensagem de erro",
					"Nao existe nenhuma transacao adicionada nesta conta.",
					e.getMessage());
		}

		try {
			contaTeste1.adicionaTransacao(descricaoDaTransacao,
					dataDaTransacao, categoria, valorDaTransacao, recorrencia1,
					tipo2);
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail("Mensagem de erro");
		}
		DateFormat dataDeFormatacao1 = DateFormat
				.getDateInstance(DateFormat.MEDIUM);
		try {
			Assert.assertEquals(
					"Mensagem de erro",
					contaTeste1.pesquisaTransacao(0).toString(),
					"Provento - Descricao: Descricao Teste 1, "
							+ "Data: "
							+ dataDeFormatacao1.format(dataDaTransacao
									.getTime())
							+ ", Valor: 100.0, Categoria: Categoria Teste, Recorrencia: 365");
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail("Mensagem de erro");
		}

		try {
			contaTeste1.editaTransacao(0, descricaoDaTransacao2,
					dataDaTransacao, categoria, valorDaTransacao, recorrencia2,
					tipo1);
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail("Mensagem de erro");
		}

		try {
			Assert.assertEquals(
					"Mensagem de erro",
					contaTeste1.pesquisaTransacao(0).toString(),
					"Despesa - Descricao: Descricao Teste 2, "
							+ "Data: "
							+ dataDeFormatacao1.format(dataDaTransacao
									.getTime())
							+ ", Valor: -100.0, Categoria: Categoria Teste, Recorrencia: 90");
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail("Mensagem de erro");
		}

		try {
			contaTeste1.editaTransacao(0, descricaoDaTransacao,
					dataDaTransacao, categoria, valorDaTransacao, recorrencia2,
					tipo1);
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail("Mensagem de erro");
		}

		try {
			Assert.assertEquals(
					"Mensagem de erro",
					contaTeste1.pesquisaTransacao(0).toString(),
					"Despesa - Descricao: Descricao Teste 1, "
							+ "Data: "
							+ dataDeFormatacao1.format(dataDaTransacao
									.getTime())
							+ ", Valor: -100.0, Categoria: Categoria Teste, Recorrencia: 90");
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail("Mensagem de erro");
		}

	}

	@Test
	public void testAdicionaTransacao() {
		Assert.assertEquals("Mensagem de erro", contaTeste1
				.getListaDeTransacoes().size(), 0);

		Assert.assertNotEquals("Mensagem de erro", contaTeste1
				.getListaDeTransacoes().size(), 1);

		try {
			contaTeste1.adicionaTransacao(descricaoDaTransacao,
					dataDaTransacao, categoria, valorDaTransacao, recorrencia1,
					tipo1);
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail("Mensagem de erro");
		}

		Assert.assertEquals("Mensagem de erro", contaTeste1
				.getListaDeTransacoes().size(), 1);
	}

	@Test
	public void testRemoveTransacao() {
		try {
			contaTeste1.removeTransacao(0);
		} catch (Exception e) {
			Assert.assertEquals("Mensagem de erro",
					"Nao existem nenhuma transacao na conta.", e.getMessage());
		}

		Assert.assertEquals("Mensagem de erro", contaTeste1
				.getListaDeTransacoes().size(), 0);

		try {
			contaTeste1.adicionaTransacao(descricaoDaTransacao,
					dataDaTransacao, categoria, valorDaTransacao, recorrencia1,
					tipo2);
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail("Mensagem de erro");
		}

		Assert.assertEquals("15 - Mensagem de erro", contaTeste1
				.getListaDeTransacoes().size(), 1);

		try {
			contaTeste1.removeTransacao(0);
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail("Mensagem de erro");
		}

		Assert.assertEquals("Mensagem de erro", contaTeste1
				.getListaDeTransacoes().size(), 0);

	}

	@Test
	public void testPesquisaTransacao() {
		DateFormat dataDeFormatacao1 = DateFormat
				.getDateInstance(DateFormat.MEDIUM);
		try {
			contaTeste1.pesquisaTransacao(0);
		} catch (Exception e) {
			Assert.assertEquals("Mensagem de erro",
					"Nao existe nenhuma transacao adicionada nesta conta.",
					e.getMessage());
		}

		try {
			contaTeste1.adicionaTransacao(descricaoDaTransacao,
					dataDaTransacao, categoria, valorDaTransacao, recorrencia1,
					tipo2);
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail("Mensagem de erro");
		}

		try {
			Assert.assertEquals(
					"Mensagem de erro",
					contaTeste1.pesquisaTransacao(0).toString(),
					"Provento - Descricao: Descricao Teste 1, Data: "
							+ dataDeFormatacao1.format(dataDaTransacao
									.getTime())
							+ ", Valor: 100.0, Categoria: Categoria Teste, Recorrencia: 365");
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail("Mensagem de erro");
		}

	}

	@Test
	public void testEqualsObject() {
		Assert.assertNotEquals("Mensagem de erro", contaTeste1,
				contaTeste2);
		try {
			contaTeste1.setNomeDaConta(nomeDaConta2);
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail("Mensagem de erro");
		}

		Assert.assertEquals("Mensagem de erro", contaTeste1, contaTeste2);

		try {
			contaTeste2.setNomeDaConta(nomeDaConta2);
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail("Mensagem de erro.");
		}

		Assert.assertEquals("Mensagem de erro", contaTeste1, contaTeste2);

		Assert.assertNotEquals("Mensagem de erro", contaTeste1, categoria);

	}

	@Test
	public void testToString() {
		Assert.assertEquals("Mensagem de erro",
				"Nome da conta: " + nomeDaConta, contaTeste1.toString());
		Assert.assertNotEquals("Mensagem de erro", "Nome da conta: "
				+ nomeDaConta, contaTeste2.toString());
	}

}
