package main

import (
	"errors"
	"fmt"
	"io/ioutil"
)

func main() {
	err0 := errors.New("Something wrong")
	fmt.Println(err0)

	name, role := "Richard Jupp", "Drummer"
	err1 := fmt.Errorf("The %s %s quit", role, name) // %v si the value in a default format
	fmt.Println(err1)

	file, err := ioutil.ReadFile("foo.txt") // []byte, error
	if err != nil {
		fmt.Println(err)
		return
	}

	fmt.Printf("%s", file)
}
