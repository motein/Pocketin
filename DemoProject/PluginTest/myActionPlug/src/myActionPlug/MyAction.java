package myActionPlug;

import printlnInterface.MyActionInterface;

public class MyAction implements MyActionInterface {

	@Override
	public void print() {
		System.out.println("Now you have used my Plug-in");
	}
}
