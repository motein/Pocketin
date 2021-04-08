package main

import (
	"fmt"
	"time"
)

func slowFunc(ch chan<- string) {
	time.Sleep(time.Second * 2)
	ch <- "sleeper() finished"
}

func main() {
	c := make(chan string)
	go slowFunc(c)
	// go func() { c <- "ping" }()
	msg := <-c
	fmt.Println(msg)
}
