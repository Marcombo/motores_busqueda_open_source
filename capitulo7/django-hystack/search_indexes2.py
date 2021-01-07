import datetime
from haystack import indexes
from haystack import site
from blog.models import BlogEntry

class BlogEntryIndex(RealTimeSearchIndex):
  
    text = indexes.CharField(document=True, use_template=True)
    author = indexes.CharField(model_attr='author')
    pub_date = indexes.DateField(model_attr='pub_date')

   def get_queryset(self):
  	return BlogEntry.objects.filter(pub_date= datetime.datetime.now(), status=BlogEntry.LIVE_STATUS)

site.register(BlogEntry, BlogEntryIndex)
