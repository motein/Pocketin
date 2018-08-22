'''
Created on Aug 22, 2018

@author: xiongan2
'''
import re

mySent = 'This book is the best book on Python or M.L. I have ever laid eyes upon.'
print(mySent.split())

regEx = re.compile('\\W+')
listOfTokens = regEx.split(mySent)
print(listOfTokens)

list1 = [tok for tok in listOfTokens if len(tok) > 0]
print(list1)

list2 = [tok.lower() for tok in listOfTokens if len(tok) > 0]
print(list2)

emailText = open('email/ham/23.txt').read()
listOfTokens = regEx.split(emailText)
print(listOfTokens)