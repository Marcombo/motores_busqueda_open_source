import elasticsearch
import json

es = elasticsearch.Elasticsearch()

#nombre del indice
indice = "movies"   

#comprobamos si el índice existe
def comprobar_indice(indice):
    if not es.indices.exists(index = indice):
        return False
    else:
        return True
        
#añadir documento al índice con el método index
def indexar_documento(indice,documento,id_documento):
    response = es.index(index=indice, body=documento, id=id_documento)
    return response

#obtener un documento del índice a partir del id
def obtener_documento(indice,id_documento):
    response = es.get(index=indice,id=id_documento)
    return response.get('_source')
    
#buscar documento a partir de una query
def buscar_documentos_query(indice,query=None):
    if query is None:
        query = {
            "query": {
                "match_all": {}
            }
        }
        
    response = es.search(index=indice, body=query, request_timeout=10)
    for hit in response['hits']['hits']:
        print(json.dumps(hit.get('_id'))+ " "+ json.dumps(hit.get('_source')))
    
if comprobar_indice(indice):
    print('El índice '+ indice + ' ya existe')
else:
    es.indices.create(index = indice)
    
documento = {"title":"movie","year":"2020","topics": ["Animation", "Action", "Comedy "]}

indexar_documento(indice,documento,1001)

print(obtener_documento(indice,1001))

buscar_documentos_query(indice)

#busqueda de documentos por id
query = {
	"query": {
		"match": {"_id":1001}
	}
}

buscar_documentos_query(indice,query)
