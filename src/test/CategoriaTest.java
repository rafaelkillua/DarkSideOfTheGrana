package test;

import java.awt.Color;

import models.Categoria;
import models.Conta;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class CategoriaTest {

	private Color corDaCategoria1 = Color.black, corDaCategoria2 = Color.GREEN;
	private String nomeDaCategoria1 = "Nome Teste da Categoria 1",
			nomeDaCategoria2 = "Nome Teste da Categoria 2";
	private double orcamentoDaCategoria1 = 100.0, orcamentoDaCategoria2 = 50.0;
	private Categoria categoria1, categoria2;
	private Conta conta;

	@Before
	public void criaObj() {
		try {
			categoria1 = new Categoria(nomeDaCategoria1, corDaCategoria1,
					orcamentoDaCategoria1);
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail("Mensagem de erro.");
		}

		try {
			categoria2 = new Categoria(nomeDaCategoria2, corDaCategoria2,
					orcamentoDaCategoria2);
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail("Mensagem de erro.");
		}

		try {
			conta = new Conta("Conta Teste");
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail("Mensagem de erro.");
		}
	}

	@Test
	public void testCategoria() {

		try {
			new Categoria("", null, 0);
		} catch (Exception e) {
			Assert.assertEquals("Mensagem de erro", "Nome invalido.",
					e.getMessage());
		}

		try {
			new Categoria(nomeDaCategoria1, null, 0);
		} catch (Exception e) {
			Assert.assertEquals("Mensagem de erro", "Cor invalida.",
					e.getMessage());
		}

		try {
			new Categoria("", corDaCategoria1, orcamentoDaCategoria1);
			Assert.fail("Era esperado um erro.");
		} catch (Exception e) {
			Assert.assertEquals("Mensagem de erro", "Nome invalido.",
					e.getMessage());
		}

		try {
			new Categoria(null, corDaCategoria1, orcamentoDaCategoria1);
			Assert.fail("Era esperado um erro.");
		} catch (Exception e) {
			Assert.assertEquals("Mensagem de erro", "Nome invalido.",
					e.getMessage());
		}

		try {
			new Categoria(nomeDaCategoria1, null, orcamentoDaCategoria1);
			Assert.fail("Era esperado um erro.");
		} catch (Exception e) {
			Assert.assertEquals("Mensagem de erro", "Cor invalida.",
					e.getMessage());
		}

		try {
			new Categoria(nomeDaCategoria1, corDaCategoria1, -1);
			Assert.fail("Era esperado um erro.");
		} catch (Exception e) {
			Assert.assertEquals("Mensagem de erro",
					"Orcamento invalido, digite valores maiores que 0 (zero).",
					e.getMessage());
		}

		try {
			new Categoria(nomeDaCategoria1, corDaCategoria1, 0);
			Assert.fail("Era esperado um erro.");
		} catch (Exception e) {
			Assert.assertEquals("Mensagem de erro",
					"Orcamento invalido, digite valores maiores que 0 (zero).",
					e.getMessage());
		}
	}

	@Test
	public void testGetNome() {
		Assert.assertEquals("Mensagem de erro", nomeDaCategoria1,
				categoria1.getNome());
		Assert.assertFalse("Mensagem de erro",
				nomeDaCategoria2.equals(categoria1.getNome()));
	}

	@Test
	public void testGetCor() {
		Assert.assertEquals("Mensagem de erro", corDaCategoria1,
				categoria1.getCor());
		Assert.assertFalse("Mensagem de erro",
				corDaCategoria2.equals(categoria1.getCor()));
	}

	@Test
	public void testSetNome() {
		Assert.assertEquals("Mensagem de erro", nomeDaCategoria1,
				categoria1.getNome());

		try {
			categoria1.setNome("");
		} catch (Exception e) {
			Assert.assertEquals("Mensagem de erro", "Nome invalido.",
					e.getMessage());
		}

		try {
			categoria1.setNome(null);
		} catch (Exception e) {
			Assert.assertEquals("Mensagem de erro", "Nome invalido.",
					e.getMessage());
		}

		try {
			categoria1.setNome(nomeDaCategoria2);
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail("Mensagem de erro.");
		}

		Assert.assertFalse("Mensagem de erro",
				nomeDaCategoria1.equals(categoria1.getNome()));
		Assert.assertEquals("Mensagem de erro", nomeDaCategoria2,
				categoria1.getNome());
	}

	@Test
	public void testSetCor() {
		Assert.assertEquals("Mensagem de erro", corDaCategoria1,
				categoria1.getCor());

		try {
			categoria1.setCor(null);
		} catch (Exception e) {
			Assert.assertEquals("Mensagem de erro", "Cor invalida.",
					e.getMessage());
		}

		try {
			categoria1.setCor(corDaCategoria2);
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail("Mensagem de erro.");
		}

		Assert.assertFalse("Mensagem de erro",
				corDaCategoria1.equals(categoria1.getCor()));
		Assert.assertEquals("Mensagem de erro", corDaCategoria2,
				categoria1.getCor());
	}

	@Test
	public void testGetOrcamento() {
		Assert.assertEquals("Mensagem de erro", orcamentoDaCategoria1,
				categoria1.getOrcamento(), 0.001);
		Assert.assertNotEquals("Mensagem de erro", orcamentoDaCategoria2,
				categoria1.getOrcamento(), 0.001);
	}

	@Test
	public void testSetOrcamento() {
		Assert.assertEquals("Mensagem de erro", orcamentoDaCategoria1,
				categoria1.getOrcamento(), 0.001);

		try {
			categoria1.setOrcamento(0);
		} catch (Exception e) {
			Assert.assertEquals("Mensagem de erro",
					"Orcamento invalido, digite valores maiores que 0 (zero).",
					e.getMessage());
		}

		try {
			categoria1.setOrcamento(-1);
		} catch (Exception e) {
			Assert.assertEquals("Mensagem de erro",
					"Orcamento invalido, digite valores maiores que 0 (zero).",
					e.getMessage());
		}

		try {
			categoria1.setOrcamento(orcamentoDaCategoria2);
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail("Mensagem de erro.");
		}

		Assert.assertNotEquals("Mensagem de erro", orcamentoDaCategoria1,
				categoria1.getOrcamento(), 0.001);
		Assert.assertEquals("Mensagem de erro", orcamentoDaCategoria2,
				categoria1.getOrcamento(), 0.001);
	}

	@Test
	public void testEqualsCategoria() {
		Assert.assertNotEquals("Mensagem de erro", categoria1, categoria2);
		try {
			categoria1.setCor(corDaCategoria2);
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail("Mensagem de erro.");
		}

		Assert.assertNotEquals("" + "Mensagem de erro", categoria1, categoria2);

		try {
			categoria1.setNome(nomeDaCategoria2);
			;
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail("Mensagem de erro.");
		}

		Assert.assertNotEquals("30 - Mensagem de erro", categoria1, categoria2);

		try {
			categoria1.setOrcamento(orcamentoDaCategoria2);
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail("Mensagem de erro.");
		}

		Assert.assertEquals("Mensagem de erro", categoria1, categoria2);
		Assert.assertEquals("Mensagem de erro", categoria1, categoria1);
		Assert.assertNotEquals("Mensagem de erro", categoria1, null);
		Assert.assertNotEquals("Mensagem de erro", categoria1, conta);

	}

	@Test
	public void testGetSaldo() {
		Assert.assertEquals("Mensagem de erro", orcamentoDaCategoria1,
				categoria1.getSaldo(), 0.001);
	}

	@Test
	public void testSetSaldo() {
		Assert.assertEquals("Mensagem de erro", orcamentoDaCategoria1,
				categoria1.getSaldo(), 0.001);

		categoria1.setSaldo(50.0);
		Assert.assertEquals("Mensagem de erro", orcamentoDaCategoria1 + 50.0,
				categoria1.getSaldo(), 0.001);
		
		categoria1.setSaldo(-orcamentoDaCategoria1);
		Assert.assertEquals("Mensagem de erro", 50.0,
				categoria1.getSaldo(), 0.001);
	}

	@Test
	public void testResetaSaldo() {
		Assert.assertEquals("Mensagem de erro", orcamentoDaCategoria1,
				categoria1.getSaldo(), 0.001);
		try {
			categoria1.setSaldo(orcamentoDaCategoria1);
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail("Mensagem de erro");
		}

		Assert.assertEquals("Mensagem de erro", 2 * orcamentoDaCategoria1,
				categoria1.getSaldo(), 0.001);
		categoria1.resetaSaldo();
		Assert.assertEquals("Mensagem de erro", orcamentoDaCategoria1,
				categoria1.getSaldo(), 0.001);
	}

	@Test
	public void testToString() {
		Assert.assertEquals("Mensagem de erro", "Nome da categoria: "
				+ nomeDaCategoria1 + ", orcamento: " + orcamentoDaCategoria1,
				categoria1.toString());

		Assert.assertNotEquals("Mensagem de erro", "Nome da categoria: "
				+ nomeDaCategoria2 + ", orcamento: " + orcamentoDaCategoria2,
				categoria1.toString());
	}

}
