'''
Created on Aug 19, 2018

@author: xiongan2
'''
from numpy import *
from matplotlib import pyplot as plt
from chapter08 import regression

xArr, yArr = regression.loadDataSet('ex0.txt')
yHat, xCopy = regression.lwlrTestPlot(xArr, yArr, 0.01)

fig = plt.figure()
ax = fig.add_subplot(111)
ax.scatter(mat(xArr)[:, 1].flatten().A[0], mat(yArr).T[:, 0].flatten().A[0])
ax.plot(xCopy[:, 1], yHat)
plt.show()