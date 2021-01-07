import requests
import json

def busqueda(query):
    url = 'http://localhost:9200/movies/_search'
    print("Consulta:", json.dumps(query))
    input_data =json.dumps(query)
    
    headers = {'content-type': 'application/json'}
    respuesta = requests.get(url,data=input_data,headers=headers)
    print(respuesta)
    
    resultados = json.loads(respuesta.text)['hits']
    
    print("Id\t Score\t Title\t Year\t Topics")
    for idx,documento in enumerate(resultados['hits']):
        print(documento['_source']['id'],documento['_score'], documento['_source']['title'],
        documento['_source']['year'],documento['_source']['topics'])

def main():
	cadena = 'action'
	
	query = {
        "query": {
            "multi_match": {
                "query": cadena,
                "fields": ["title", "topics"]
                }
        }
	}

	busqueda(query)

if __name__== "__main__":
    main()
