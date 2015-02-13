package test;

import java.awt.Color;
import java.util.Calendar;

import models.*;

import org.junit.Assert;
import org.junit.Test;
import org.junit.Before;

public class TestaDespesa {

    Transacao desp;
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

        try{
        	desp = new Despesa("comida", data, cat, 2.50, 365);
        }catch(Exception e){
        	e.printStackTrace();
        	Assert.fail();
        }
    }

    @Test
    public void TestaGets() {
        Assert.assertTrue(desp.getDataDeInsercao().get(Calendar.MONTH) == 2);
        Assert.assertTrue(desp.getDescricao().equals("comida"));
        Assert.assertTrue(desp.getRecorrencia() == 365);
        Assert.assertTrue(desp.getValor() == -2.50);
        Assert.assertTrue(desp.getTipoDaTransacao().equals("Despesa"));
        Assert.assertTrue(desp.getCategoria().getNome().equals("teste"));
        Assert.assertTrue(desp.getCategoria().getCor().equals(Color.BLACK));
    }

    @Test
    public void TestaSets() {

        try {
            cat2 = new Categoria("categoria", Color.GREEN, 20.00);
        } catch (Exception e) {
            Assert.fail();
            e.printStackTrace();
        }

        desp.setCategoria(cat2);
        Assert.assertTrue(cat2.getNome().equals("categoria"));
        Assert.assertTrue(cat2.getCor().equals(Color.GREEN));
        Assert.assertTrue(cat2.getOrcamento() == 20.00);

        desp.setRecorrencia(31);
        Assert.assertTrue(desp.getRecorrencia() == 31);

        desp.setDataDeInsercao(data2);
        Assert.assertTrue(desp.getDataDeInsercao().get(Calendar.MONTH) == 4);
        Assert.assertTrue(desp.getDataDeInsercao().get(Calendar.YEAR) == 2015);

        desp.setDescricao("alimentos");
        Assert.assertTrue(desp.getDescricao().equals("alimentos"));

        try {
			desp.setValor(0);
			Assert.fail("Esperava excecao");
		} catch (Exception e1) {
			Assert.assertEquals("O valor da despesa deve ser diferente de zero.", e1.getMessage());
		}
        
        try {
            desp.setValor(3.50);
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail();
        }
        Assert.assertTrue(desp.getValor() == -3.50);

        try {
            desp.getCategoria().setCor(Color.RED);
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail();
        }

        Assert.assertTrue(desp.getCategoria().getCor().equals(Color.RED));

        try {
            desp.getCategoria().setNome("Nome");
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail();
        }

        Assert.assertTrue(desp.getCategoria().getNome().equals("Nome"));
    }

    @Test
    public void testaEquals() {
        Assert.assertEquals("Despesa - Descricao: comida, Data: " + Calendar.getInstance().get(Calendar.DAY_OF_MONTH) + "/03/2014, Valor: -2.5, Categoria: teste, Recorrencia: 365", desp.toString());
    }
}
