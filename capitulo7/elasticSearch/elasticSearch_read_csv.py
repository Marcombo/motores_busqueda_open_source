import csv
import elasticsearch

es = elasticsearch.Elasticsearch()

#nombre del índice
index_name = "index"

#creamos el índice
es.indices.create(index = index_name, ignore = 400)

with open("data.csv") as file:
    reader = csv.DictReader(file)
    for line in reader:
        es.index(index=index_name, body= line)
        
