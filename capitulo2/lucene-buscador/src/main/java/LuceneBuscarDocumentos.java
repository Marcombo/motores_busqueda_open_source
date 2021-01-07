import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.core.WhitespaceAnalyzer;
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

public class LuceneBuscarDocumentos {

	public static void main(String[] args) throws Exception {

		Path path = Paths.get("/home/linux/documentos");
		Directory directorio = FSDirectory.open(path);

		Analyzer analyzer = new WhitespaceAnalyzer();

		IndexReader indexReader = DirectoryReader.open(directorio);
		IndexSearcher indexSearcher = new IndexSearcher(indexReader);
		QueryParser parser = new QueryParser("texto",analyzer);
		Query query = parser.parse("Python");
		
		int hitsPerPage = 10;
		TopDocs docs = indexSearcher.search(query, hitsPerPage);
		ScoreDoc[] hits = docs.scoreDocs;
		int end = (int) Math.min(docs.totalHits.value, hitsPerPage);
		System.out.print("Total de documentos: " + docs.totalHits);
		
		System.out.print("\nResultados: ");
		for (int i = 0; i < end; i++) {
				Document d = indexSearcher.doc(hits[i].doc);
				System.out.println("Documento: " + d.get("nombre")+" Texto: " + d.get("texto"));
		}
		
		//Puntuaciones
		
		TopDocs topDocs = indexSearcher.search(query, hitsPerPage);
		for (ScoreDoc scoreDoc : topDocs.scoreDocs) {
			Document documento = new Document();
			documento = indexReader.document(scoreDoc.doc);
			System.out.println(scoreDoc.score + ": " + documento.getField("nombre").stringValue()+ " "+ documento.getField("texto").stringValue());
		}
	}
	

}
