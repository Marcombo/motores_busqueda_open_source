import requests
import json

def validate(query):
	url = 'http://localhost:9200/movies/_validate/query?explain'
	print(json.dumps(query))
	input_data =json.dumps(query)
	headers = {'content-type': 'application/json'}
	respuesta = requests.get(url,data=input_data,headers=headers)
	print(respuesta)
	explanations = json.loads(respuesta.text)['explanations']
	print(json.dumps(explanations, indent=True))

def main():
	search = 'mars'
	
	query = {
	"query": {
	"multi_match": {
	"query": search
	}
	}
	}

	validate(query)

if __name__== "__main__":
  main()




