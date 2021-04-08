package main

import (
	"fmt"
	"time"
)

func sender(ch chan string) {
	t := time.NewTicker(1 * time.Second)
	for {
		ch <- "I'm sending a message"
		<-t.C
	}
}

func main() {
	messages := make(chan string)
	stop := make(chan bool)
	go sender(messages)
	go func() {
		time.Sleep(time.Second * 2)
		fmt.Println("Time's up")
		stop <- true // stop
	}()
	for {
		select {
		case <-stop: // when receiving stop channel, then stop running
			return
		case msg := <-messages:
			fmt.Println("received", msg)
		}
	}
}
