package main

import (
	"fmt"
)

func getAverage(arr []int, size int) float32 {
	var i, sum int
	var avg float32

	for i = 0; i < size; i++ {
		sum += arr[i]
	}

	avg = float32(sum / size)
	return avg
}

func getAverage2(arr [5]int) float32 {
	var i, sum int
	var avg float32

	for i = 0; i < 5; i++ {
		sum += arr[i]
	}

	avg = float32(sum / 5)
	return avg
}

func main() {
	var cheeses [2]string
	cheeses[0] = "Mariolles"
	cheeses[1] = "Epoisses de Bourgogne"
	fmt.Println(cheeses)

	var cheeses2 = make([]string, 2)
	cheeses2[0] = "Hello"
	cheeses2[1] = "World"
	cheeses2 = append(cheeses2, "Again")

	fmt.Println(cheeses2)

	//var balance = []int{1000, 2, 3, 17, 50}
	var balance2 = [5]int{1000, 2, 3, 17, 50}
	fmt.Println(getAverage2(balance2))
}
