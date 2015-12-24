package br.com.ac.surpresa;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import br.com.ac.surpresa.dominio.Resultado;

public class Surpresa {

public Random random = new Random();
	
	public void setSeed(long seed){
		this.random = new Random(seed);
	}
	public int rodaAroda(){
		return random.nextInt(60);
	}

	/**
	 * Gera txt e lista a partir do html da cx
	 * @return
	 */
	public List<List<Integer>> geraFromHtml() {
		String fileName = "/resource/D_MEGA.HTM";
		String filePath = new File("").getAbsolutePath();
		
		String line = null;
		FileReader fileReader = null;
		BufferedReader bufferedReader = null;

		Pattern regexpDt = Pattern.compile("\\d{2}\\/\\d{2}\\/\\d{4}");
	    Matcher matcherDt = regexpDt.matcher("");
	    
	    Pattern regexpNum = Pattern.compile(">(\\d{2})<");
	    Matcher matcherNum = regexpNum.matcher("");
		
	    List<List<Integer>> todos = new ArrayList<List<Integer>>();
	    
		int counter = 0;
		int credit = 0;
		int concurso = 0;
		try {
			FileWriter outfile = new FileWriter(filePath+"/resource/result.txt");
	        BufferedWriter output = new BufferedWriter(outfile); 
	        
			// FileReader reads text files in the default encoding.
			fileReader = new FileReader(filePath+fileName);

			// Always wrap FileReader in BufferedReader.
			bufferedReader = new BufferedReader(fileReader);

			while ((line = bufferedReader.readLine()) != null){// && counter < 110) {
				counter++;
				// print out the lines above that are found in the text
				matcherDt.reset(line);
					
				if(credit > 0){
					matcherNum.reset(line);
					//System.out.println(line);
					if(matcherNum.find()){
						output.write(matcherNum.group(1)+" ");
						todos.get(concurso).add(Integer.valueOf(matcherNum.group(1)));
					}
					if(credit == 1){
						output.newLine();
						concurso++;
					}
					credit--;
				}
				if(matcherDt.find()){
					credit = 12;
					todos.add(new ArrayList<Integer>());
				}
			}
			bufferedReader.close();
			fileReader.close();
			output.close();
			outfile.close();
		} catch (FileNotFoundException ex) {
			System.out.println("Unable to open file '" + fileName + "'");
		} catch (IOException ex) {
			System.out.println("Error reading file '" + fileName + "'");
			// Or we could just do this:
			// ex.printStackTrace();
		}

		return todos;
	}
	
	/**
	 * Gera array a partir do txt obtido do geraArray	
	 * @return
	 */
	public List<List<Integer>> geraFromResource() {
		String fileName = "/resource/result.txt";
		String filePath = new File("").getAbsolutePath();
		
		String line = null;
		FileReader fileReader = null;
		BufferedReader bufferedReader = null;

		Pattern regexpDt = Pattern.compile("(\\d{2}) (\\d{2}) (\\d{2}) (\\d{2}) (\\d{2}) (\\d{2})");
	    Matcher matcherDt = regexpDt.matcher("");
	    
	    List<List<Integer>> todos = new ArrayList<List<Integer>>();
	    
		int counter = 0;
		try {
	        
			// FileReader reads text files in the default encoding.
			fileReader = new FileReader(filePath+fileName);

			// Always wrap FileReader in BufferedReader.
			bufferedReader = new BufferedReader(fileReader);

			while ((line = bufferedReader.readLine()) != null){// && counter < 110) {
				// print out the lines above that are found in the text
				matcherDt.reset(line);
				if(matcherDt.find()){
					todos.add(new ArrayList<Integer>());
					todos.get(counter).add(Integer.valueOf(matcherDt.group(1)));
					todos.get(counter).add(Integer.valueOf(matcherDt.group(2)));
					todos.get(counter).add(Integer.valueOf(matcherDt.group(3)));
					todos.get(counter).add(Integer.valueOf(matcherDt.group(4)));
					todos.get(counter).add(Integer.valueOf(matcherDt.group(5)));
					todos.get(counter).add(Integer.valueOf(matcherDt.group(6)));
					
				}
				counter++;
			}
			bufferedReader.close();
			fileReader.close();
		} catch (FileNotFoundException ex) {
			System.out.println("Unable to open file '" + fileName + "'");
		} catch (IOException ex) {
			System.out.println("Error reading file '" + fileName + "'");
			// Or we could just do this:
			// ex.printStackTrace();
		}

		return todos;
	}
	
