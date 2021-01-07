import java.io.File;
import java.io.FileReader;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.ClassicAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

public class LuceneCrearIndice {

	public static void main(String[] args) throws Exception {

		File directorioDocumentos = new File("/home/linux/documentos");

		Path path = Paths.get("/home/linux/documentos");

		Directory directory = FSDirectory.open(path);

		Analyzer analizador = new ClassicAnalyzer();

		IndexWriterConfig configuracionIndice = new IndexWriterConfig(analizador);

		IndexWriter indice = new IndexWriter(directory, configuracionIndice);

		File[] archivos = directorioDocumentos.listFiles();
		for (int i = 0; i < archivos.length; i++) {
			File f = archivos[i];
			if (!f.isDirectory() && !f.isHidden() && f.exists() && f.canRead() && (f.getName().endsWith(".txt"))) {
				System.out.println("Indexing " + f.getCanonicalPath());
				Document documento = new Document();
				// Campo nombre archivo
				Field camponombre = new TextField("rutaArchivo", f.getName(), TextField.Store.YES);
				documento.add(camponombre);
				// Campo contenido del archivo
				Field campocontenido = new TextField("contenido", new FileReader(f));
				documento.add(campocontenido);
				indice.addDocument(documento);
			}
		}
		System.out.println("El numero de documentos indexados es " + indice.numRamDocs());
		indice.close();
	}

}
