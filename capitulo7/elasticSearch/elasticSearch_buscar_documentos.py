from elasticsearch import Elasticsearch

es= Elasticsearch()

#buscar el documento con identificador id:1000
query = {'query':{'match':{'_id':1000}}}

respuesta_busqueda = es.search(index="movies",body=query)
print(respuesta_busqueda)

#buscar el documento con title:mars
query = {'query':{'match':{'title':'mars'}}}

respuesta_busqueda = es.search(index="movies",body=query)
print(respuesta_busqueda)
