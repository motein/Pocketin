package main

import (
	"fmt"

	"example.com/hello"
)

func main() {
	message := hello.Hello()
	fmt.Println(message)
}
