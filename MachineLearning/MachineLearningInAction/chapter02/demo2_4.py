'''
Created on Aug 13, 2018

@author: xiongan2
'''
from chapter02 import kNN

if __name__ == "__main__":
    testVector = kNN.img2vector('testDigits/0_13.txt')
    print(len(testVector[0, 0:31]))
    print(len(testVector[0]))
    print(type(testVector))
    print(testVector.shape)