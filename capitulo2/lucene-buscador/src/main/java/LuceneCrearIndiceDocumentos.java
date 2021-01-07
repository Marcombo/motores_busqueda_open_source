import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.core.WhitespaceAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

public class LuceneCrearIndiceDocumentos {

	public static void main(String[] args) throws Exception {

		Path path = Paths.get("/home/linux/documentos");
		Directory directorio = FSDirectory.open(path);

		Analyzer analyzer = new WhitespaceAnalyzer();
		IndexWriterConfig config = new IndexWriterConfig(analyzer);
		IndexWriter indexWriter = new IndexWriter(directorio, config);

		// Documento Lucene
		Document documentoLucene = new Document();
		String textoLucene = "Lucene is a Search Engine and  Information Retrieval "
				+ "library written in Java language";
		documentoLucene.add(new TextField("texto", textoLucene, Field.Store.YES));
		documentoLucene.add(new TextField("nombre", "Lucene", Field.Store.YES));
		indexWriter.addDocument(documentoLucene);

		// Documento Java
		Document documentoJava = new Document();
		String textoJava = "La plataforma Java es el nombre de un entorno o plataforma "
				+ "de computaci�n originaria de Sun Microsystems, capaz de ejecutar "
				+ "aplicaciones desarrolladas usando el lenguaje de programaci�n "
				+ "Java u otros lenguajes que compilen a bytecode y un conjunto de " + "herramientas de desarrollo";
		documentoJava.add(new TextField("texto", textoJava, Field.Store.YES));
		documentoJava.add(new TextField("nombre", "Java", Field.Store.YES));
		indexWriter.addDocument(documentoJava);

		// Documento Python
		Document documentoPython = new Document();
		String textoPython = "Python es un lenguaje de programaci�n interpretado "
				+ "cuya filosof�a hace hincapi� en la legibilidad de su c�digo. "
				+ "Se trata de un lenguaje de programaci�n multiparadigma, ya que "
				+ "soporta orientaci�n a objetos, programaci�n imperativa y, en menor "
				+ "medida, programaci�n funcional.";
		documentoPython.add(new TextField("texto", textoPython, Field.Store.YES));
		documentoPython.add(new TextField("nombre", "Python", Field.Store.YES));
		indexWriter.addDocument(documentoPython);

		// Documento PHP
		Document documentoPHP = new Document();
		String textoPHP = "PHP, acr�nimo recursivo en ingl�s de PHP: Hypertext Preprocessor, "
				+ "es un lenguaje de programaci�n de prop�sito general de c�digo del lado "
				+ "del servidor originalmente dise�ado para el preprocesado de texto plano en UTF-8";
		documentoPHP.add(new TextField("texto", textoPHP, Field.Store.YES));
		documentoPHP.add(new TextField("nombre", "PHP", Field.Store.YES));
		indexWriter.addDocument(documentoPHP);

		// Documento GOLANG
		Document documentoGolang = new Document();
		String textoGolang = "Go es un lenguaje de programación concurrente y compilado "
				+ "inspirado en la sintaxis de C, que intenta ser dinámico como Python y "
				+ "con el rendimiento de C o C++. Ha sido desarrollado por Google, y "
				+ "sus diseñadores iniciales fueron Robert Griesemer, Rob Pike y Ken Thompson.";
		documentoGolang.add(new TextField("texto", textoGolang, Field.Store.YES));
		documentoGolang.add(new TextField("nombre", "Golang", Field.Store.YES));
		indexWriter.addDocument(documentoGolang);

		System.out.println("El numero de documentos indexados es " + indexWriter.numRamDocs());

		indexWriter.close();
	}

}
