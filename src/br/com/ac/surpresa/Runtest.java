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
		//32 37 44 47 54 60
		
		//print media e desvp
		//List<List<Double>> mediaDesv = new ArrayList<List<Double>>();
	    //mediaDesv = surpresa.mapaMediaDesvPadrao(tuto);
		//surpresa.printResultMediaDesvp(mediaDesv, true);
		
		//Historico de ocorrencias por freq de aparicao
		surpresa.printHistoricoOcorrencias(surpresa.getHistoricoOcorrencias(tuto, false),1000, false);
		
		//analise de numero de sequencias
		//surpresa.analiseSequenciais(pot, 3);
		
		//ocorrencias
		Map<Integer, Integer> mapaOcorrencias = surpresa.analiseOrdemAparicoes(tuto, true);
		surpresa.printValoresQuantidadesOcorrencias(mapaOcorrencias);
		
		
		//int[] ocorrencias = {0,2,3,1};// 4 posicoes , de mais ocorridos a menos com quantidade p cada
		//pot = surpresa.littleSurprise(32.5, 16.5, 50, mapaOcorrencias, ocorrencias, false);
		//surpresa.testarGerados(tuto, pot);
		System.out.println("Program ended!");
	}
	//fimain

}
