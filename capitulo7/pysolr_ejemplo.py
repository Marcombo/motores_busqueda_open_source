import pysolr

# Configurar una instancia de Solr. El parámetro timeout es opcional
solr = pysolr.Solr('http://localhost:8983/solr/new_core',always_commit=True, timeout=10)

print(solr.ping())

# añadimos un documento al índice de productos
solr.add([
    {
        "id":1,
        "title": "movie 2020",
        "year": 2020,
        "topics":["Action"]  
    },
    {
        "id":2,
        "title": "movie 2021",
        "year": 2021,
        "topics":["Action"]  
    }
])

resultados_busqueda = solr.search('title:"movie 2020"')

#obtener número de resultados
print("Número resultados {0}.".format(len(resultados_busqueda)))


for resultado in resultados_busqueda:
    print("\n Id :{0} ".format(resultado['id'][0]))
    print(" Title :{0} ".format(resultado['title'][0]))
    print(" Year :{0} ".format(resultado['year'][0]))
    print(" Topics :{0} ".format(resultado['topics']))
