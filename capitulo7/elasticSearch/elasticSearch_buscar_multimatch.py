from elasticsearch import Elasticsearch

es= Elasticsearch()

#buscar los documentos que contienen 'animation' en alguno de los campos
query = {'query':{'multi_match':{'query':'Animation'}}}

respuesta_busqueda = es.search(index="movies",body=query)
print(respuesta_busqueda)
