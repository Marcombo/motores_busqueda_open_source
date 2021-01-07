import csv
import re
from collections import deque
import elasticsearch
from elasticsearch import helpers

def readMovies():
    csvfile = open('ml-latest-small/movies.csv','r',encoding="utf8")
    reader = csv.DictReader(csvfile)
    
    for line in reader:
        movie = {}
        movie['id'] = int(line['movieId'])
        movie['title'] = re.sub("\(.*\)$","",re.sub('"','',line['title']))
        movie['year'] = line['title'][-5:-1]
        movie['topics'] = line['genres'].split("|")
        yield(movie)

        
readMovies()

es = elasticsearch.Elasticsearch()
#es.indices.delete(index="movies")
deque(helpers.parallel_bulk(es,readMovies(),index="movies"),maxlen=0)
es.indices.refresh()

