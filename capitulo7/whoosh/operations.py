from whoosh import fields, index

from datetime import datetime
import os

'''class whooshSCHEMA(fields.SchemaClass):
    id=fields.ID(stored=True, unique=True)
    title = fields.TEXT(stored=True,sortable=True)
    date = fields.DATETIME(stored=True)
    content = fields.TEXT)'''

WHOOSH_SCHEMA = fields.Schema(
   id=fields.ID(stored=True, unique=True),
   title=fields.TEXT(stored=True,sortable=True),
   date = fields.DATETIME(stored=True),
   content = fields.TEXT)

if not os.path.exists("documents"):
    os.mkdir("documents")

my_index = index.create_in("documents",schema=WHOOSH_SCHEMA)

#Para crear un índice necesitas un objeto writer
writer = my_index.writer()

#añadir documentos
writer.add_document(id='1',title="documento1",
  date = datetime(2019,1,1),
  content = "Documento 1")

writer.add_document(id='2',title="documento2",
  date = datetime(2020,1,1),
  content = "Documento 2")
  
writer.add_document(id='3',title="documento3",
  date = datetime(2020,1,1),
  content = "Documento 3")
  
writer.commit()  

#realizar la búsqueda por un campo
from whoosh import qparser

queryParser = qparser.QueryParser("title",schema = my_index.schema)
query = queryParser.parse("documento2")
with my_index.searcher() as searcher:
  results = searcher.search(query)
  print(results)
  for result in results:
    print(result)

#realizar la búsqueda por múltiples campos
from whoosh.qparser import MultifieldParser, OrGroup

queryParser = MultifieldParser(["title", 
                       "content"],
                        schema = my_index.schema,
                        group = OrGroup)
query = queryParser.parse("documento")
with my_index.searcher() as searcher:
  results = searcher.search(query)
  print(results)
  for result in results:
    print(result)

#scoring tf_idf
from whoosh import scoring
with my_index.searcher(weighting=scoring.TF_IDF()) as searcher:
  results = searcher.search(query)
  print(results)
  for result in results:
    print(result)
    
#scoring frecuency
from whoosh import scoring
with my_index.searcher(weighting=scoring.Frequency) as searcher:
  results = searcher.search(query)
  print(results)
  for result in results:
    print(result)

