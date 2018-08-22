'''
Created on Aug 22, 2018

@author: xiongan2
'''
from chapter04 import bayes
import feedparser
ny = feedparser.parse('https://newyork.craigslist.org/search/bts?format=rss')
print(ny)
print(ny['entries'])
print(len(ny['entries']))

sf = feedparser.parse('https://newyork.craigslist.org/search/bts?format=rss')
vocalList, pSF, pNY = bayes.localWords(ny, sf)