	/**
	 * 
	 * @param med media
	 * @param dep desv padrao
	 * @param qnt numero de geracoes
	 * @param map *opt mapa com numero calculado de ocorrencias por num
	 * @param occurs *opt lista com quantidades de occorrencias desejadas: 1 maior ocorrencia, 2 media maior, 3 media menor, 4 menor
	 * @param printLog *opt imprime med e desvp das tentativas
	 * @return
	 */
	public List<List<Integer>> littleSurprise(double med, double dep, int qnt, Map<Integer, Integer> map, int[] occurs, boolean printLog, boolean quantidadePorMediana) {
		
		List<List<Integer>> lifi = new ArrayList<List<Integer>>();
		
		Integer tentativas = 0;
		boolean checkout = false;
		
		for (int i = 0; i < qnt; i++) {
			lifi.add(new ArrayList<Integer>());
			do {
				List<Integer> seq = new ArrayList<Integer>();
				do {
					Integer num = rodaAroda();
					if (!seq.contains(num) && num > 0) {
						seq.add(num);
					}

				} while (seq.size() < 6);
				if(seq.size() == 6){
					boolean medVale = false;
					boolean depVale = false;
					Double calcmed = media(seq);
					Double calcdep = desv(seq, calcmed);
					Double rangemdw = med-0.5d;
					Double rangemup = rangemdw + 0.5d;
					Double rangeddw = dep-0.5d;
					Double rangedup = rangeddw + 0.5d;
					
					if(rangemdw.compareTo(calcmed) < 0 && calcmed.compareTo(rangemup) < 0){
						medVale = true;
					}
					if(rangeddw.compareTo(calcdep) < 0 && calcdep.compareTo(rangedup) < 0){
						depVale = true;
					}
					
					if(occurs != null && map == null){
						System.out.println("Mapa nao preenchido!!");
						return null;
					}
					
					if(( med == 0 && dep == 0 || (medVale && depVale)) && !containsSeq(lifi, seq) && (occurs == null || analiseQuantidadeMaiorMenorOcorrencia(map, seq,occurs, quantidadePorMediana) )){
						lifi.get(i).addAll(seq);
						if(printLog){
							System.out.print("med: "+calcmed);
							System.out.print(" devp: "+calcdep);
							System.out.println();
						}
					}
					tentativas++;
					if(tentativas > 10000000){//max 2bi
						System.out.println("Após 10 mi de tentativas não foi possível gerar todos "+qnt+" numeros com essas características...");
						if(lifi.size() > 0){
							printResult(lifi);
						}
						//System.out.println("Program ended!");
						//System.exit(0);
						//break;
						checkout = true;
						break;
					}
				}
				if(checkout) break;
				
			} while (lifi.get(i).size() < 6);
		}
		printResult(lifi);
		return lifi;
	}
	
	public void printResult(List<List<Integer>> lifi){
		for (int i = 0; i < lifi.size(); i++) {
			Collections.sort(lifi.get(i));
			for (int j = 0; j < 6; j++) {
				if(lifi.get(i) != null && !lifi.get(i).isEmpty()){
					System.out.print(lifi.get(i).get(j) + ",");
				}
			}
			System.out.println();
		}
		System.out.println("gerados: "+lifi.size());
	}
	
	public void printResultMediaDesvp(List<List<Double>> lifi, boolean showNumConcurso){
		for (int i = 0; i < lifi.size(); i++) {
			if(lifi.get(i) != null && !lifi.get(i).isEmpty()){
				String conc = "";
				if(showNumConcurso){
					conc = String.valueOf(i+1);
				}
				System.out.print(conc+" "+lifi.get(i).get(0) + " "+lifi.get(i).get(1));
			}
			System.out.println();
		}
	}
	
	public List<List<Double>> mapaMediaDesvPadrao(List<List<Integer>> jogs){
		List<List<Double>> mapaMediaDesvio = new ArrayList<List<Double>>();
		for (int i = 0; i < jogs.size(); i++) {
			List<Double> lista = new ArrayList<Double>();
			Double media  = media(jogs.get(i));
			lista.add(media);
			lista.add(desv(jogs.get(i),media));
			mapaMediaDesvio.add(lista);
			
		}
		return mapaMediaDesvio;
	}
	
	public boolean containsSeq(List<List<Integer>>lista, List<Integer> seq){
		boolean found = false;
		for (List<Integer> jogo : lista) {
			if(jogo.containsAll(seq)){
				found = true;
			}
		}
		return found;
	}
	
	public Resultado containsSeqResult(List<List<Integer>>lista, List<Integer> seq){
		Resultado resultado = new Resultado();
		int i = 1;
		for (List<Integer> jogo : lista) {
			if(jogo.containsAll(seq)){
				resultado.setFound(true);
				resultado.setLot(jogo);
				resultado.setGen(seq);
				System.out.println("Posicao :"+i);
			}
			i++;
		}
		return resultado;
	}
	
	
	public double media(List<Integer> seq) {
		int sum = 0;
		for (Integer num : seq) {
			sum = sum + num;
		}
		return ((double) sum / 6);
	}

