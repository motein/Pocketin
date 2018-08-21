'''
Created on Aug 21, 2018

@author: xiongan2
'''
from chapter03 import trees

a = [1, 2, 3]
b = [4, 5, 6]
a.append(b)
print(a)

a = [1, 2, 3]
a.extend(b)
print(a)

myDat, labels = trees.createDataSet()
print(myDat)
print(trees.splitDataSet(myDat, 1, 1))
print(trees.splitDataSet(myDat, 0, 0))