package br.com.ac.exec;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import br.com.ac.surpresa.Surpresa;


public class GeraNumeros {
	
	public static void main(String[ ] args) {
		
		if(args == null || args.length != 7){
			System.out.println("Erro no input.");
			System.exit(0);
		}
		
		if(args[0] == "help"){
			System.out.println("Informe com espacos os valores:");
			System.out.println("Numero de jogos a serem gerados.");
			System.out.println("Media ou N.");
			System.out.println("Desvio padrao ou N.");
			System.out.println("Maior ocorrencia 0-6");
			System.out.println("Media maior ocorrencia 0-6");
			System.out.println("Media menor ocorrencia 0-6");
			System.out.println("Menor ocorrencia 0-6");
			System.out.println("OBS: soma das ocorrencias deve ser 6");
		}
		boolean semMedia = false;
		
		if(args[0] == "N" || args[0] == "n"){
			semMedia = true;
		}
		
		Surpresa surpresa = new Surpresa();

		List<List<Integer>> tuto = new ArrayList<List<Integer>>();
		List<List<Integer>> pot = new ArrayList<List<Integer>>();
		
		surpresa.setSeed(1618033l*System.currentTimeMillis());
		tuto = surpresa.geraFromResource();
		
		Map<Integer, Integer> mapaOcorrencias = surpresa.analiseOrdemAparicoes(tuto, true);
		surpresa.printValoresQuantidadesOcorrencias(mapaOcorrencias);
		
		int mr = 0;
		int mmr = 0;
		int mnr = 0;
		int mn = 0;
		try {
			mr = Integer.valueOf(args[3]).intValue();
			mmr = Integer.valueOf(args[4]).intValue();
			mnr = Integer.valueOf(args[5]).intValue();
			mn = Integer.valueOf(args[6]).intValue();
			if(mr+mmr+mnr+mn != 6){
				System.out.println("Numero de ocorrencias diferente de 6.");
				throw new Exception();
			}
		} catch (Exception e) {
			System.out.println("Erro no input.");
		}
		
		
		int[] ocorrencias = {mr,mmr,mnr,mn};// 4 posicoes , de mais ocorridos a menos com quantidade p cada
		int quantidade  = Integer.valueOf(args[0]).intValue();
		if(semMedia){
			pot = surpresa.littleSurprise(0, 0, quantidade, mapaOcorrencias, ocorrencias, false);
		}else{
			double media = Double.valueOf(args[1]).doubleValue();
			double desvio = Double.valueOf(args[2]).doubleValue();
			pot = surpresa.littleSurprise(media, desvio, quantidade, mapaOcorrencias, ocorrencias, false);
		}
		surpresa.testarGerados(tuto, pot);
		System.out.println("Program ended!");
		
		
	}

}
