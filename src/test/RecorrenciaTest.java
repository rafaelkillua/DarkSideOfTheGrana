package test;

import models.Recorrencia;

import org.junit.Assert;
import org.junit.Test;

public class RecorrenciaTest {

	@Test
	public void testGetValor() {
		Assert.assertEquals("Mesagem de erro", 0, Recorrencia.NENHUMA.getValor());
		Assert.assertEquals("Mesagem de erro", 365, Recorrencia.ANUAL.getValor());
		Assert.assertEquals("Mesagem de erro", 7, Recorrencia.SEMANAL.getValor());
		
		Assert.assertNotEquals("Mesagem de erro", 90, Recorrencia.BIMESTRAL.getValor());
		Assert.assertNotEquals("Mesagem de erro", 30, Recorrencia.DIARIO.getValor());
		Assert.assertNotEquals("Mesagem de erro", 0, Recorrencia.MENSAL.getValor());
	}

	@Test
	public void testToString() {
		Assert.assertEquals("Mesagem de erro", "Nenhuma", Recorrencia.NENHUMA.toString());
		Assert.assertEquals("Mesagem de erro", "Anual", Recorrencia.ANUAL.toString());
		Assert.assertEquals("Mesagem de erro", "Semanal", Recorrencia.SEMANAL.toString());
	}

}
