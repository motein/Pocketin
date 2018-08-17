'''
Created on Aug 17, 2018

@author: xiongan2
'''
 # -*- coding: utf-8 -*-
from chapter10 import kMeans

geoResults = kMeans.geoGrab('4570 Lombard Ave', 'Beaverton, OR')
print(geoResults['results'][0]['geometry']['location'])
kMeans.massPlaceFind('portlandClubs.txt')