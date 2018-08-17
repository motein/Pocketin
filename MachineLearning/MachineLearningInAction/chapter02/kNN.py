'''
Created on Aug 13, 2018

@author: xiongan2
'''
import numpy as np
import operator

def classify(inX, dataSet, labels, k):
    dataSetSize = dataSet.shape[0]
    diffMat = np.tile(inX, (dataSetSize, 1)) - dataSet
    sqDiffMat = diffMat**2
    sqDistances = sqDiffMat.sum(axis=1)
    distances = sqDistances**0.5

    sortedDistanceIndices = distances.argsort()
    classCount={}
    for i in range(k):
        voteIlabel = labels[sortedDistanceIndices[i]]
        classCount[voteIlabel] = classCount.get(voteIlabel, 0) + 1
        
    sortedClassCount = sorted(classCount.items(), key=operator.itemgetter(1), reverse=True)                  
    return sortedClassCount[0][0]

def file2matrix(fileName):
    fileHandler = open(fileName)
    numberOfLines = len(fileHandler.readlines())
    returnMat = np.zeros((numberOfLines, 3))
    classLabelVector = []
    fileHandler = open(fileName)
    index = 0
    for line in fileHandler.readlines():
        line = line.strip()                         #strip blank characters
        listFromLine = line.split('\t')
        returnMat[index,:] = listFromLine[0:3]
        classLabelVector.append(listFromLine[-1])
        index += 1
        
    return returnMat.astype(np.float), list(map(float, classLabelVector))

'''
newValue = (oldValue - min)/(max-min)
'''
def autoNorm(dataSet):
    minVals = dataSet.min()
    maxVals = dataSet.max()
    ranges = maxVals - minVals
    normDataSet = np.zeros(np.shape(dataSet))
    m = dataSet.shape[0]
    normDataSet = dataSet - np.tile(minVals, (m, 1))
    normDataSet = normDataSet/np.tile(ranges, (m, 1))
    return normDataSet, ranges, minVals

def img2vector(filename):
    returnVect = np.zeros((1, 1024))
    fr = open(filename)
    for i in range(32):
        lineStr = fr.readline()
        for j in range(32): # 32 lines in a file
            returnVect[0,32*i+j] = int(lineStr[j]) # 32 ¡Á 32 = 1024
    return returnVect
    