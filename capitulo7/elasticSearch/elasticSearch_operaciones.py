import elasticsearch
es = elasticsearch.Elasticsearch()

#nombre del indice
indice = "movies"   

#comprobamos si el índice existe, de lo contrario,lo creamos
if not es.indices.exists(index = indice):
	print('El índice no existe, lo creamos.....')
	es.indices.create(index = indice)
	time.sleep(2)
	print('Indice creado ok')
else:
	print('El índice '+ indice + ' ya existe')

documento = {"title":"title movie","year":"2020","topics": ["Animation", "Action", "Comedy "]}

#añadir documento al índice con el método index y le pasamos en el body el cuerpo del documento
response = es.index(index=indice, body=documento,id=1000)
print(response)

#actualizar documento con identificador id=1000
script = {"script": "ctx._source.title = 'new title movie '"}
es.update (index = indice, body = script, id = 1000,ignore = 404)

#obtener documento con identificador id=1000
response = es.get(index=indice, id=1000,ignore=404)
print(response)

#obtener mappings para un índice en concreto
mappings = es.indices.get_mapping(indice)
print(mappings)

#busqueda de documentos
query = {
	"query": {
		"match_all": {}
	}
}

response = es.search(index=indice, body=query, request_timeout=10)

for hit in response['hits']['hits']:
	print(str(hit.get('_id'))+ " "+ str(hit.get('_source')))
