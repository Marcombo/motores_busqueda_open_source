import csv
import re

csvfile = open('ml-latest-small/movies.csv','r',encoding="utf8")

reader = csv.DictReader(csvfile)

for movie in reader:
    print("{ \"create\" : { \"_index\": \"movies\",\"_id\":\"",movie['movieId'],"\"}}",sep='')
    title = re.sub("\(.*\)$","",re.sub('"','',movie['title']))
    year = str(movie['title'][-5:-1])
    topics = movie['genres'].split("|")
    print("{ \"id\" : ", int(movie['movieId']),",\"title\":\"",title,"\",\"year\":\"",str(year),"\",\"topics\":[",end='',sep='')
    for topic in topics[:-1]:
        print("\"",topic,"\",",end='',sep='')
    print("\"",topics[-1],"\"",end='',sep='')
    print("]}")
