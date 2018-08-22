'''
Created on Aug 22, 2018

@author: xiongan2
'''
from chapter05 import logRegress
from pprint import pprint

dataArr, labelMat = logRegress.loadDataSet()
# print(dataArr + labelMat)
weights = logRegress.gradAscent(dataArr, labelMat)
print(weights)
logRegress.plotBestFit(weights.getA())
