package main

import (
	"fmt"
)

type Movie struct {
	Name   string
	Rating float32
}

func main() {
	m := Movie{Name: "Citizen Kanne", Rating: 10}
	fmt.Println(m.Name, m.Rating)

	var m2 Movie
	m2.Name = "More"
	m2.Rating = 12.33
	fmt.Println(m2.Name, m2.Rating)

	m3 := new(Movie)
	m3.Name = "Metropilis"
	m3.Rating = 0.99
	fmt.Println(m3.Name, m3.Rating)
}
