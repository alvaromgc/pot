package br.com.ac.surpresa;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class Runtest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
				
		Surpresa surpresa = new Surpresa();

		List<List<Integer>> tuto = new ArrayList<List<Integer>>();
		List<List<Integer>> pot = new ArrayList<List<Integer>>();
		
		surpresa.setSeed(1618033l*System.currentTimeMillis());
		//tuto = surpresa.geraArray();
		tuto = surpresa.geraFromResource();
		
		//faltando no html
		//tuto.add(Arrays.asList(22,23,41,46,53,60));
		//tuto.add(Arrays.asList(16,26,35,39,44,45));
		//tuto.add(Arrays.asList(5,7,11,34,35,50));
		
		
		//Historico de ocorrencias por freq de aparicao
		//surpresa.printHistoricoOcorrencias(surpresa.getHistoricoOcorrencias(tuto, 1000),1000, true);
		
		//analise de numero de sequencias
		//surpresa.analiseSequenciais(pot, 3);
		
		//ocorrencias
		Map<Integer, Integer> mapaOcorrencias = surpresa.analiseOrdemAparicoes(tuto, true);
		surpresa.printValoresQuantidadesOcorrencias(mapaOcorrencias);
		
		
		int[] ocorrencias = {2,3,1,0};// 4 posicoes , de mais ocorridos a menos com quantidade p cada
		pot = surpresa.littleSurprise(31.5, 21, 50, mapaOcorrencias, ocorrencias, false);
		surpresa.testarGerados(tuto, pot);
		System.out.println("Program ended!");
	}
	//fimain

}