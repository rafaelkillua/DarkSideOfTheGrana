package test;

import java.awt.Color;
import java.util.Calendar;

import models.Categoria;
import models.Provento;
import models.Transacao;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TestaProvento {

    Transacao prov;
    Categoria cat;
    Calendar data;
    Calendar data2;
    Categoria cat2;

    @Before
    public void CriaObj() throws Exception {

        data = Calendar.getInstance();
        data2 = Calendar.getInstance();

        data2.set(Calendar.MONTH, 4);
        data2.set(Calendar.YEAR, 2015);

        data.set(Calendar.MONTH, 2);
        data.set(Calendar.YEAR, 2014);

        try {
            cat = new Categoria("teste", Color.BLACK, 25.00);
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail();
        }

        prov = new Provento("dinheiro", data, cat, 2.50, 365);
    }

    @Test
    public void TestaGets() {
        Assert.assertTrue(prov.getDataDeInsercao().get(Calendar.MONTH) == 2);
        Assert.assertTrue(prov.getDescricao().equals("dinheiro"));
        Assert.assertTrue(prov.getRecorrencia() == 365);
        Assert.assertTrue(prov.getValor() == 2.50);
        Assert.assertTrue(prov.getTipoDaTransacao().equals("Provento"));
        Assert.assertTrue(prov.getCategoria().getNome().equals("teste"));
        Assert.assertTrue(prov.getCategoria().getCor().equals(Color.BLACK));
    }

    @Test
    public void TestaSets() {

        try {
            cat2 = new Categoria("categoria", Color.GREEN, 20.00);
        } catch (Exception e) {
            Assert.fail();
            e.printStackTrace();
        }

        prov.setCategoria(cat2);
        Assert.assertTrue(cat2.getNome().equals("categoria"));
        Assert.assertTrue(cat2.getCor().equals(Color.GREEN));
        Assert.assertTrue(cat2.getOrcamento() == 20.00);

        prov.setRecorrencia(31);
        Assert.assertTrue(prov.getRecorrencia() == 31);

        prov.setDataDeInsercao(data2);
        Assert.assertTrue(prov.getDataDeInsercao().get(Calendar.MONTH) == 4);
        Assert.assertTrue(prov.getDataDeInsercao().get(Calendar.YEAR) == 2015);

        prov.setDescricao("alimentos");
        Assert.assertTrue(prov.getDescricao().equals("alimentos"));
        
        try {
			prov.setValor(0);
			Assert.fail("Esperava excecao");
		} catch (Exception e1) {
			Assert.assertEquals("O valor do provento deve ser positivo e diferente de zero.", e1.getMessage());
		}

        try {
            prov.setValor(3.50);
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail();
        }
        Assert.assertTrue(prov.getValor() == 3.50);

        try {
            prov.getCategoria().setCor(Color.RED);
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail();
        }

        Assert.assertTrue(prov.getCategoria().getCor().equals(Color.RED));

        try {
            prov.getCategoria().setNome("Nome");
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail();
        }

        Assert.assertTrue(prov.getCategoria().getNome().equals("Nome"));
    }

    @Test
    public void testaEquals() {
        Assert.assertEquals("Provento - Descricao: dinheiro, Data: " + Calendar.getInstance().get(Calendar.DAY_OF_MONTH) + "/03/2014, Valor: 2.5, Categoria: teste, Recorrencia: 365", prov.toString());
    }
}
