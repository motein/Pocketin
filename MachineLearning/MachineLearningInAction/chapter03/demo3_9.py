'''
Created on Aug 21, 2018

@author: xiongan2
'''

from chapter03 import trees, treePlotter

fr = open('lenses.txt')
lenses = [inst.strip().split('\t') for inst in fr.readlines()]
lensesLabels = ['age', 'prescript', 'astigmatic', 'tearRate']
lensesTree = trees.createTree(lenses, lensesLabels)
print(type(lensesTree))
print(lensesTree)

treePlotter.createPlot(lensesTree)