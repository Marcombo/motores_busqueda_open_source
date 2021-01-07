import re
import os
from urllib.parse import urlparse
from urllib.parse import urljoin
import hashlib
import json
import sys

from elasticsearch import Elasticsearch

es= Elasticsearch()

title_re = re.compile(r'([=*]{3,})\n([^\n]+)\n\1\n')

def find_title(rst):
    matches = title_re.findall(rst)
    if not matches:
        return ''
    else:
        return matches[0][1]


def walk_documentation(path='.', base_url=''):
    path = os.path.realpath(path)
    #print('Path',path)
    for dirpath, dirnames, filenames in os.walk(path):
        #print(dirpath)
        for filename in filenames:
            if filename.endswith('.txt'):
                filepath = os.path.join(dirpath, filename)
                content = open(filepath).read()
                title = find_title(content)
                # Figure out path relative to original
                relpath = os.path.relpath(filepath, path)
                url = urljoin(base_url, relpath[:-4] + '/')
                print(content)
                yield {
                    'title': title,
                    'path': relpath,
                    'top_folder': relpath.split('/')[0],
                    'url': url,
                    'content': content,
                    'id': hashlib.sha1(url.encode('utf-8')).hexdigest(),
                }


def uso(extra_error=''):
    print("Usage: %s <path-to-folder> <base-url-of-hosted-docs>" % sys.argv[0])
    print("python3 index_django_docs_to_elasticsearch.py  django-master/docs/ https://docs.djangoproject.com/en/3.1")
    if extra_error:
        print (extra_error)
    sys.exit(1)


if __name__ == '__main__':
    args = sys.argv[1:]
    if len(args) != 2:
        uso()
    path, base_url = args
    if not (base_url.startswith('http://') or base_url.startswith('https://')):
        uso('Docs URL must start with http or https')

    for documento in walk_documentation(path, base_url):
        print (json.dumps({'index': {'_id': documento['id']}}))
        print (json.dumps(documento))
        es.index(index="django",id=documento['id'],body=json.dumps(documento))
