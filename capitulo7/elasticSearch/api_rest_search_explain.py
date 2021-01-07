import requests
import json

def explain(query):
	url = 'http://localhost:9200/movies/_search'
	print(json.dumps(query))
	input_data = json.dumps(query)
	headers = {'content-type': 'application/json'}
	respuesta = requests.get(url,data=input_data,headers=headers)
	print(respuesta)
	documentos = json.loads(respuesta.text)['hits']
	for idx, hit in enumerate(documentos['hits']):
		print("Explain for movie %s" % documentos['hits'][idx]['_source']['title'])
		print(json.dumps(documentos['hits'][idx]['_explanation'], indent=True))


def main():
	query = {
    "explain":"true",
	"query": {
        "multi_match": {
            "query": "action",
            "fields": ["title", "topics"]
        }
	}
	}

	explain(query)

if __name__== "__main__":
  main()

