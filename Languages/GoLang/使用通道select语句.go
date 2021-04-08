package main

import (
	"fmt"
	"time"
)

func ping1(ch chan string) {
	time.Sleep(time.Second * 1)
	ch <- "Ping on channel 1"
}

func ping2(ch chan string) {
	time.Sleep(time.Second * 2)
	ch <- "Ping on channel 2"
}

func main() {
	channel1 := make(chan string)
	channel2 := make(chan string)
	go ping1(channel1)
	go ping2(channel2)

	select {
	case msg1 := <-channel1:
		fmt.Println("received", msg1)
	case msg2 := <-channel2:
		fmt.Println("received", msg2)
	}
}
