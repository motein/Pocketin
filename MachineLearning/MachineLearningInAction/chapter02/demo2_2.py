'''
Created on Aug 13, 2018

@author: xiongan2
'''
from chapter02 import kNN

def datingClassTest():
    hoRatio = 0.10
    datingDataMat, datingLables = kNN.file2matrix('datingTestSet2.txt')
    normMat, ranges, minVals = kNN.autoNorm(datingDataMat)
    m = normMat.shape[0]
    numTestVecs = int(m*hoRatio)
    errorCount = 0.0
    for i in range(numTestVecs):
        classifierResult = kNN.classify(normMat[i,:], normMat[numTestVecs:m,:], datingLables[numTestVecs:m],3)
        print("the classifier came back with: %s, the real answer is: %s" % (classifierResult, datingLables[i]))
        if (classifierResult != datingLables[i]):
            errorCount += 1.0
    print("the total error rate is: %f" %(errorCount/float(numTestVecs)))
    print(errorCount)
    
if __name__ == "__main__":
    datingClassTest()