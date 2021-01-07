import json
import requests

url = "http://localhost:9200/movies/_doc/1001"

documento = {
  "id":1001,
  "title": "Title movie",
  "year" : "2022",
  "topics": ["Animation", "Action", "Comedy "]
  }

cabeceras = {'Content-type': 'application/json', 'Accept': 'application/json'}
requests.post(url, data=json.dumps(documento),headers=cabeceras)



