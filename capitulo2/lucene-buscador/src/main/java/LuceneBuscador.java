import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.ClassicAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

public class LuceneBuscador {

	public static void main(String[] args) throws Exception {

		File directorioDocumentos = new File("/home/linux/documentos");

		Path path = Paths.get("/home/linux/documentos");
		
		Directory directory = FSDirectory.open(path);

		Analyzer analizador = new ClassicAnalyzer();
		
		String cadenaBuscar="Python";
		
		IndexReader indexReader = DirectoryReader.open(directory);
		IndexSearcher indexSearcher = new IndexSearcher(indexReader);
		QueryParser parser = new QueryParser("contenido",analizador);
		Query query = parser.parse(cadenaBuscar);
		
		int hitsPerPage = 10;
		TopDocs docs = indexSearcher.search(query, hitsPerPage);
		System.out.print("Total de documentos: " + docs.totalHits+"\n");
		
		ScoreDoc[] hits = docs.scoreDocs;
		for (ScoreDoc scoreDoc : hits) {
			Document documento = new Document();
			documento = indexSearcher.doc(scoreDoc.doc);
			System.out.println("Puntuacion:"+scoreDoc.score + "/" + documento.getField("rutaArchivo").stringValue());
		}
		
	
	}

}
