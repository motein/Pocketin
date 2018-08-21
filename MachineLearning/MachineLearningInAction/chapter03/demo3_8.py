'''
Created on Aug 21, 2018

@author: xiongan2
'''
# -*- coding: utf-8 -*-
from chapter03 import trees, treePlotter

myDat, labels = trees.createDataSet()
print(labels)

myTree = treePlotter.retrieveTree(0)
print(myTree)

print(trees.classify(myTree, labels, [1,0]))
print(trees.classify(myTree, labels, [1,1]))

trees.storeTree(myTree, 'classifierStorage.txt')
trees.grabTree('classifierStorage.txt')