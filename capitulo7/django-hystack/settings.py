# Add Haystack to INSTALLED_APPS
INSTALLED_APPS = settings.INSTALLED_APPS + (
    'haystack',
)

HAYSTACK_CONNECTIONS = {
    'default': {
        # For Solr:
        'ENGINE': 'haystack.backends.solr_backend.SolrEngine',
        'URL': 'http://localhost:9001/solr/example',
        'TIMEOUT': 60 * 5,
        'INCLUDE_SPELLING': True,
    },
    'elasticsearch': {
        'ENGINE': 'haystack.backends.elasticsearch_backend.ElasticsearchSearchEngine',
        'URL': 'http://localhost:9200',
        'INDEX_NAME': 'elastic_search_project'
    },
    'whoosh': {
        # For Whoosh:
        'ENGINE': 'haystack.backends.whoosh_backend.WhooshEngine',
        'PATH': os.path.join(os.path.dirname(__file__), 'whoosh_index'),
        'INCLUDE_SPELLING': True,
    },
    'simple': {
        # For Simple:
        'ENGINE': 'haystack.backends.simple_backend.SimpleEngine',
    }
}

