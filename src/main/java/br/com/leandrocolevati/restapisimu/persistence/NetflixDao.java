package br.com.leandrocolevati.restapisimu.persistence;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import br.com.leandrocolevati.restapisimu.model.Netflix;

@Component
public class NetflixDao {

	private URL datasetDirUrl;
	private File datasetDir;
	private File datasetFile;

	public NetflixDao() {
		this.datasetDirUrl = this.getClass().getClassLoader().getResource("/dataset");
		try {
			this.datasetDir = new File(datasetDirUrl.toURI());
		} catch (URISyntaxException e) {
			this.datasetDir = new File(datasetDirUrl.getPath());
		}
		this.datasetFile = this.datasetDir.listFiles()[0];
	}
	
	public Netflix getNetflix(int id) throws IOException {
		String[] vetDataset = openDataset().split("\r");
		Netflix n = new Netflix();
		for (String s : vetDataset) {
			String[] vetLinha = s.split(";");
			int idLinha = Integer.parseInt(vetLinha[0]);
			if (id == idLinha) {
				n = linhaToNetflix(s);
				break;
			}
		}
		
		return n;
	}
	
	public List<Netflix> getNetflixes() throws IOException {
		List<Netflix> lista = new ArrayList<Netflix>();
		String ds = openDataset(); 
		String[] vetDataset = ds.split("\r");
		
		for (String s : vetDataset) {
			Netflix n = linhaToNetflix(s);
			lista.add(n);
		}
		
		return lista;
	}
	
	public boolean insertNetflix(Netflix n) {
		String linha = netflixToLinha(n);
		try {
			adicionaLinhaDataset(linha);
			return true;
		} catch (IOException e) {
			return false;
		}
	}
	
	private void adicionaLinhaDataset(String linha) throws IOException {
		FileWriter fw = new FileWriter(datasetFile, true);
		PrintWriter pw = new PrintWriter(fw);
		pw.write(linha);
		pw.flush();
		pw.close();
		fw.close();
	}

	private String netflixToLinha(Netflix n) {
		StringBuffer sb = new StringBuffer();
		sb.append(n.getId());
		sb.append(";");
		sb.append(n.getGenre());
		sb.append(";");
		sb.append(n.getTitle());
		sb.append(";");
		sb.append(n.getSubgenre());
		sb.append(";");
		sb.append("--");
		sb.append(";");
		sb.append(n.getPremiereYear());
		sb.append(";");
		sb.append("--");
		sb.append(";");
		sb.append("--");
		sb.append(";");
		sb.append("--");
		sb.append(";");
		sb.append("--");
		sb.append(";");
		sb.append("--");
		sb.append(";");
		sb.append("--");
		sb.append(";");
		sb.append(n.getImdbRating());		
		sb.append("\r");
		
		return sb.toString();
	}

	private Netflix linhaToNetflix(String linha) {
		Netflix n = new Netflix();
		String[] vetLinha = linha.split(";");
		
		n.setId(Integer.parseInt(vetLinha[0]));
		n.setGenre(vetLinha[1]);
		n.setTitle(vetLinha[2]);
		n.setSubgenre(vetLinha[3]);
		n.setPremiereYear(Integer.parseInt(vetLinha[5]));
		n.setImdbRating(Integer.parseInt(vetLinha[12]));
		
		return n;
	}

	public String openDataset() throws IOException {
		FileInputStream fis = new FileInputStream(datasetFile);
		InputStreamReader isr = new InputStreamReader(fis);
		BufferedReader br = new BufferedReader(isr);
		StringBuffer sb = new StringBuffer();
		String linha = br.readLine();
		linha = br.readLine();
		
		while (linha != null) {
			sb.append(linha);
			sb.append("\r");
			
			linha = br.readLine();
		}
		br.close();
		isr.close();
		fis.close();
		
		return sb.toString();
	}
	
	
	
}
