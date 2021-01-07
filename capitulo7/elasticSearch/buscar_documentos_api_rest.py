import requests
import json

def busqueda(uri, termino_busqueda):
    query = json.dumps({
        "query": {
            "match": {
                "title": termino_busqueda
            }
        }
    })
    cabeceras = {'Content-type': 'application/json', 'Accept': 'application/json'}
    
    respuesta = requests.get(uri, data=query, headers=cabeceras)
    resultados = json.loads(respuesta.text)
    return resultados

def formato_resultados(resultados):
    print("Número de resultados:",resultados['hits']['total']['value'])
    
    datos = [doc for doc in resultados['hits']['hits']]
    print("                 _id    id        title   year   topics")
    print("-------------------------------------------------------")
    for documento in datos:
        print("%s %s %s %s %s" % (documento['_id'],
        documento['_source']['id'], documento['_source']['title'],
        documento['_source']['year'],documento['_source']['topics']))
        
if __name__ == '__main__':
    uri_busqueda = 'http://localhost:9200/movies/_search'
    resultados = busqueda(uri_busqueda, "action")
    formato_resultados(resultados)




