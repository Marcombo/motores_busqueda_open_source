import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.ClassicAnalyzer;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.MultiReader;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

public class LuceneMultiSearcher {

	public static void main(String[] args) throws Exception {

		IndexReader[] readers;		
		Analyzer analyzer = new ClassicAnalyzer();

		Path path = Paths.get("/home/linux/documentos");
		Path path2 = Paths.get("/home/linux/documentos");
		
		Directory directory = FSDirectory.open(path);
		Directory directory2 = FSDirectory.open(path2);
		
		IndexReader indexReader1 = DirectoryReader.open(directory);
		IndexReader indexReader2 = DirectoryReader.open(directory2);
		
		readers = new IndexReader[2];
		readers[0] = indexReader1;
		readers[1] = indexReader2;
		
		MultiReader reader = new MultiReader(readers);
		IndexSearcher searcher=new IndexSearcher(reader);
		QueryParser parser = new QueryParser("contenido",analyzer);
		Query query = parser.parse("Python");
	    TopDocs docs=searcher.search(query,10);
		System.out.print("Total de documentos: " + docs.totalHits);

	}
}