	public double desv(List<Integer> seq, double med) {
		double sum = 0;
		for (Integer num : seq) {
			double d = Math.abs((double) num - med);
			sum = sum + Math.pow(d, 2);
		}
		return Math.sqrt(sum / 5);
	}
	/**
	 * numero de sequencias em uma lista
	 */
	public void analiseSequenciais(List<List<Integer>> lista, int numeroSequencias){
		int i = 1;
		int contador = 0;
		for (List<Integer> gam : lista) {
			int seqCont = 0;
			for (Integer n : gam) {
				for (Integer m : gam) {
					if(n-m == 1){
						seqCont++;
					}
				}
				if(seqCont == numeroSequencias){
					contador++;
					System.out.println("No ["+i+"] tem ["+seqCont+"] repets.");
				}
			}
			i++;
		}
		System.out.println("Sao ["+contador+"] com ["+numeroSequencias+"] sequenciais");
	}
	
	public Map<Integer, Integer> analiseOrdemAparicoes(List<List<Integer>> sorteados, boolean printStats){
		Map<Integer, Integer> unidades = new HashMap<Integer, Integer>();
		for (int i = 1; i < 61; i++) {
			unidades.put(i, 0);
		}
		for (Integer num : unidades.keySet()) {
			int ap = 0;
			for (List<Integer> gam : sorteados) {
				if(gam.contains(num)){
					ap++;
				}
			}
			unidades.put(num, ap);
			//System.out.println("O numero ["+num+"] saiu ["+unidades.get(num)+"] vezes.");
		}
		
		//System.out.println("Ordenando...");
		
		List<Integer> result = new ArrayList<Integer>(); 
		LinkedHashMap<Integer, Integer> resultMap = new LinkedHashMap<Integer, Integer>();
		
		Integer menorKey = 1;
		List<Integer> winnerKeys = new ArrayList<Integer>();
		Integer menorValor = 99999;
		
		for (int i = 1; i <=60 ; i++) {
			menorValor = 999999;
			for (int g = 1; g <=60 ; g++) {
				if(unidades.get(g) <= menorValor && !winnerKeys.contains(g)){
					menorValor = unidades.get(g);
					menorKey = g;
				}
			}
			winnerKeys.add(menorKey);
			result.add(menorKey);
		}
		
		for (Integer numero : result) {
			if(printStats)System.out.println("O numero ["+numero+"] saiu ["+unidades.get(numero)+"] vezes.");
			resultMap.put(numero, unidades.get(numero));
		}
		
		return resultMap;
		
	}
	
	/**
	 * Busca o historico das ordens de aparicao 
	 * @param resultados
	 * @param classesPorMediana calcular classes por mediana o media
	 */
	public List<int[]> getHistoricoOcorrencias(List<List<Integer>> resultados, boolean classesPorMediana){
		List<int[]> listaTiposOcorrencia = new ArrayList<int[]>();
		for(int i = 1; i < resultados.size(); i++){
			List<List<Integer>> parcial = resultados.subList(0, i -1);
			int[] a = {0,0,0,0};
			if(i > 100){
				Map<Integer, Integer> mapaParcial = analiseOrdemAparicoes(parcial, false);
				a = quantidadeMaioMenorOcorrencia(mapaParcial, resultados.get(i), classesPorMediana);
			}
			listaTiposOcorrencia.add(a);
		}
		return listaTiposOcorrencia;
		
	}
	
	/**
	 * Imprime o historico das ordens de aparicao a partir do 1k
	 * @param resultados
	 */
	public void printHistoricoOcorrencias(List<int[]> resultados, int concInicial, boolean printNumConc){
		
		for(int i = concInicial - 1; i < resultados.size(); i++){
			//System.out.println(i+" :: "+a[0]+" - "+a[1]+" - "+a[2]+" - "+a[3]);
			String numConc = "";
			if(printNumConc){
				numConc = String.valueOf(i+1);
				numConc = "Concurso n "+numConc+" ";
			}
			System.out.println(numConc+resultados.get(i)[0]+" - "+resultados.get(i)[1]+" - "+resultados.get(i)[2]+" - "+resultados.get(i)[3]);
		}
		
	}
	
