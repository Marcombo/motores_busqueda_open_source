import shodan

API_KEY= " "
shodan = shodan.Shodan(API_KEY)

try:
    resultados = shodan.search('elasticSearch')
    print("Numero de resultados:",resultados.items())
    # mostrar los resultados
    print('Resultados: %s' % results['total'])
    for resultado in resultados['matches']:
        print('IP: %s' % result['ip_str'])
        print(result['data'])
except Exception as exception:
    print(str(exception))
