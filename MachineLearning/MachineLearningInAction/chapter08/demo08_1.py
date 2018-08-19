'''
Created on Aug 19, 2018

@author: xiongan2
'''
from numpy import *
from matplotlib import pyplot as plt
from chapter08 import regression

xArr, yArr = regression.loadDataSet('ex1.txt')
print(xArr)
ws = regression.standRegres(xArr, yArr)
print(ws)
xMat = mat(xArr)
yMat = mat(yArr)
yHat = xMat * ws
yHat = xMat * ws
print(corrcoef(yHat.T, yMat))

fig = plt.figure()
ax = fig.add_subplot(111)
ax.scatter(xMat[:, 1].flatten().A[0], yMat.T[:, 0].flatten().A[0])
xCopy = xMat.copy()
xCopy.sort(0)
yHat = xCopy * ws
ax.plot(xCopy[:, 1], yHat)
plt.show()