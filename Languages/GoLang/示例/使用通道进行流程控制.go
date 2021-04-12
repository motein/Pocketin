package main

import (
	"fmt"
	"time"
)

func pinger(ch chan string) {
	t := time.NewTicker(1 * time.Second)
	for {
		ch <- "ping"
		<-t.C
		// fmt.Println(t.C)
	}
}

func main() {
	messages := make(chan string)
	go pinger(messages)
	for {
		msg := <-messages
		fmt.Println(msg)
	}
}
