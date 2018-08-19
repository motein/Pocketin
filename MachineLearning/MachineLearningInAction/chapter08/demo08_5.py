'''
Created on Aug 19, 2018

@author: xiongan2
'''
from numpy import *
from matplotlib import pyplot as plt
from chapter08 import regression

abX, abY = regression.loadDataSet('abalone.txt')
ridgeWeights = regression.ridgeTest(abX, abY)
fig = plt.figure()
ax = fig.add_subplot(111)
ax.plot(ridgeWeights)
plt.show()