import requests
import json

def busqueda(uri, term):
    query = json.dumps({
        "query": {
            "match": {
                "content": term
            }
        }
    })
    cabeceras = {'Content-type': 'application/json', 'Accept': 'application/json'}
    respuesta = requests.get(uri, data=query,headers=cabeceras)
    resultados = json.loads(respuesta.text)
    return resultados

def formato_resultados(resultados):
    print("Número de resultados:",resultados['hits']['total']['value'])
    data = [documento for documento in resultados['hits']['hits']]
    print("Id\t Score\t Title\t URL\t")
    for documento in data:
        print(documento['_source']['id'],documento['_score'],
        documento['_source']['title'],documento['_source']['url'])
        
if __name__ == '__main__':
    uri_search = 'http://localhost:9200/django/_search'
    resultados = busqueda(uri_search, "Git")
    formato_resultados(resultados)




