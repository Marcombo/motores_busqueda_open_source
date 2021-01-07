from pybinaryedge import BinaryEdge
import os

key= os.environ['BINARYEDGE_API_KEY']

binaryEdge = BinaryEdge(key)

busqueda = 'elasticSearch AND tag.keyword:DATABASE'

results = binaryEdge.host_search(busqueda)

for ip in results['events']:
    print("%s" %(ip['target']['ip']))
