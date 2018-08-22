'''
Created on Aug 22, 2018

@author: xiongan2
'''
from chapter04 import bayes
from pprint import pprint
import feedparser
ny = feedparser.parse('https://newyork.craigslist.org/search/act?format=rss')
sf = feedparser.parse('https://newyork.craigslist.org/search/act?format=rss')
# pprint([i['title'] for i in ny['entries']])
bayes.getTopWords(ny, sf)