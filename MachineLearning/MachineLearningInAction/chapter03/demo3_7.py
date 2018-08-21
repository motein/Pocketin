'''
Created on Aug 21, 2018

@author: xiongan2
'''
from chapter03 import treePlotter

myTree = treePlotter.retrieveTree(0)
treePlotter.createPlot(myTree)

myTree['no surfacing'][3] = 'maybe'
print(myTree)
treePlotter.createPlot(myTree)