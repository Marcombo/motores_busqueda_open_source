# !/usr/bin/python3

import os

os.chdir(".\\django-master\\docs")

for root, dirs, files in os.walk(".", topdown = False):
   for name in files:
      print(os.path.join(root, name))
   for name in dirs:
      print(os.path.join(root, name))