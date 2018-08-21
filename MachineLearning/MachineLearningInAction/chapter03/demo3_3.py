'''
Created on Aug 21, 2018

@author: xiongan2
'''
from chapter03 import trees

myDat, labels = trees.createDataSet()
print(myDat)
print(trees.chooseBestFeatureToSplit(myDat))