'''
Created on Aug 19, 2018

@author: xiongan2
'''
from numpy import *
from matplotlib import pyplot as plt
from chapter08 import regression

xArr, yArr = regression.loadDataSet('ex0.txt')
print(yArr[0])
ws = regression.lwlr(xArr[0], xArr, yArr, 1.0)
print(ws)
ws = regression.lwlr(xArr[0], xArr, yArr, 0.001)
print(ws)

yHat = regression.lwlrTest(xArr, xArr, yArr, 0.01)
xMat = mat(xArr)
srtInd = xMat[:, 1].argsort(0)
xSort = xMat[srtInd][:,0,:]

fig = plt.figure()
ax = fig.add_subplot(111)
ax.plot(xSort[:, 1],yHat[srtInd])
ax.scatter(xMat[:, 1].flatten().A[0], mat(yArr).T.flatten().A[0], s = 2, c = 'red')
plt.show()