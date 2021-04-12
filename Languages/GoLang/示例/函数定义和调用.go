package main

import (
	"fmt"
)

func getPrice() (int, string) {
	i := 2
	s := "goldfish"

	return i, s
}

func sumNumbers(numbers ...int) int {
	total := 0
	for _, number := range numbers {
		total += number
	}
	return total
}

func main() {
	quantity, prize := getPrice()
	fmt.Println(quantity, prize)

	result := sumNumbers(1, 2, 3, 4)
	fmt.Println(result)
}
