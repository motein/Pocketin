'''
Created on Aug 17, 2018

@author: xiongan2
'''
 # -*- coding: utf-8 -*-
from chapter10 import kMeans
import json

geoResults = kMeans.geoGrabFromBaidu2('北京市海淀区上地十街10号')
print(geoResults)