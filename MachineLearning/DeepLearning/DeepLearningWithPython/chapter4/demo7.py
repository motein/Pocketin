'''
Created on Aug 4, 2018

@author: motein
'''
import theano.tensor as T
from theano import function

# binary cross entropy
a1 = T.dmatrix('a1')
a2 = T.dmatrix('a2')
f_a = T.nnet.binary_crossentropy(a1, a2).mean()
f_sigmoid = function([a1, a2],[f_a])
info1 = "Binary Cross Entropy [[0.01,0.01,0.01]],[[0.99,0.99,0.01]]: "
print(info1, f_sigmoid([[0.01,0.01,0.01]],[[0.99,0.99,0.01]]))

# categorical cross entropy
b1 = T.dmatrix('b1')
b2 = T.dmatrix('b2')
f_b = T.nnet.categorical_crossentropy(b1, b2)
f_sigmoid = function([b1, b2],[f_b])
info2 = "Categorical Cross Entropy [[0.01,0.01,0.01]],[[0.99,0.99,0.01]]: "
print(info2, f_sigmoid([[0.01,0.01,0.01]],[[0.99,0.99,0.01]]))

# squared error
def squared_error(x,y):
    return (x - y) ** 2

c1 = T.dmatrix('b1')
c2 = T.dmatrix('b2')
f_c = squared_error(c1, c2)
f_squared_error = function([c1, c2],[f_c])
info3 = "Squared Error [[0.01,0.01,0.01]],[[0.99,0.99,0.01]]: "
print(info3, f_sigmoid([[0.01,0.01,0.01]],[[0.99,0.99,0.01]]))