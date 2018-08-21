'''
Created on Aug 21, 2018

@author: xiongan2
'''
from chapter03 import trees

myDat, labels = trees.createDataSet()
print(myDat)
shannonEnt = trees.calcShannonEnt(myDat)
print(shannonEnt)

myDat[0][-1] = 'maybe'
print(myDat)
shannonEnt = trees.calcShannonEnt(myDat)
print(shannonEnt)