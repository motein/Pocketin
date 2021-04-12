package main

import (
	"fmt"
	"time"
)

func receiver(ch chan string) {
	for msg := range ch {
		fmt.Println(msg)
	}
}

func main() {
	messages := make(chan string, 3)
	messages <- "hello"
	messages <- "world"
	// messages <- "again"
	close(messages)

	fmt.Println("Pushed two messages onto Channel with no receivers")
	time.Sleep(time.Second * 1)
	receiver(messages)
}
