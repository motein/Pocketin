'''
Created on Aug 9, 2018

@author: xiongan2
'''
#Wrapper Around Numpy
import autograd.numpy as np
import autograd.numpy.random as npr
from autograd.test_util import check_grads
from autograd import grad
#Define the function
def f(x1, x2):
    return np.sqrt(x1 * x1 + x2 * x2)

def logsumexp(x):
    max_x = np.max(x)
    return max_x + np.log(np.sum(np.exp(x - max_x)))

def example_func(y):
    z = y**2
    lse = logsumexp(z)
    return np.sum(lse)

#Computes and checks the gradient for the given values
check_grads(f)(1.0, 2.0)

grad_of_example = grad(example_func)
print("Gradient: \n", grad_of_example(npr.randn(10)))

# Check the gradients numerically, just to be safe.
check_grads(example_func, modes=['rev'])(npr.randn(10))