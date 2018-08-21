'''
Created on Aug 21, 2018

@author: xiongan2
'''
from chapter03 import treePlotter

myTree = treePlotter.retrieveTree(1)
print(myTree)

myTree = treePlotter.retrieveTree(0)
print(myTree)
print(treePlotter.getNumLeafs(myTree))
print(treePlotter.getTreeDepth(myTree))