package main

import (
	"fmt"
	"time"
)

func slowFunc() {
	fmt.Println("start")
	time.Sleep(time.Second * 5)
	fmt.Println("sleeper() finished")
}

func main() {
	// slowFunc()
	go slowFunc()
	fmt.Println("I am not shown until slowFunc() completes")
	time.Sleep(time.Second * 7)
}