	/**
	 * 
	 * @param mapa mapa calculado de quantidade de ocorrencia por numeros
	 * @param jog lista de jogos
	 * @param tipos tipos de ocorrencia por classe desejados {0,0,0,0}
	 * @param mediana calcular classes pela mediana das aparicoes ou pela media
	 * @return
	 */
	public Boolean analiseQuantidadeMaiorMenorOcorrencia(Map<Integer, Integer> mapa, List<Integer> jog, int[] tipos, boolean mediana){
		
		if(tipos.length != 4){
			System.out.println("Array de quantitativos deve ter 4 posicoes(Mais, medioMax, MedioMin, menos occorridos ).");
			return null;
		}
		if((tipos[0]+tipos[1]+tipos[2]+tipos[3]) != 6){
			System.out.println("Array de quantitativos deve ter 6 elementos ao todo.");
			return null;
		}
		
		int[] relOcorr = quantidadeMaioMenorOcorrencia(mapa, jog, mediana);
		
		return relOcorr[0] == tipos[0] && relOcorr[1] == tipos[1] && relOcorr[2] == tipos[2] && relOcorr[3] == tipos[3];
		
	}
	
	/**
	 * 
	 * @param mapa mapa de quantidades
	 * @param jog array de jogos
	 * @param mediana calculo de classes de aparicao por mediana ou por media
	 * @return
	 */
	public int[] quantidadeMaioMenorOcorrencia(Map<Integer, Integer> mapa,
			List<Integer> jog, boolean mediana) {
		
		Iterator<Integer> ite = mapa.keySet().iterator(); 
		Integer menorValor = mapa.get((Integer)ite.next());
		Integer maiorValor = 0;
		Integer meio = 0;
		Integer umQuarto = 0;
		Integer tresQuarto = 0;
		
		if(mediana){
			while (ite.hasNext()) {
				maiorValor = mapa.get((Integer) ite.next());
			}
			
			meio = (Integer)((maiorValor + menorValor)/2);
			umQuarto = (Integer)((meio + menorValor)/2);
			tresQuarto = (Integer)((maiorValor + meio)/2);
		}else{
			int posicao = 1;
			while (ite.hasNext()) {
				posicao++;
				if(posicao == 14){
					umQuarto = mapa.get((Integer) ite.next());
				}
				if(posicao == 29){
					meio = mapa.get((Integer) ite.next());
				}
				if(posicao == 44){
					tresQuarto = mapa.get((Integer) ite.next());
				}
				maiorValor = mapa.get((Integer) ite.next());
			}
		}
		
		int[] relOcorr = {0,0,0,0};
		for (Integer num : jog) {
			Integer saiu = mapa.get(num);
			if(saiu >= tresQuarto){
				relOcorr[0]++;
			}else if(saiu < tresQuarto && saiu >= meio){
				relOcorr[1]++;
			}else if(saiu >= umQuarto && saiu < meio){
				relOcorr[2]++;
			}else if(saiu < umQuarto){
				relOcorr[3]++;
			}
		}
		return relOcorr;
	}
	
	public void testarGerados(List<List<Integer>> sorteados, List<List<Integer>> gerados){
		boolean none = true;
		for (List<Integer> gam : gerados) {
			Resultado res = containsSeqResult(sorteados, gam);
			if(res.getFound()){
				System.out.println("Got it miserable!!!");
				System.out.println(res.getLot());
				System.out.println(res.getGen());
				none = false;
			}
		}
		if(none){
			System.out.println("Agua!!!");
		}
	}
	
	public void printValoresQuantidadesOcorrencias(Map<Integer, Integer> mapa, boolean mediana){
		Iterator<Integer> ite = mapa.keySet().iterator(); 
		Integer menorValor = mapa.get((Integer)ite.next());
		Integer maiorValor = 0;
		Integer meio = 0;
		Integer umQuarto = 0;
		Integer tresQuarto = 0;
		if(mediana){
			while (ite.hasNext()) {
				maiorValor = mapa.get((Integer) ite.next());
			}
			
			meio = (Integer)((maiorValor + menorValor)/2);
			umQuarto = (Integer)((meio + menorValor)/2);
			tresQuarto = (Integer)((maiorValor + meio)/2);
		}else{
			int posicao = 1;
			while (ite.hasNext()) {
				posicao++;
				if(posicao == 14){
					umQuarto = mapa.get((Integer) ite.next());
				}
				if(posicao == 29){
					meio = mapa.get((Integer) ite.next());
				}
				if(posicao == 44){
					tresQuarto = mapa.get((Integer) ite.next());
				}
				maiorValor = mapa.get((Integer) ite.next());
			}
		}
		System.out.println("Classes de ordem sao: Maior:"+maiorValor+" Meio maior: "+tresQuarto+" Meio : "+meio+" Meio menor: "+umQuarto+" Menor: "+menorValor);
	}
	
}
