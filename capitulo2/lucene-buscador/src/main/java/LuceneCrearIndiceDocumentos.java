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
				+ "de computación originaria de Sun Microsystems, capaz de ejecutar "
				+ "aplicaciones desarrolladas usando el lenguaje de programación "
				+ "Java u otros lenguajes que compilen a bytecode y un conjunto de " + "herramientas de desarrollo";
		documentoJava.add(new TextField("texto", textoJava, Field.Store.YES));
		documentoJava.add(new TextField("nombre", "Java", Field.Store.YES));
		indexWriter.addDocument(documentoJava);

		// Documento Python
		Document documentoPython = new Document();
		String textoPython = "Python es un lenguaje de programación interpretado "
				+ "cuya filosofía hace hincapié en la legibilidad de su código. "
				+ "Se trata de un lenguaje de programación multiparadigma, ya que "
				+ "soporta orientación a objetos, programación imperativa y, en menor "
				+ "medida, programación funcional.";
		documentoPython.add(new TextField("texto", textoPython, Field.Store.YES));
		documentoPython.add(new TextField("nombre", "Python", Field.Store.YES));
		indexWriter.addDocument(documentoPython);

		// Documento PHP
		Document documentoPHP = new Document();
		String textoPHP = "PHP, acrónimo recursivo en inglés de PHP: Hypertext Preprocessor, "
				+ "es un lenguaje de programación de propósito general de código del lado "
				+ "del servidor originalmente diseñado para el preprocesado de texto plano en UTF-8";
		documentoPHP.add(new TextField("texto", textoPHP, Field.Store.YES));
		documentoPHP.add(new TextField("nombre", "PHP", Field.Store.YES));
		indexWriter.addDocument(documentoPHP);

		// Documento GOLANG
		Document documentoGolang = new Document();
		String textoGolang = "Go es un lenguaje de programaciÃ³n concurrente y compilado "
				+ "inspirado en la sintaxis de C, que intenta ser dinÃ¡mico como Python y "
				+ "con el rendimiento de C o C++. Ha sido desarrollado por Google, y "
				+ "sus diseÃ±adores iniciales fueron Robert Griesemer, Rob Pike y Ken Thompson.";
		documentoGolang.add(new TextField("texto", textoGolang, Field.Store.YES));
		documentoGolang.add(new TextField("nombre", "Golang", Field.Store.YES));
		indexWriter.addDocument(documentoGolang);

		System.out.println("El numero de documentos indexados es " + indexWriter.numRamDocs());

		indexWriter.close();
	}

}
