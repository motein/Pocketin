package main

import (
	"bytes"
	"fmt"
)

func main() {
	var buffer bytes.Buffer

	for i := 0; i < 5; i++ {
		buffer.WriteString("zz ")
	}

	fmt.Println(buffer.String())
}
