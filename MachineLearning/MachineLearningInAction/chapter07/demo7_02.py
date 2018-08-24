'''
Created on Aug 24, 2018

@author: xiongan2
'''
from numpy import *
from chapter07 import adaboost
import matplotlib.pyplot as plt

D = mat(ones((5, 1)) / 5)
datMat, classLabels = adaboost.loadSimpData()
result = adaboost.buildStump(datMat, classLabels, D)
print(result)