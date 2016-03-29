package calc.datastructures.core;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import calc.interfacegrafica.Informativo;

public class ManipuladorXML {

	private static List<String> atributosEspecificos = Arrays.asList("TITULO-DO-TRABALHO", "NOME-COMPLETO-AUTOR", "NRO-ID-CNPQ");

	private static String nomeAutor = "NOME-COMPLETO";
	private static String idAutor = "NUMERO-IDENTIFICADOR";
	private static String idCnpq = "NRO-ID-CNPQ";
	private static String titulo = "TITULO-DO-TRABALHO";
	private static String ano = "ANO-DO-TRABALHO";
	

	static List<Documento> listaDeDocumentos = new ArrayList<Documento>();

	private static List<Pessoa> listaDePessoas = new ArrayList<Pessoa>();
	private static List<Publicacao> listaDePublicacao = new ArrayList<Publicacao>();
	private static Pessoa autor;

	public static List<Documento> geraListaDeDocumentosComTermosDeTagsEspecificas(List<File> listaDeCurriculoXML) {
		try {

			for (File curriculo : listaDeCurriculoXML) {
				listaPessoas(listaDeCurriculoXML);
				if (curriculo.isDirectory())
					continue;
				Documento documentoIndexado = new Documento(curriculo.getName());
				Informativo.geraInfo("Lendo arquivo " + curriculo.getName());
				Document document = criaDocument(curriculo);
				Node nodoPai = document.getFirstChild();
				NodeList filhosDiretos = nodoPai.getChildNodes();
				setId(nodoPai.getAttributes());
				setNome(filhosDiretos);	
				setPublicacoes(filhosDiretos);
		

				StringBuilder valoresDosAtributos = new StringBuilder();
				List<String> listaAreaDeConhecimento = new ArrayList<String>();
				for (int i = 0; i < filhosDiretos.getLength(); i++) {
					Node filhoDireto = filhosDiretos.item(i);
					if (filhoDireto.getNodeType() == Node.ELEMENT_NODE) {
						obtemValoresDosAtributosEspecificos(filhoDireto, valoresDosAtributos, listaAreaDeConhecimento);
						if (filhoDireto.getChildNodes().getLength() > 0) {
							obtemValoresDosAtributosEspecificosDosFilhos(filhoDireto.getChildNodes(),
									valoresDosAtributos, listaAreaDeConhecimento);
						}
					}
				}
				documentoIndexado.setValoresDosAtributos(valoresDosAtributos);
				documentoIndexado.setListaAreaDeConhecimento(listaAreaDeConhecimento);
				String a = "";
				for (int i = 0; i < listaAreaDeConhecimento.size(); i++) {
					a = a + listaAreaDeConhecimento.get(i);
				}
				System.out.println(a);
				listaDeDocumentos.add(documentoIndexado);
			}
			return listaDeDocumentos;

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private static void listaPessoas(List<File> listaDeCurriculoXML) {
		for (File curriculo : listaDeCurriculoXML) {
			autor = new Pessoa();
			if (curriculo.isDirectory())
				continue;
			Informativo.geraInfo("Lendo arquivo " + curriculo.getName());
			Document document = criaDocument(curriculo);
			Node nodoPai = document.getFirstChild();
			NamedNodeMap atributosPai = nodoPai.getAttributes();

			setId(atributosPai);
			NodeList filhosDiretos = nodoPai.getChildNodes();
			setNome(filhosDiretos);
			
			listaDePessoas.add(autor);
			//todo 
		}
	}
	
	private static void setPublicacoes(NodeList filhosDiretos) {
		Publicacao publicacao = new Publicacao();
		
		for (int i = 0; i < filhosDiretos.getLength(); i++) {
			Node filhoDireto = filhosDiretos.item(i);
			if (filhoDireto.getNodeType() == Node.ELEMENT_NODE) {
				NamedNodeMap atributos = filhoDireto.getAttributes();

				for (int j = 0; j < atributos.getLength(); j++) {
					Attr atributo = (Attr) atributos.item(j);
					if (atributo.getNodeName().equals(titulo)) {
						publicacao.setTitulo(atributo.getValue());
					}
					if (atributo.getNodeName().equals(ano)) {
						publicacao.setAno(atributo.getValue());
						if(!verificaPublicacao(publicacao)){
							publicacao.setAutor(autor);
							listaDePublicacao.add(publicacao);
						}else {
							
							publicacao.setAutor(autor);
						}
						//verifica se publicacao ja existe
						//se nao existir, criar uma publicacao e seta esse autor  autor
						//senao quer dizerque já existe entao seta o autor atual  autor
						autor.getPublicacoes().add(publicacao);
					}

				}
			} 
			if (filhoDireto.getChildNodes().getLength() > 0) {
				setPublicacoes(filhoDireto.getChildNodes());
			}

		}

	}
	
	private static boolean verificaPublicacao(Publicacao p){
		for (Publicacao p1 : listaDePublicacao) {
			if(p1.equals(p)){
				return true;
			}
		}
		return false;
	}


	private static void setNome(NodeList filhosDiretos) {
		for (int i = 0; i < filhosDiretos.getLength(); i++) {
			Node filhoDireto = filhosDiretos.item(i);
			if (filhoDireto.getNodeType() == Node.ELEMENT_NODE) {
				NamedNodeMap atributos = filhoDireto.getAttributes();

				for (int j = 0; j < atributos.getLength(); j++) {
					Attr atributo = (Attr) atributos.item(j);
					if (atributo.getNodeName().equals(nomeAutor)) {
						autor.setNome(atributo.getValue());
					}
				}
			}

		}
	}

	private static void setId(NamedNodeMap atributosPai) {
		for (int k = 0; k < atributosPai.getLength(); k++) {
			Attr atributoPai = (Attr) atributosPai.item(k);
			if (atributoPai.getNodeName().equals(idAutor)) {
				autor.setId(atributoPai.getValue());
			}
		}
	}

	private static void obtemValoresDosAtributosEspecificosDosFilhos(NodeList filhos, StringBuilder valoresDosAtributos,
			List<String> listaAreaDeConhecimento) {
		for (int i = 0; i < filhos.getLength(); i++) {
			Node filho = filhos.item(i);
			if (filho.getNodeType() != Node.ELEMENT_NODE) {
				continue;
			}
			if (filho != null) {
				obtemValoresDosAtributosEspecificos(filho, valoresDosAtributos, listaAreaDeConhecimento);
				if (filho.getChildNodes().getLength() > 0) {
					obtemValoresDosAtributosEspecificosDosFilhos(filho.getChildNodes(), valoresDosAtributos,
							listaAreaDeConhecimento);
				}
			}
		}
	}

	private static StringBuilder obtemValoresDosAtributosEspecificos(Node nodo, StringBuilder valorDoField,
			List<String> listaAreaDeConhecimento) {
		NamedNodeMap atributos = nodo.getAttributes();
		if (atributos == null || nodo.getNodeType() != Node.ELEMENT_NODE) {
			return valorDoField;
		}
		for (int i = 0; i < atributos.getLength(); i++) {
			Attr atributo = (Attr) atributos.item(i);

			if (atributo.getNodeName().equals(idCnpq) && !atributo.getValue().equals("")) {

				if (atributo.getOwnerElement().getParentNode().getParentNode().getParentNode().getNodeName()
						.equals("PRODUCAO-BIBLIOGRAFICA")) {
					for (int j = 0; j < listaDePessoas.size(); j++) {
						if (listaDePessoas.get(j).getId().trim().equals(atributo.getValue())) {
							boolean naoContem = !listaAreaDeConhecimento
									.contains("[" + autor.getNome() + " ; " + listaDePessoas.get(j).getNome() + "]");
							boolean naoEhOAutor = !autor.getNome().equals(listaDePessoas.get(j).getNome());
							if (naoContem && naoEhOAutor) {
								listaAreaDeConhecimento
										.add("[" + autor.getNome() + " ; " + listaDePessoas.get(j).getNome() + "]");
							}
						}
					}
				}

			}
			for (String atributoEspecifico : atributosEspecificos) {
				if (!atributo.getNodeName().contains(atributoEspecifico))
					continue;
				if (atributo.getNodeName().contains("-INGLES") || atributo.getNodeName().contains("-EN"))
					continue;
				String valorDoAtributo = atributo.getValue();
				valorDoField.append(valorDoAtributo + " ");
			}

		}
		return valorDoField;

	}

	private static Document criaDocument(File curriculo) {
		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder documentBuilder;
		Document document = null;
		try {
			documentBuilder = documentBuilderFactory.newDocumentBuilder();
			document = documentBuilder.parse(curriculo);
			document.getDocumentElement().normalize();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
			throw new RuntimeException("Erro na leitura dos curr�culos");
		} catch (SAXException e) {
			e.printStackTrace();
			throw new RuntimeException("Erro na leitura dos curr�culos");
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("Erro na leitura dos curr�culos");
		}
		return document;
	}
}